package com.hermesafe.domain.valueobject;

import com.hermesafe.domain.exception.InvalidDimensionsException;
import com.hermesafe.domain.exception.InvalidPostalCodeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @Test
    void shouldCreateValidPostalCode() {
        PostalCode code = new PostalCode("12345");
        assertEquals("12345", code.value());
    }

    @Test
    void shouldThrowForInvalidPostalCodePattern() {
        assertThrows(InvalidPostalCodeException.class, () -> new PostalCode("INVALID_CODE_123"));
    }

    @Test
    void shouldCreateValidWeight() {
        Weight weight = new Weight(2.5);
        assertEquals(2.5, weight.value());

        Weight weight2 = new Weight(1.5);
        Weight total = weight.plus(weight2);
        assertEquals(4.0, total.value());
    }

    @Test
    void shouldThrowForNegativeWeight() {
        assertThrows(IllegalArgumentException.class, () -> new Weight(-1.0));
        assertThrows(IllegalArgumentException.class, () -> new Weight(0.0));
    }

    @Test
    void shouldCreateValidDistance() {
        Distance dist = new Distance(100);
        assertEquals(100, dist.kilometers());
    }

    @Test
    void shouldThrowForNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Distance(-10));
    }

    @Test
    void shouldCreateValidProductIdAndOrderId() {
        ProductId pid = new ProductId("PROD-1");
        OrderId oid = new OrderId("ORD-1");
        assertEquals("PROD-1", pid.value());
        assertEquals("ORD-1", oid.value());
    }

    @Test
    void shouldCreateAndValidateNewIdValueObjects() {
        PackageId pkgId = PackageId.generate();
        assertNotNull(pkgId.value());

        CustomerId custId = CustomerId.generate();
        assertNotNull(custId.value());

        WarehouseId whId = WarehouseId.generate();
        assertNotNull(whId.value());

        RouteId rId = RouteId.generate();
        assertNotNull(rId.value());

        assertThrows(IllegalArgumentException.class, () -> new PackageId(null));
        assertThrows(IllegalArgumentException.class, () -> new CustomerId("   "));
        assertThrows(IllegalArgumentException.class, () -> new WarehouseId(null));
        assertThrows(IllegalArgumentException.class, () -> new RouteId(""));
    }

    @Test
    void shouldCreateAndCalculateDimensions() {
        Dimensions dims = new Dimensions(10, 20, 30);
        assertEquals(6000.0, dims.volumeCm3());

        Weight volWeight = dims.calculateVolumetricWeight(5000.0);
        assertEquals(1.2, volWeight.value());

        assertThrows(InvalidDimensionsException.class, () -> new Dimensions(-1, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> dims.calculateVolumetricWeight(0));
    }

    @Test
    void shouldCreateLocationAndEstimatedTime() {
        Location loc = new Location("Santiago", "Av. Providencia 1234");
        assertEquals("Santiago", loc.city());
        assertEquals("Av. Providencia 1234", loc.address());

        EstimatedTime time = new EstimatedTime(2.5);
        assertEquals(2.5, time.hours());

        assertThrows(IllegalArgumentException.class, () -> new Location(null, "Address"));
        assertThrows(IllegalArgumentException.class, () -> new Location("City", " "));
        assertThrows(IllegalArgumentException.class, () -> new EstimatedTime(-1));
    }

    @Test
    void shouldPerformShippingRateArithmetic() {
        ShippingRate rate1 = new ShippingRate(100.0);
        ShippingRate rate2 = new ShippingRate(50.0);

        ShippingRate total = rate1.add(rate2);
        assertEquals(150.0, total.amount());

        ShippingRate surcharged = rate1.applySurcharge(15.0);
        assertEquals(115.0, surcharged.amount(), 0.0001);

        ShippingRate multiplied = rate1.multiply(2.0);
        assertEquals(200.0, multiplied.amount());

        assertThrows(IllegalArgumentException.class, () -> rate1.add(null));
        assertThrows(IllegalArgumentException.class, () -> rate1.applySurcharge(-5));
        assertThrows(IllegalArgumentException.class, () -> rate1.multiply(-1));
    }
}
