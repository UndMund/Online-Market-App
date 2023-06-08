package org.example;

import org.example.dao.UserRepository;
import org.example.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        var userRep = context.getBean("userRepository", UserRepository.class);
        var userService = context.getBean("userService", UserService.class);
        System.out.println(userRep.findById(1L));
        System.out.println(userService.isUniqueUserName("Nazar"));

    }
}
