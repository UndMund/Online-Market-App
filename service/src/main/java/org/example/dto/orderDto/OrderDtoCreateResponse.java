package org.example.dto.orderDto;

import lombok.Builder;
import lombok.Value;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.userDto.UserDtoRequest;

@Value
@Builder
public class OrderDtoCreateResponse {
    ProductDtoRequest product;
    UserDtoRequest user;
}
