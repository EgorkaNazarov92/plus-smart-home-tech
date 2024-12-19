package ru.yandex.practicum.warehouse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.*;

import java.util.Map;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {
	@PostMapping("/check")
	BookedProductsDto checkAvailableProducts(@RequestBody ShoppingCartDto shoppingCartDto);

	@PostMapping("/assembly")
	BookedProductsDto bookProducts(@RequestBody AssemblyProductsForOrderRequest request);

	@GetMapping("/address")
	AddressDto getWareHouseAddress();

	@PostMapping("/shipped")
	void shippedToDelivery(@RequestBody ShippedToDeliveryRequest deliveryRequest);

	@PostMapping("/return")
	void returnProductsToWarehouse(@RequestBody Map<String, Long> products);
}
