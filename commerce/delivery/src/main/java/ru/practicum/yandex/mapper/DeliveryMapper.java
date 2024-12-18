package ru.practicum.yandex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.yandex.model.Delivery;
import ru.yandex.practicum.dto.DeliveryDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {
	Delivery deliveryDtoToDelivery(DeliveryDto dto);

	DeliveryDto toDeliveryDto(Delivery delivery);

	List<DeliveryDto> mapListDeliveries(List<Delivery> deliveries);
}
