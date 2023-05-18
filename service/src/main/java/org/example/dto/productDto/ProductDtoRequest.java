package org.example.dto.productDto;

import lombok.Builder;
import lombok.Value;
import org.example.dto.categoryDto.CategoryDto;
import org.example.dto.statusDto.StatusDto;
import org.example.dto.userDto.UserDtoRequest;

import java.math.BigDecimal;

@Value
@Builder
public class ProductDtoRequest {
    Long id;
    String name;
    BigDecimal price;
    String description;
    StatusDto status;
    CategoryDto category;
    UserDtoRequest user;
}
