package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        var sort = Sort.by("price");
        var context = SpringApplication.run(ApplicationRunner.class, args);
    }
}
