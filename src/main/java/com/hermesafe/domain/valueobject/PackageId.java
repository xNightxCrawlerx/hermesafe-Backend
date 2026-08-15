package com.hermesafe.domain.valueobject;

import java.util.UUID;

public record PackageId(String value) {
    public PackageId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Package ID cannot be null or blank");
        }
    }

    public static PackageId generate() {
        return new PackageId(UUID.randomUUID().toString());
    }
}
