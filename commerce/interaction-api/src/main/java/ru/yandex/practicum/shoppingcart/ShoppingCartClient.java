package ru.yandex.practicum.shoppingcart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.dto.ShoppingCartDto;


@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart")
public interface ShoppingCartClient {
	@GetMapping
	ShoppingCartDto getShoppingCart(@RequestParam String username);
}