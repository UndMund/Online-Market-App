package org.example.validator.productValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.ProductRepository;
import org.example.dto.productDto.ProductDtoResponse;
import org.example.validator.ValidationResult;
import org.example.validator.Validator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewProductValidator implements Validator<ProductDtoResponse> {
    private static final NewProductValidator INSTANCE = new NewProductValidator();
    private static final ProductAttributesValidator productValidator = ProductAttributesValidator.getInstance();
    private static final ProductRepository productDao = ProductRepository.getInstance();

    public static NewProductValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(ProductDtoResponse object) {
        var validationResult = new ValidationResult();

        /*if (productDao.findByNameAndUser(object.getName(),
                object.getUser().getId())) {
            validationResult.add(Error.of("invalid.data",
                    "Such advertise has already exist"));
            return validationResult;
        }

        if (object.getName().isEmpty() ||
            object.getName().length() < 5) {
            validationResult.add(Error.of("invalid.name",
                    "Name is invalid, it must contains at least 5 symbols"));
        }

        try {
            if (Integer.parseInt(object.getPrice()) == 0) {
                validationResult.add(Error.of("invalid.price",
                        "Price is invalid"));
            }
        } catch (NumberFormatException e) {
            validationResult.add(Error.of("invalid.price",
                    "Price is invalid"));
        }

        if (object.getPrice().isEmpty() ||
            productValidator.validatePrice(object.getPrice())) {
            validationResult.add(Error.of("invalid.price",
                    "Price is invalid"));
        }

        if (object.getDescription().isEmpty() ||
            object.getDescription().length() < 10) {
            validationResult.add(Error.of("invalid.description",
                    "Description is invalid, it must contains at least 10 symbols"));
        }*/
        return validationResult;
    }
}
