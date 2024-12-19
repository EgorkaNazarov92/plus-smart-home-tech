package ru.practicum.yandex.service;

import ru.yandex.practicum.dto.*;

import java.util.Map;

public interface WarehouseService {
	void createProductToWarehouse(NewProductInWarehouseRequest request);

	BookedProductsDto checkAvailableProducts(ShoppingCartDto shoppingCartDto);

	void addProductToWarehouse(AddProductToWarehouseRequest request);

	AddressDto getWareHouseAddress();

	BookedProductsDto bookProducts(AssemblyProductsForOrderRequest request);

	void shippedToDelivery(ShippedToDeliveryRequest deliveryRequest);

	void returnProductsToWarehouse(Map<String, Long> products);
}
