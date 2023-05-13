package org.example.mapper.productMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.categoryDto.CategoryDtoResponse;
import org.example.dto.productDto.ProductDtoResponse;
import org.example.entity.Product;
import org.example.entity.User;
import org.example.mapper.Mapper;
import org.example.mapper.categoryMap.CategoryDtoMapper;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDtoMapper implements Mapper<ProductDtoResponse, Product> {
    private static final ProductDtoMapper INSTANCE = new ProductDtoMapper();
    private static final CategoryDtoMapper categoryMapper = CategoryDtoMapper.getInstance();

    public static ProductDtoMapper getInstance() {
        return INSTANCE;
    }


    @Override
    public Product mapFrom(ProductDtoResponse object) {
        return Product.builder()
                .productName(object.getName())
                .price(new BigDecimal(object.getPrice()))
                .description(object.getDescription())
                .category(categoryMapper
                        .mapFrom(
                                CategoryDtoResponse.builder()
                                        .categoryName(object.getCategory())
                                        .build()
                        ))
                .user(
                        User.builder()
                                .id(Long.valueOf(object.getUser().getId()))
                                .build())
                .build();
    }
}
