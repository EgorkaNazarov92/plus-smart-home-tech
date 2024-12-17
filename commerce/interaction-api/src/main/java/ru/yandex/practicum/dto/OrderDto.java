package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.types.OrderState;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
	@NotBlank
	private String orderId;

	@NotBlank
	private String shoppingCartId;

	private Map<String, Long> products;

	private String paymentId;

	private String deliveryId;

	private OrderState state;

	private double deliveryWeight;

	private double deliveryVolume;

	private boolean fragile;

	private double totalPrice;

	private double deliveryPrice;

	private double productPrice;
}
