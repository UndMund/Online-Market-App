package org.example.validator.userValidator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserNameUniqueValidator implements ConstraintValidator<UserNameUnique, String> {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByUsername(s).isEmpty();
    }
}
