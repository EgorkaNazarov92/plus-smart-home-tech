package ru.practicum.yandex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.service.PaymentService;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
	private final PaymentService paymentService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public PaymentDto addPayment(@RequestBody OrderDto orderDto) {
		log.info("Формирование оплаты для заказа orderDto --> {}", orderDto);
		return paymentService.addPayment(orderDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/totalCost")
	public double getTotalCost(@RequestBody OrderDto orderDto) {
		log.info("Расчёт полной стоимости заказа orderDto --> {}", orderDto);
		return paymentService.getTotalCost(orderDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/refund")
	public void refundPayment(@RequestBody String paymentId) {
		log.info("Метод для эмуляции успешной оплаты в платежного шлюза, paymentId --> {}", paymentId);
		paymentService.refundPayment(paymentId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/productCost")
	public double getProductsCost(@RequestBody OrderDto orderDto) {
		log.info("Расчёт стоимости товаров в заказе, paymentId --> {}", orderDto);
		return paymentService.getProductsCost(orderDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/failed")
	public void failedPayment(@RequestBody String paymentId) {
		log.info("Метод для эмуляции отказа в оплате платежного шлюза, paymentId --> {}", paymentId);
		paymentService.failedPayment(paymentId);
	}
}
