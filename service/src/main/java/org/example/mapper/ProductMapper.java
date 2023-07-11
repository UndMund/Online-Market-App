package org.example.mapper;

import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        uses = {CategoryMapper.class, UserMapper.class, StatusMapper.class, ImageMapper.class})
public interface ProductMapper {
    Product toProduct(ProductDtoCreateResponse productDto);

    Product toProduct(ProductDtoRequest productDto);

    ProductDtoRequest toProductDto(Product product);
}
