package org.example.validator.userValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OldPasswordCorrectValidator.class)
public @interface OldPasswordCorrect {
    String message() default "{password.wrongData}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
