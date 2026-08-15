package com.hermesafe.domain.valueobject;

import com.hermesafe.domain.exception.InvalidDimensionsException;

public record Dimensions(double lengthCm, double widthCm, double heightCm) {
    public Dimensions {
        if (lengthCm <= 0 || widthCm <= 0 || heightCm <= 0) {
            throw new InvalidDimensionsException("Dimensions must be positive");
        }
    }

    public double volumeCm3() {
        return lengthCm * widthCm * heightCm;
    }

    public Weight calculateVolumetricWeight(double divisor) {
        if (divisor <= 0) {
            throw new IllegalArgumentException("Divisor must be positive");
        }
        double volWeight = volumeCm3() / divisor;
        return new Weight(volWeight);
    }

    public Weight calculateVolumetricWeight() {
        return calculateVolumetricWeight(5000.0);
    }
}
