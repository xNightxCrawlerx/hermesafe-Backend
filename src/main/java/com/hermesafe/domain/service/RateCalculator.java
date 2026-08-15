package com.hermesafe.domain.service;

import com.hermesafe.domain.entity.Package;
import com.hermesafe.domain.valueobject.Distance;
import com.hermesafe.domain.valueobject.ShippingRate;
import com.hermesafe.domain.valueobject.Weight;

public class RateCalculator {

    private static final double BASE_RATE = 100.0;

    public double calculate(double weight, int distance, boolean isRural) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive :)");
        }

        double price = BASE_RATE;

        if (weight > 2) {
            price += 20; // surcharge for heavy packages
        }

        if (isRural) {
            price *= 1.15; // rural surcharge
        }

        return price;
    }

    public ShippingRate calculate(Weight weight, Distance distance, boolean isRural) {
        double amount = calculate(weight.value(), distance.kilometers(), isRural);
        return new ShippingRate(amount);
    }

    public ShippingRate calculate(Package pkg, Distance distance, boolean isRural) {
        if (pkg == null) {
            throw new IllegalArgumentException("Package cannot be null");
        }
        Weight billableWeight = pkg.calculateBillableWeight();
        return calculate(billableWeight, distance, isRural);
    }
}
