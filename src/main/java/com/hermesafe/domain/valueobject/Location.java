package com.hermesafe.domain.valueobject;

public record Location(String city, String address) {
    public Location {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank");
        }
    }
}
