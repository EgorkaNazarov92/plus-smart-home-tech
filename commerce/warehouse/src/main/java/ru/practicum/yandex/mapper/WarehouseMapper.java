package ru.practicum.yandex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.yandex.model.WarehouseProduct;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseMapper {
	WarehouseProduct toWarehouse(NewProductInWarehouseRequest request);
}
