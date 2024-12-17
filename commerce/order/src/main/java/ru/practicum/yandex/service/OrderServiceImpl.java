package ru.practicum.yandex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.yandex.mapper.OrderMapper;
import ru.practicum.yandex.model.Order;
import ru.practicum.yandex.repository.OrderRepository;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.shoppingCart.ShoppingCartClient;
import ru.yandex.practicum.types.OrderState;
import ru.yandex.practicum.warehouse.WarehouseClient;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final ShoppingCartClient cartClient;
	private final WarehouseClient warehouseClient;

	@Transactional(readOnly = true)
	@Override
	public List<OrderDto> getUserOrders(String username) {
		ShoppingCartDto shoppingCart = cartClient.getShoppingCart(username);
		return orderMapper.mapListOrders(orderRepository.findByShoppingCartId(shoppingCart.getShoppingCartId()));
	}

	@Override
	public OrderDto createNewOrder(CreateNewOrderRequest request) {
		Order order = Order.builder()
				.shoppingCartId(request.getShoppingCart().getShoppingCartId())
				.products(request.getShoppingCart().getProducts())
				.state(OrderState.NEW)
				.build();

		BookedProductsDto bookedProductsDto = warehouseClient.checkAvailableProducts(
				ShoppingCartDto.builder()
						.shoppingCartId(request.getShoppingCart().getShoppingCartId())
						.products(request.getShoppingCart().getProducts())
						.build()
		);

		order.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
		order.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
		order.setFragile(bookedProductsDto.getFragile());
		return orderMapper.toOrderDto(orderRepository.save(order));
	}

	@Override
	public OrderDto returnOrder(ProductReturnRequest request) {
		return null;
	}

	@Override
	public OrderDto paymentOrder(String orderId) {
		Order order = getOrder(orderId);
		order.setState(OrderState.PAID);
		return orderMapper.toOrderDto(orderRepository.save(order));
	}

	@Override
	public OrderDto paymentOrderFailed(String orderId) {
		Order order = getOrder(orderId);
		order.setState(OrderState.PAYMENT_FAILED);
		return orderMapper.toOrderDto(orderRepository.save(order));
	}

	@Override
	public OrderDto deliveryOrder(String orderId) {
		Order order = getOrder(orderId);
		order.setState(OrderState.DELIVERED);
		return orderMapper.toOrderDto(orderRepository.save(order));
	}

	@Override
	public OrderDto deliveryOrderFailed(String orderId) {
		Order order = getOrder(orderId);
		order.setState(OrderState.DELIVERY_FAILED);
		return orderMapper.toOrderDto(orderRepository.save(order));
	}

	@Override
	public OrderDto completedOrder(String orderId) {
		Order order = getOrder(orderId);
		order.setState(OrderState.COMPLETED);
		return orderMapper.toOrderDto(orderRepository.save(order));
	}

	@Override
	public OrderDto calculateTotalOrder(String orderId) {
		return null;
	}

	@Override
	public OrderDto calculateDeliveryOrder(String orderId) {
		return null;
	}

	@Override
	public OrderDto assemblyOrder(String orderId) {
		Order order = getOrder(orderId);
		order.setState(OrderState.ASSEMBLED);
		return orderMapper.toOrderDto(orderRepository.save(order));
	}

	@Override
	public OrderDto assemblyOrderFailed(String orderId) {
		Order order = getOrder(orderId);
		order.setState(OrderState.ASSEMBLY_FAILED);
		return orderMapper.toOrderDto(orderRepository.save(order));
	}


	private Order getOrder(String orderId) {
		Optional<Order> order = orderRepository.findById(orderId);
		if (order.isEmpty())
			throw new NoOrderFoundException("Заказа с id = " + orderId + " не существует");
		return order.get();
	}
}
