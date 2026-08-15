package com.hermesafe.domain.service;

import com.hermesafe.domain.entity.Package;
import com.hermesafe.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RateCalculatorTest {

    @Test
    void shouldThrowExceptionForNegativeWeight() {
        RateCalculator calculator = new RateCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(-1, 50, false));
    }

    @Test
    void shouldApplyBaseRateForLightPackage() {
        RateCalculator calculator = new RateCalculator();
        double result = calculator.calculate(1.5, 50, false);
        assertEquals(100.0, result);
    }

    @Test
    void shouldApplyWeightSurcharge() {
        RateCalculator calculator = new RateCalculator();
        double result = calculator.calculate(3.0, 50, false);
        assertEquals(120.0, result);
    }

    @Test
    void shouldApplyRuralSurcharge() {
        RateCalculator calculator = new RateCalculator();
        double result = calculator.calculate(1.5, 50, true);
        assertEquals(115.0, result, 0.0001);
    }

    @Test
    void shouldCalculateRateForPackageEntity() {
        RateCalculator calculator = new RateCalculator();
        Package pkg = new Package(
                PackageId.generate(),
                new Weight(3.0),
                new Dimensions(10, 10, 10),
                new PostalCode("12345")
        );
        Distance dist = new Distance(50);
        ShippingRate rate = calculator.calculate(pkg, dist, true);
        assertEquals(138.0, rate.amount(), 0.0001);
    }
}
