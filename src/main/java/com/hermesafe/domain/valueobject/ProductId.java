package com.hermesafe.domain.valueobject;

public record ProductId(String value) {
    public ProductId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }
    }
}
