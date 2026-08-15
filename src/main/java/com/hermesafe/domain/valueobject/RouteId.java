package com.hermesafe.domain.valueobject;

import java.util.UUID;

public record RouteId(String value) {
    public RouteId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Route ID cannot be null or blank");
        }
    }

    public static RouteId generate() {
        return new RouteId(UUID.randomUUID().toString());
    }
}
