package ru.practicum.yandex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.yandex.mapper.BookingMapper;
import ru.practicum.yandex.mapper.WarehouseMapper;
import ru.practicum.yandex.model.Booking;
import ru.practicum.yandex.model.WarehouseProduct;
import ru.practicum.yandex.repository.BookingRepository;
import ru.practicum.yandex.repository.WarehouseRepository;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
	private final WarehouseRepository warehouseRepository;
	private final WarehouseMapper warehouseMapper;
	private final BookingRepository bookingRepository;
	private final BookingMapper bookingMapper;

	@Transactional
	@Override
	public void createProductToWarehouse(NewProductInWarehouseRequest request) {
		Optional<WarehouseProduct> product = getProduct(request.getProductId());
		if (product.isPresent())
			throw new SpecifiedProductAlreadyInWarehouseException("Такой продукт уже есть в БД");
		warehouseRepository.save(warehouseMapper.toWarehouse(request));
	}

	@Override
	public BookedProductsDto checkAvailableProducts(ShoppingCartDto shoppingCartDto) {
		Map<String, Long> products = shoppingCartDto.getProducts();
		List<WarehouseProduct> warehouseProducts = warehouseRepository.findAllById(products.keySet());
		warehouseProducts.forEach(warehouseProduct -> {
			if (warehouseProduct.getQuantity() < products.get(warehouseProduct.getProductId()))
				throw new ProductInShoppingCartLowQuantityInWarehouse("Не достаточно товара на складе");
		});

		double deliveryWeight = warehouseProducts.stream()
				.map(WarehouseProduct::getWeight)
				.mapToDouble(Double::doubleValue)
				.sum();

		double deliveryVolume = warehouseProducts.stream()
				.map(warehouseProduct -> warehouseProduct.getDimension().getDepth()
						* warehouseProduct.getDimension().getHeight() * warehouseProduct.getDimension().getWidth())
				.mapToDouble(Double::doubleValue)
				.sum();

		boolean fragile = warehouseProducts.stream()
				.anyMatch(WarehouseProduct::isFragile);
		return BookedProductsDto.builder()
				.deliveryWeight(deliveryWeight)
				.deliveryVolume(deliveryVolume)
				.fragile(fragile)
				.build();
	}

	@Transactional
	@Override
	public void addProductToWarehouse(AddProductToWarehouseRequest request) {
		Optional<WarehouseProduct> product = getProduct(request.getProductId());
		if (product.isEmpty())
			throw new NoSpecifiedProductInWarehouseException("Нет такого товара");
		WarehouseProduct pr = product.get();
		pr.setQuantity(pr.getQuantity() + request.getQuantity());
		warehouseRepository.save(pr);
	}

	@Override
	public AddressDto getWareHouseAddress() {
		return AddressDto.builder()
				.country(CURRENT_ADDRESS)
				.city(CURRENT_ADDRESS)
				.street(CURRENT_ADDRESS)
				.house(CURRENT_ADDRESS)
				.flat(CURRENT_ADDRESS)
				.build();
	}

	@Override
	public BookedProductsDto bookProducts(AssemblyProductsForOrderRequest request) {
		Map<String, Long> products = request.getProducts();
		List<WarehouseProduct> warehouseProducts = warehouseRepository.findAllById(products.keySet());
		warehouseProducts.forEach(warehouseProduct -> {
			if (warehouseProduct.getQuantity() < products.get(warehouseProduct.getProductId()))
				throw new ProductInShoppingCartLowQuantityInWarehouse("Не достаточно товара на складе");
			warehouseProduct.setQuantity(warehouseProduct.getQuantity() - products.get(warehouseProduct.getProductId()));
			warehouseRepository.save(warehouseProduct);
		});

		double deliveryWeight = warehouseProducts.stream()
				.map(WarehouseProduct::getWeight)
				.mapToDouble(Double::doubleValue)
				.sum();

		double deliveryVolume = warehouseProducts.stream()
				.map(warehouseProduct -> warehouseProduct.getDimension().getDepth()
						* warehouseProduct.getDimension().getHeight() * warehouseProduct.getDimension().getWidth())
				.mapToDouble(Double::doubleValue)
				.sum();

		boolean fragile = warehouseProducts.stream()
				.anyMatch(WarehouseProduct::isFragile);

		Booking booking = Booking.builder()
				.orderId(request.getOrderId())
				.fragile(fragile)
				.deliveryVolume(deliveryVolume)
				.deliveryWeight(deliveryWeight)
				.products(products)
				.build();
		return bookingMapper.bookingToBookedProductsDto(bookingRepository.save(booking));
	}

	@Override
	public void shippedToDelivery(ShippedToDeliveryRequest deliveryRequest) {
		Booking booking = getBooking(deliveryRequest.getOrderId());
		booking.setDeliveryId(deliveryRequest.getDeliveryId());
		bookingRepository.save(booking);
	}

	@Override
	public void returnProductsToWarehouse(Map<String, Long> products) {
		products.forEach((productId, quantity) -> {
			Optional<WarehouseProduct> product = warehouseRepository.getByProductId(productId);
			if (product.isEmpty())
				throw new NoSpecifiedProductInWarehouseException("нет такого продукта");
			WarehouseProduct warehouseProduct = product.get();
			warehouseProduct.setQuantity(warehouseProduct.getQuantity() + quantity);
			warehouseRepository.save(warehouseProduct);
		});

	}

	private Booking getBooking(String orderId) {
		Optional<Booking> booking = bookingRepository.findById(orderId);
		if (booking.isEmpty())
			throw new NoSpecifiedProductInWarehouseException("нет такого бронирования");
		return booking.get();
	}

	private static final String[] ADDRESSES =
			new String[]{"ADDRESS_1", "ADDRESS_2"};

	private static final String CURRENT_ADDRESS =
			ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

	private Optional<WarehouseProduct> getProduct(String productId) {
		return warehouseRepository.findById(productId);
	}
}
