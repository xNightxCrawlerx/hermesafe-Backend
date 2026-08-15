package com.hermesafe.domain.valueobject;

public record Weight(double value) {
    public Weight {
        if (value <= 0) {
            throw new IllegalArgumentException("Weight must be positive :)");
        }
    }

    public Weight plus(Weight other) {
        if (other == null) {
            throw new IllegalArgumentException("Weight to add cannot be null");
        }
        return new Weight(this.value + other.value);
    }
}
