package org.example.validator.userValidator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=UserEmailUniqueValidator.class)
public @interface UserEmailUnique {
    String message() default "{email.notUnique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
