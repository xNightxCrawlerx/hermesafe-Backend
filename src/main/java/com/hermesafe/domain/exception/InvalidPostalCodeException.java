package com.hermesafe.domain.exception;

public class InvalidPostalCodeException extends RuntimeException {
    public InvalidPostalCodeException(String message) {
        super(message);
    }
}
