package org.example.mapper.productMap;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserRepository;
import org.example.dto.categoryDto.CategoryDto;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.entity.Product;
import org.example.mapper.Mapper;
import org.example.mapper.categoryMap.CategoryDtoMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class ProductDtoMapper implements Mapper<ProductDtoCreateResponse, Product> {
    private final CategoryDtoMapper categoryMapper;
    private final UserRepository userRepository;

    @Override
    public Product mapFrom(ProductDtoCreateResponse object) {
        return Product.builder()
                .productName(object.getName())
                .price(new BigDecimal(object.getPrice()))
                .description(object.getDescription())
                .category(categoryMapper
                        .mapFrom(
                                CategoryDto.builder()
                                        .id(object.getCategory().getId())
                                        .categoryName(object.getCategory().getCategoryName())
                                        .build()
                        ))
                .user(
                        userRepository.findById(object.getUser().id()).get()
                )
                .build();
    }
}
