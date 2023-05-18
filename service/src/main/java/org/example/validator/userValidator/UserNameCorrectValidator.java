package org.example.validator.userValidator;

import org.example.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameCorrectValidator implements ConstraintValidator<UserNameCorrect, String> {
    private static final UserService userService = UserService.getInstance();

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isUniqueUserName(s);
    }
}
