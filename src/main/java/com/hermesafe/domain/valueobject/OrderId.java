package com.hermesafe.domain.valueobject;

public record OrderId(String value) {
    public OrderId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
    }
}
