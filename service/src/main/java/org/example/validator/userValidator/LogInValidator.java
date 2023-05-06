package org.example.validator.userValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.service.UserService;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.example.validator.Validator;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogInValidator implements Validator<UserDtoLoginResponse> {
    private static final LogInValidator INSTANCE = new LogInValidator();
    private static final UserService userService = UserService.getInstance();

    public static LogInValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserDtoLoginResponse object) {
        var validationResult = new ValidationResult();
        if (userService.isUniqueUsername(object.getUsername())) {
            validationResult.add(Error.of("invalid.username",
                    "Such user does not exist"));
        }
        return validationResult;
    }

    public ValidationResult isValidPassword(Optional<UserDtoRequest> optionalUserDto, ValidationResult validationResult) {
        if (optionalUserDto.isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        return validationResult;
    }
}
