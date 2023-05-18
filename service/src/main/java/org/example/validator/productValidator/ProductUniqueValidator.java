package org.example.validator.productValidator;

import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.service.ProductService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductUniqueValidator implements ConstraintValidator<ProductUnique, ProductDtoCreateResponse> {
private static final ProductService productService = ProductService.getInstance();
    @Override
    public boolean isValid(ProductDtoCreateResponse productDtoCreateResponse, ConstraintValidatorContext constraintValidatorContext) {
        return productService.isUniqueProduct(productDtoCreateResponse);
    }
}
