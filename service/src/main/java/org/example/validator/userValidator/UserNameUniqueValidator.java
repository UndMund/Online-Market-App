package org.example.validator.userValidator;

import lombok.RequiredArgsConstructor;
import org.example.service.UserService;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
@Component
@RequiredArgsConstructor
public class UserNameUniqueValidator implements ConstraintValidator<UserNameUnique, String> {
    private final UserService userService;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isUniqueUserName(s);
    }
}
