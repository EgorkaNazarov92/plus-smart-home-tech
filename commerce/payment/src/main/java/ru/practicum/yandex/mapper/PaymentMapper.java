package ru.practicum.yandex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.yandex.model.Payment;
import ru.yandex.practicum.dto.PaymentDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
	Payment paymentDtoToPayment(PaymentDto dto);

	PaymentDto toPaymentDto(Payment payment);

	List<PaymentDto> mapListPayments(List<Payment> payments);
}
