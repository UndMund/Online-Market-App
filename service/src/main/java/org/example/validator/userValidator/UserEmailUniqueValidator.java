package org.example.validator.userValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.service.UserService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserEmailUniqueValidator implements ConstraintValidator<UserEmailUnique, String> {
private final UserService userService;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isUniqueUserEmail(s);
    }
}
