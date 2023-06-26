package org.example.validator.userValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPhoneUniqueValidator implements ConstraintValidator<UserPhoneUnique, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByPhoneNumber(s).isEmpty();
    }
}
