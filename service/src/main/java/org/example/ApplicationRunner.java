package org.example;

import org.example.service.CategoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        var categoryService = (CategoryService)context.getBean("categoryService");
        System.out.println(categoryService.getCategories());
    }
}
