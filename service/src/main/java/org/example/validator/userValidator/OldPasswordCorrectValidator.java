package org.example.validator.userValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.dto.userDto.UserDtoUpdatePasswordResponse;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OldPasswordCorrectValidator implements ConstraintValidator<OldPasswordCorrect, UserDtoUpdatePasswordResponse> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(UserDtoUpdatePasswordResponse userDtoUpdatePasswordResponse, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findById(userDtoUpdatePasswordResponse.getId())
                .map(user ->
                        passwordEncoder.matches(
                                userDtoUpdatePasswordResponse.getOldPassword(),
                                user.getPassword()
                        )
                ).orElseThrow();
    }
}
