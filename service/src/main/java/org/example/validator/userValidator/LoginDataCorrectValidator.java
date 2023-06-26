package org.example.validator.userValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.dto.userDto.UserDtoLogResponse;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginDataCorrectValidator implements ConstraintValidator<LoginDataCorrect, UserDtoLogResponse> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UserDtoLogResponse userDtoLogResponse, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository
                .findByUsernameAndPassword(
                        userDtoLogResponse.getUsername(),
                        userDtoLogResponse.getPassword()
                )
                .isPresent();
    }
}
