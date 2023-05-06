package org.example.validator.userValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.exception.ValidationException;
import org.example.service.PositionService;
import org.example.service.UserService;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.example.validator.Validator;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewUserValidator implements Validator<UserDtoRegResponse> {

    public static final NewUserValidator INSTANCE = new NewUserValidator();
    private static final UserAttributesValidator userValidator = UserAttributesValidator.getInstance();
    private static final UserService userService = UserService.getInstance();
    private static final PositionService positionService = PositionService.getInstance();

    public static NewUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserDtoRegResponse object) throws ValidationException {
        var validationResult = new ValidationResult();

        List<String> invalidDataMessages = new ArrayList<>();

        if (!userService.isUniqueUsername(object.getUsername())) {
            invalidDataMessages.add("username");
        }
        if (!userService.isUniqueEmail(object.getEmail())) {
            invalidDataMessages.add("email");
        }
        if (!userService.isUniquePhoneNumber(object.getPhoneNumber())) {
            invalidDataMessages.add("phone number");
        }
        if (!invalidDataMessages.isEmpty()) {
            String errorMessage = "User with such data already exists";
            String errors = "(" + String.join(", ", invalidDataMessages) + ")";

            validationResult.add(Error.of("invalid.data",
                    errorMessage + errors));
            return validationResult;
        }


        if (object.getUsername().isEmpty() ||
            object.getUsername().length() > 32) {
            validationResult.add(Error.of("invalid.username",
                    "Username is invalid"));
        }
        if (object.getEmail().isEmpty() ||
            !userValidator.validateEmail(object.getEmail())) {
            validationResult.add(Error.of("invalid.email",
                    "Email is invalid"));
        }

        if (positionService.getPosition(object.getPosition()).isEmpty()) {
            validationResult.add(Error.of("invalid.position",
                    "Position is Invalid"));
        }

        if (object.getPhoneNumber().isEmpty() ||
            !userValidator.validatePhoneNumber(object.getPhoneNumber())) {
            validationResult.add(Error.of("invalid.phoneNumber",
                    "Phone number is invalid"));
        }
        if (object.getPassword().isEmpty() ||
            !userValidator.validatePassword(object.getPassword())) {
            validationResult.add(Error.of("invalid.password",
                    "Password is invalid. It must have one lowercase and one uppercase letter, one number and contain at least 6 characters"));
        }

        return validationResult;
    }
}
