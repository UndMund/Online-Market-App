package org.example.validator.productValidator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ProductUniqueValidator.class)
public @interface ProductUnique {
    String message() default "{product.notUnique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
