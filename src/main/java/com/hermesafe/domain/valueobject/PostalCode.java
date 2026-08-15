package com.hermesafe.domain.valueobject;

import com.hermesafe.domain.exception.InvalidPostalCodeException;
import java.util.regex.Pattern;

public record PostalCode(String value) {
    private static final Pattern VALID_PATTERN = Pattern.compile("^[A-Za-z0-9]{5,6}$");

    public PostalCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Postal code cannot be null or blank");
        }
        if (!VALID_PATTERN.matcher(value).matches()) {
            throw new InvalidPostalCodeException("Formato de código postal inválido: " + value);
        }
        value = value.trim();
    }
}
