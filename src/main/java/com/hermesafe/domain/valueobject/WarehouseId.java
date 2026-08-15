package com.hermesafe.domain.valueobject;

import java.util.UUID;

public record WarehouseId(String value) {
    public WarehouseId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Warehouse ID cannot be null or blank");
        }
    }

    public static WarehouseId generate() {
        return new WarehouseId(UUID.randomUUID().toString());
    }
}
