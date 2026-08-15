package com.hermesafe.domain.valueobject;

public record Distance(int kilometers) {
    public Distance {
        if (kilometers < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
    }
}
