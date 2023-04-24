package org.example.validator.userValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.UserDaoImpl;
import org.example.dto.positionDto.PositionDto;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.exception.ValidationException;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.example.validator.Validator;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewUserValidator implements Validator<UserDtoRegResponse> {

    public static final NewUserValidator INSTANCE = new NewUserValidator();
    private static final UserAttributesValidator userValidator = UserAttributesValidator.getInstance();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    public static NewUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserDtoRegResponse object) throws ValidationException {
        var validationResult = new ValidationResult();

        List<String> invalidDataMessages = new ArrayList<>();

        if (!userDao.isUniqueUsername(object.getUserName())) {
            invalidDataMessages.add("username");
        }
        if (!userDao.isUniqueEmail(object.getEmail())) {
            invalidDataMessages.add("email");
        }
        if (!userDao.isUniquePhoneNumber(object.getPhoneNumber())) {
            invalidDataMessages.add("phone number");
        }
        if (!invalidDataMessages.isEmpty()) {
            String errorMessage = "User with such data already exists";
            String errors = "(" + String.join(", ", invalidDataMessages) + ")";

            validationResult.add(Error.of("invalid.data",
                    errorMessage + errors));
            return validationResult;
        }


        if (object.getUserName().isEmpty() ||
            object.getUserName().length() > 32) {
            validationResult.add(Error.of("invalid.username",
                    "Username is invalid"));
        }
        if (object.getEmail().isEmpty() ||
            !userValidator.validateEmail(object.getEmail())) {
            validationResult.add(Error.of("invalid.email",
                    "Email is invalid"));
        }

        if (PositionDto.find(object.getPosition()).isEmpty()) {
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
