package ru.practicum.yandex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.yandex.model.Booking;
import ru.yandex.practicum.dto.BookedProductsDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
	BookedProductsDto bookingToBookedProductsDto(Booking booking);
}
