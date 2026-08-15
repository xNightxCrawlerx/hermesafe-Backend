package com.hermesafe.domain.valueobject;

public record ShippingRate(double amount) {
    public ShippingRate {
        if (amount < 0) {
            throw new IllegalArgumentException("Shipping rate amount cannot be negative");
        }
        amount = Math.round(amount * 100.0) / 100.0;
    }

    public ShippingRate add(ShippingRate other) {
        if (other == null) {
            throw new IllegalArgumentException("Rate to add cannot be null");
        }
        return new ShippingRate(this.amount + other.amount);
    }

    public ShippingRate applySurcharge(double percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Surcharge percentage cannot be negative");
        }
        return new ShippingRate(this.amount * (1.0 + percentage / 100.0));
    }

    public ShippingRate multiply(double factor) {
        if (factor < 0) {
            throw new IllegalArgumentException("Factor cannot be negative");
        }
        return new ShippingRate(this.amount * factor);
    }
}
