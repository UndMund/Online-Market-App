package org.example.validator.userValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoResponse;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.example.validator.Validator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewUserValidator implements Validator<UserDtoResponse> {

    public static final NewUserValidator INSTANCE = new NewUserValidator();
    private static final UserAttributesValidator userValidator = UserAttributesValidator.getInstance();

    public static NewUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserDtoResponse object) {
        var validationResult = new ValidationResult();
        if (object.getUserName().isEmpty() ||
            object.getUserName().length() > 32) {
            validationResult.add(Error.of("invalid.username",
                    "Username is invalid"));
        }
        if (object.getEmail().isEmpty() ||
            userValidator.validateEmail(object.getEmail())) {
            validationResult.add(Error.of("invalid.email",
                    "Email is invalid"));
        }
        if (object.getPhoneNumber().isEmpty() ||
            userValidator.validatePhoneNumber(object.getPhoneNumber())) {
            validationResult.add(Error.of("invalid.phoneNumber",
                    "Phone number is invalid"));
        }
        if (object.getPassword().isEmpty() ||
            userValidator.validatePassword(object.getPassword())) {
            validationResult.add(Error.of("invalid.password",
                    "Password is invalid, it must contains "));
        }
        return validationResult;
    }
}
