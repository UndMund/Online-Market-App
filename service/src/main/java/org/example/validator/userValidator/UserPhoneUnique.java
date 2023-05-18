package org.example.validator.userValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=UserPhoneUniqueValidator.class)
public @interface UserPhoneUnique {
    String message() default "{phone.notUnique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
