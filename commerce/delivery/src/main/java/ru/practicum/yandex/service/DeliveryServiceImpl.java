package ru.practicum.yandex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.yandex.mapper.DeliveryMapper;
import ru.practicum.yandex.model.Delivery;
import ru.practicum.yandex.repository.DeliveryRepository;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.order.OrderClient;
import ru.yandex.practicum.types.DeliveryState;
import ru.yandex.practicum.warehouse.WarehouseClient;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
	private final DeliveryRepository deliveryRepository;
	private final DeliveryMapper deliveryMapper;
	private final OrderClient orderClient;
	private final WarehouseClient warehouseClient;
	private final double baseRate = 5.0;

	@Override
	public DeliveryDto createNewDelivery(DeliveryDto deliveryDto) {
		Delivery delivery = deliveryMapper.deliveryDtoToDelivery(deliveryDto);
		delivery.setDeliveryState(DeliveryState.CREATED);
		return deliveryMapper.toDeliveryDto(deliveryRepository.save(delivery));
	}

	@Override
	public void setDeliverySuccess(String deliveryId) {
		Delivery delivery = getDelivery(deliveryId);
		orderClient.completedOrder(delivery.getOrderId());
		delivery.setDeliveryState(DeliveryState.DELIVERED);
		deliveryRepository.save(delivery);
	}

	@Override
	public void setDeliveryPicked(String deliveryId) {
		Delivery delivery = getDelivery(deliveryId);
		orderClient.assemblyOrder(delivery.getOrderId());
		delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
		ShippedToDeliveryRequest deliveryRequest = ShippedToDeliveryRequest.builder()
				.orderId(delivery.getOrderId())
				.deliveryId(deliveryId)
				.build();
		warehouseClient.shippedToDelivery(deliveryRequest);
		deliveryRepository.save(delivery);
	}

	@Override
	public void setDeliveryFailed(String deliveryId) {
		Delivery delivery = getDelivery(deliveryId);
		orderClient.deliveryOrderFailed(delivery.getOrderId());
		delivery.setDeliveryState(DeliveryState.FAILED);
		deliveryRepository.save(delivery);
	}

	@Transactional(readOnly = true)
	@Override
	public Double getCostDelivery(OrderDto orderDto) {
		Delivery delivery = getDelivery(orderDto.getDeliveryId());
		AddressDto warehouseAddr = warehouseClient.getWareHouseAddress();
		double warehouseDeliveryRate = warehouseAddr.getCity().equals("ADDRESS_1") ? baseRate : baseRate * 2;
		double deliveryCost = baseRate + warehouseDeliveryRate;
		if (orderDto.getFragile()) deliveryCost = deliveryCost + (deliveryCost * 0.2);
		deliveryCost = deliveryCost + (orderDto.getDeliveryWeight() * 0.3);
		deliveryCost = deliveryCost + (orderDto.getDeliveryVolume() * 0.2);
		if (!warehouseAddr.getStreet().equals(delivery.getToAddress().getStreet()))
			deliveryCost = deliveryCost + (deliveryCost * 0.2);
		return deliveryCost;
	}


	private Delivery getDelivery(String deliveryId) {
		Optional<Delivery> delivery = deliveryRepository.findById(deliveryId);
		if (delivery.isEmpty())
			throw new NoDeliveryFoundException("Доставки с id = " + deliveryId + " не существует");
		return delivery.get();
	}
}
