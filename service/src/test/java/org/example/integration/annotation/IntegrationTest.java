package org.example.integration.annotation;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@AutoConfigureTestDatabase
//@SpringBootTest(classes = {ApplicationRunner.class, TestConfiguration.class})
@Transactional
public @interface IntegrationTest {
}
