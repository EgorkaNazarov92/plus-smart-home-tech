package ru.practicum.yandex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.service.WarehouseService;
import ru.yandex.practicum.dto.*;

import java.util.Map;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {
	private final WarehouseService warehouseService;

	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void createProductToWarehouse(@RequestBody NewProductInWarehouseRequest request) {
		log.info("Добавить новый товар на склад, request --> {}", request);
		warehouseService.createProductToWarehouse(request);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/shipped")
	public void shippedToDelivery(@RequestBody ShippedToDeliveryRequest deliveryRequest) {
		log.info("Передать товары в доставку, deliveryRequest --> {}", deliveryRequest);
		warehouseService.shippedToDelivery(deliveryRequest);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/return")
	public void returnProductsToWarehouse(@RequestBody Map<String, Long> products) {
		log.info("Принять возврат товаров на склад, products --> {}", products);
		warehouseService.returnProductsToWarehouse(products);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/check")
	public BookedProductsDto checkAvailableProducts(@RequestBody ShoppingCartDto shoppingCartDto) {
		log.info("Запрос, на проверку количества товаров shoppingCartDto --> {}", shoppingCartDto);
		return warehouseService.checkAvailableProducts(shoppingCartDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/assembly")
	public BookedProductsDto bookProducts(@RequestBody AssemblyProductsForOrderRequest request) {
		log.info("Собрать товары к заказу для подготовки к отправке, request --> {}", request);
		return warehouseService.bookProducts(request);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/add")
	public void addProductToWarehouse(@RequestBody AddProductToWarehouseRequest request) {
		log.info("Запрос, на добавление товара на склад, request --> {}", request);
		warehouseService.addProductToWarehouse(request);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/address")
	public AddressDto getWareHouseAddress() {
		log.info("Пришел запрос, на получения адреса склада для расчёта доставки.");
		return warehouseService.getWareHouseAddress();
	}
}
