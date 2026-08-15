package com.hermesafe.domain.valueobject;

public record EstimatedTime(double hours) {
    public EstimatedTime {
        if (hours < 0) {
            throw new IllegalArgumentException("Estimated time cannot be negative");
        }
    }
}
