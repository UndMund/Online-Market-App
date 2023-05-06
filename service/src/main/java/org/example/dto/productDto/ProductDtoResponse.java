package org.example.dto.productDto;

import lombok.Builder;
import lombok.Value;
import org.example.dto.userDto.UserDtoCreateProductResponse;

@Value
@Builder
public class ProductDtoResponse {
    String name;
    String price;
    String description;
    String category;
    UserDtoCreateProductResponse user;
}
