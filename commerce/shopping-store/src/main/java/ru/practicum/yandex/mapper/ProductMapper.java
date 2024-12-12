package ru.practicum.yandex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.yandex.model.Product;
import ru.yandex.practicum.dto.ProductDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

	Product productDtoToProduc(ProductDto productDto);

	ProductDto productToProductDto(Product product);

	List<ProductDto> mapListProducts(List<Product> products);
}
