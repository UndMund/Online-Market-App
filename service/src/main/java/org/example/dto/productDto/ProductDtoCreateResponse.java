package org.example.dto.productDto;

import lombok.Builder;
import lombok.Value;
import org.example.dto.categoryDto.CategoryDto;
import org.example.dto.userDto.UserDtoCreateProductResponse;
import org.example.validator.productValidator.ProductUnique;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Value
@Builder
@ProductUnique
public class ProductDtoCreateResponse {
    @Size(min = 5, max = 30, message = "Name must be 5-30 characters long")
    String name;
    @Digits(integer = 5, fraction = 0, message = "Max price is 99999")
    @DecimalMin(value = "1", message = "Price must be more than 0")
    String price;
    @Size(min = 20, max = 300, message = "Description must be 20-300 characters long")
    String description;
    CategoryDto category;
    UserDtoCreateProductResponse user;
}
