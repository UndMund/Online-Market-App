package org.example.validator.productValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.service.ProductService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductUniqueValidator implements ConstraintValidator<ProductUnique, ProductDtoCreateResponse> {
private final ProductService productService;
    @Override
    public boolean isValid(ProductDtoCreateResponse productDtoCreateResponse, ConstraintValidatorContext constraintValidatorContext) {
        return productService.isUniqueProduct(productDtoCreateResponse);
    }
}
