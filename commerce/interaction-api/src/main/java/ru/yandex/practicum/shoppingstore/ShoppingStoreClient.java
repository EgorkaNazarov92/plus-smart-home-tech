package ru.yandex.practicum.shoppingstore;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.dto.ProductDto;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {
	@GetMapping("/{productId}")
	ProductDto getProductInfo(@PathVariable String productId);
}
