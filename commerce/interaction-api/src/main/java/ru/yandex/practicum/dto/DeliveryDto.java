package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.types.DeliveryState;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDto {
	@NotBlank
	private String deliveryId;

	@NotBlank
	private AddressDto fromAddress;

	@NotBlank
	private AddressDto toAddress;

	@NotBlank
	private String orderId;

	@NotBlank
	private DeliveryState deliveryState;
}
