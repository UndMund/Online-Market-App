package org.example.integration.anotation;

import org.example.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@SpringBootTest(classes = ApplicationRunner.class)
@Transactional
public @interface IntegrationTest {
}
