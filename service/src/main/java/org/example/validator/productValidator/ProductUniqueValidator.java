package org.example.validator.productValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductUniqueValidator implements ConstraintValidator<ProductUnique, ProductDtoCreateResponse> {
private final ProductRepository productRepository;
    @Override
    public boolean isValid(ProductDtoCreateResponse productDtoCreateResponse, ConstraintValidatorContext constraintValidatorContext) {
        return !productRepository.findByProductNameAndUserId(
                productDtoCreateResponse.getName(),
                productDtoCreateResponse.getUser().id()
        );
    }
}
