package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.Package;
import com.hermesafe.domain.service.RateCalculator;
import com.hermesafe.domain.valueobject.Distance;
import com.hermesafe.domain.valueobject.ShippingRate;
import com.hermesafe.domain.valueobject.Weight;

public class CalculateShippingRateUseCase {

    private final RateCalculator rateCalculator;

    public CalculateShippingRateUseCase(RateCalculator rateCalculator) {
        if (rateCalculator == null) {
            throw new IllegalArgumentException("Rate calculator cannot be null");
        }
        this.rateCalculator = rateCalculator;
    }

    public ShippingRate execute(Weight weight, Distance distance, boolean isRural) {
        return rateCalculator.calculate(weight, distance, isRural);
    }

    public ShippingRate execute(Package pkg, Distance distance, boolean isRural) {
        return rateCalculator.calculate(pkg, distance, isRural);
    }
}
