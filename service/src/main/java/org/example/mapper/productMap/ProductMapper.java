package org.example.mapper.productMap;

import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.entity.Product;
import org.example.mapper.Mapper;
import org.example.mapper.categoryMap.CategoryMapper;
import org.example.mapper.statusMap.StatusMapper;
import org.example.mapper.userMap.UserMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductMapper implements Mapper<Product, ProductDtoRequest> {
    private final CategoryMapper categoryMapper;
    private final StatusMapper statusMapper;
    private final UserMapper userMapper;
    public ProductDtoRequest mapFrom(Product object) {
        return ProductDtoRequest.builder()
                .id(object.getId())
                .name(object.getProductName())
                .price(object.getPrice())
                .description(object.getDescription())
                .category(categoryMapper.mapFrom(object.getCategory()))
                .status(statusMapper.mapFrom(object.getStatus()))
                .user(userMapper.mapFrom(object.getUser()))
                .build();
    }
}
