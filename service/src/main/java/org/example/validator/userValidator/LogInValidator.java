package org.example.validator.userValidator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.UserDaoImpl;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRequest;
import org.example.validator.Error;
import org.example.validator.ValidationResult;
import org.example.validator.Validator;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogInValidator implements Validator<UserDtoLoginResponse> {
    private static final LogInValidator INSTANCE = new LogInValidator();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    public static LogInValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserDtoLoginResponse object) {
        var validationResult = new ValidationResult();
        if (userDao.isUniqueUsername(object.getUserName())) {
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
