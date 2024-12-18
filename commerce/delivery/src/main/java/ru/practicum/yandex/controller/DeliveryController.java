package ru.practicum.yandex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.service.DeliveryService;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {
	private final DeliveryService deliveryService;

	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public DeliveryDto createNewDelivery(@RequestBody DeliveryDto deliveryDto) {
		log.info("Создать новую доставку deliveryDto --> {}", deliveryDto);
		return deliveryService.createNewDelivery(deliveryDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/successful")
	public void setDeliverySuccess(@RequestBody String deliveryId) {
		log.info("Эмуляция успешной доставки товара, deliveryId --> {}", deliveryId);
		deliveryService.setDeliverySuccess(deliveryId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/picked")
	public void setDeliveryPicked(@RequestBody String deliveryId) {
		log.info("Эмуляция получения товара в доставку, deliveryId --> {}", deliveryId);
		deliveryService.setDeliveryPicked(deliveryId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/failed")
	public void setDeliveryFailed(@RequestBody String deliveryId) {
		log.info("Эмуляция неудачного вручения товара, deliveryId --> {}", deliveryId);
		deliveryService.setDeliveryFailed(deliveryId);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/cost")
	public Double getCostDelivery(@RequestBody OrderDto orderDto) {
		log.info("Расчёт полной стоимости доставки заказа, orderDto --> {}", orderDto);
		return deliveryService.getCostDelivery(orderDto);
	}
}
