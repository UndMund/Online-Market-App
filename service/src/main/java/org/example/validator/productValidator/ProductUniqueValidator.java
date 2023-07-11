package org.example.validator.productValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class ProductUniqueValidator implements ConstraintValidator<ProductUnique, ProductDtoCreateResponse> {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean isValid(ProductDtoCreateResponse productDtoCreateResponse, ConstraintValidatorContext constraintValidatorContext) {
        return productRepository.findByProductNameAndUserId(
                productDtoCreateResponse.getProductName(),
                productDtoCreateResponse.getUserId()
        ).isEmpty();
    }
}
