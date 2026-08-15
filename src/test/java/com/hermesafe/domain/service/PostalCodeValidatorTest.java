package com.hermesafe.domain.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostalCodeValidatorTest {

    @Test
    void shouldThrowExceptionForNullPostalCode() {
        PostalCodeValidator validator = new PostalCodeValidator();
        assertThrows(IllegalArgumentException.class, () -> validator.isValid(null));
    }

    @Test
    void shouldThrowExceptionForBlankPostalCode() {
        PostalCodeValidator validator = new PostalCodeValidator();
        assertThrows(IllegalArgumentException.class, () -> validator.isValid("   "));
    }

    @Test
    void shouldReturnTrueForValidPostalCode() {
        PostalCodeValidator validator = new PostalCodeValidator();
        assertTrue(validator.isValid("12345"));
    }

    @Test
    void shouldReturnTrueForValidAlphanumericPostalCode() {
        PostalCodeValidator validator = new PostalCodeValidator();
        assertTrue(validator.isValid("A1B2C3"));
    }

    @Test
    void shouldReturnFalseForInvalidPostalCode() {
        PostalCodeValidator validator = new PostalCodeValidator();
        assertFalse(validator.isValid("12@#"));
    }

    @Test
    void shouldReturnFalseForTooLongPostalCode() {
        PostalCodeValidator validator = new PostalCodeValidator();
        assertFalse(validator.isValid("ABCDEFG"));
    }
}
