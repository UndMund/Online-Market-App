package org.example.validator.userValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginDataCorrectValidator.class)
public @interface LoginDataCorrect {
    String message() default "{login.wrongData}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
