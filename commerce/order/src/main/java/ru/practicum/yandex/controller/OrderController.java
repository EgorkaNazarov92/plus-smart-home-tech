package ru.practicum.yandex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.service.OrderService;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
	private final OrderService orderService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<OrderDto> getUserOrders(@RequestParam String username) {
		log.info("Пришел запрос на получения заказов пользователся: username --> {}", username);
		return orderService.getUserOrders(username);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public OrderDto createNewOrder(@RequestBody CreateNewOrderRequest request) {
		log.info("Создать новый заказ request --> {}", request);
		return orderService.createNewOrder(request);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/return")
	public OrderDto returnOrder(@RequestBody ProductReturnRequest request) {
		log.info("Запрос на возврат товара request --> {}", request);
		return orderService.returnOrder(request);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/payment")
	public OrderDto paymentOrder(@RequestBody String orderId) {
		log.info("Запрос на оплату, orderId --> {}", orderId);
		return orderService.paymentOrder(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/payment/failed")
	public OrderDto paymentOrderFailed(@RequestBody String orderId) {
		log.info("Запрос на оплату с ошибкой, orderId --> {}", orderId);
		return orderService.paymentOrderFailed(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/delivery")
	public OrderDto deliveryOrder(@RequestBody String orderId) {
		log.info("Запрос на доставку, orderId --> {}", orderId);
		return orderService.deliveryOrder(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/delivery/failed")
	public OrderDto deliveryOrderFailed(@RequestBody String orderId) {
		log.info("Запрос на доставку с ошибкой, orderId --> {}", orderId);
		return orderService.deliveryOrderFailed(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/completed")
	public OrderDto completedOrder(@RequestBody String orderId) {
		log.info("Заверешние заказа, orderId --> {}", orderId);
		return orderService.completedOrder(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/calculate/total")
	public OrderDto calculateTotalOrder(@RequestBody String orderId) {
		log.info("Расчет стоимости заказа, orderId --> {}", orderId);
		return orderService.calculateTotalOrder(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/calculate/delivery")
	public OrderDto calculateDeliveryOrder(@RequestBody String orderId) {
		log.info("Расчет стоимости доставки, orderId --> {}", orderId);
		return orderService.calculateDeliveryOrder(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/assembly")
	public OrderDto assemblyOrder(@RequestBody String orderId) {
		log.info("Сборка заказа, orderId --> {}", orderId);
		return orderService.assemblyOrder(orderId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/assembly.афшдув")
	public OrderDto assemblyOrderFailed(@RequestBody String orderId) {
		log.info("Сборка заказа с ошибкой, orderId --> {}", orderId);
		return orderService.assemblyOrderFailed(orderId);
	}
}
