package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Configuration
@ComponentScan(basePackages = "org.example")
@Import(RepositoryConfiguration.class)
public class ServiceConfiguration {
    /*@Bean("validatorFactory")
    public ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }*/

    @Bean("validator")
    public Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().usingContext().getValidator();
    }

}
