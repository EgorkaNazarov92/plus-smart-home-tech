package ru.practicum.yandex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.yandex.mapper.WarehouseMapper;
import ru.practicum.yandex.model.WarehouseProduct;
import ru.practicum.yandex.repository.WarehouseRepository;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
	private final WarehouseRepository warehouseRepository;
	private final WarehouseMapper warehouseMapper;

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
				.country("Россия")
				.city("Москва")
				.street("Пушкина")
				.house("3")
				.flat("6")
				.build();
	}

	private Optional<WarehouseProduct> getProduct(String productId) {
		return warehouseRepository.findById(productId);
	}
}
