package ru.yandex.practicum.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.OrderDto;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderClient {
	@PostMapping("/payment")
	OrderDto paymentOrder(@RequestBody String orderId);

	@PostMapping("/payment/failed")
	OrderDto paymentOrderFailed(@RequestBody String orderId);
}
