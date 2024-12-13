package ru.practicum.yandex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.yandex.model.ShoppingCart;
import ru.yandex.practicum.dto.ShoppingCartDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
	ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart);
}
