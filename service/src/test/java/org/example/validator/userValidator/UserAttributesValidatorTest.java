package org.example.validator.userValidator;

import org.example.validator.Validator;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserAttributesValidatorTest {
private static final UserAttributesValidator validator = UserAttributesValidator.getInstance();
    @Test
    public void validateEmail() {
        assertTrue(validator.validateEmail("zavadskiyNazar@mail.ru"));
    }

    @Test
    public void validateWrongEmail() {
        assertFalse(validator.validateEmail("zavadskiyNazarmail."));
    }

    @Test
    public void validatePhoneNumber() {
        assertTrue(validator.validatePhoneNumber("+375336328517"));
    }
    @Test
    public void validateWrongPhoneNumber() {
        assertFalse(validator.validatePhoneNumber("+37599628517"));
    }

    @Test
    public void validatePassword() {
        assertTrue(validator.validatePassword("Nazar17"));
    }

    @Test
    public void validateWrongPassword() {
        assertFalse(validator.validatePassword("nazar45"));
    }
}