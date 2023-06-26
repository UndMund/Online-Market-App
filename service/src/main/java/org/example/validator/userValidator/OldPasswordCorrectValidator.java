package org.example.validator.userValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OldPasswordCorrectValidator implements ConstraintValidator<OldPasswordCorrect, UserDtoUpdatePasswordResponse> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UserDtoUpdatePasswordResponse userDtoUpdatePasswordResponse, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByIdAndPassword(
                userDtoUpdatePasswordResponse.getId(),
                userDtoUpdatePasswordResponse.getOldPassword()
        ).isPresent();
    }
}
