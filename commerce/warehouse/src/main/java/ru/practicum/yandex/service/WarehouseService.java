package ru.practicum.yandex.service;

import ru.yandex.practicum.dto.*;

public interface WarehouseService {
	void createProductToWarehouse(NewProductInWarehouseRequest request);

	BookedProductsDto checkAvailableProducts(ShoppingCartDto shoppingCartDto);

	void addProductToWarehouse(AddProductToWarehouseRequest request);

	AddressDto getWareHouseAddress();

	BookedProductsDto bookedProducts(ShoppingCartDto shoppingCartDto);
}
