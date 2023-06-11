package org.example;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.dto.statusDto.StatusDto;
import org.example.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        var productService = (ProductService)context.getBean("productService");
        System.out.println(productService.getProductsByStatus(StatusDto.ON_SALE));
    }

    @Bean("validator")
    public Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().usingContext().getValidator();
    }

}
