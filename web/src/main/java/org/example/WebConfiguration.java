package org.example;

import org.example.servlet.MainServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@ComponentScan(basePackages = "org.example")
@Import(ServiceConfiguration.class)
@EnableSpringConfigured
public class WebConfiguration {
    @Bean("mainServlet")
    public MainServlet getMainServlet() {
        return new MainServlet();
    }
}
