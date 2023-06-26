package org.example.mapper;

import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, StatusMapper.class})
public interface ProductMapper {
    @Mapping(target = "productName", source = "name")
    Product toProduct(ProductDtoCreateResponse productDto);
    @Mapping(target = "name", source = "productName")
    ProductDtoRequest toProductDto(Product product);
}
