package org.example.mapper.productMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.entity.Product;
import org.example.mapper.Mapper;
import org.example.mapper.categoryMap.CategoryMapper;
import org.example.mapper.statusMap.StatusMapper;
import org.example.mapper.userMap.CreateUserDtoMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper implements Mapper<Product, ProductDtoRequest> {
    private static final ProductMapper INSTANCE = new ProductMapper();
    private static final CategoryMapper categoryDtoMapper = CategoryMapper.getInstance();
    private static final CreateUserDtoMapper userMapper = CreateUserDtoMapper.getInstance();
    private static final StatusMapper CREATE_STATUS_DTO_MAPPER = StatusMapper.getInstance();

    public static ProductMapper getInstance() {
        return INSTANCE;
    }
    public ProductDtoRequest mapFrom(Product object) {
        return ProductDtoRequest.builder()
                .id(object.getId())
                .name(object.getProductName())
                .price(object.getPrice())
                .description(object.getDescription())
                .status(CREATE_STATUS_DTO_MAPPER.mapFrom(object.getStatus()))
                //.category(categoryDtoMapper.mapFrom(object.getCategory()))
                .user(userMapper.mapFrom(object.getUser()))
                .build();
    }
}
