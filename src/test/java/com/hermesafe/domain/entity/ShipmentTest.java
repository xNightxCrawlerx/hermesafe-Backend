package com.hermesafe.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    @Test
    @DisplayName("Should create valid shipment instance")
    void shouldCreateValidShipment() {
        Shipment shipment = new Shipment(
                "ENV-1001",
                "HMS-849201-CL",
                "TechSolutions Chile SpA",
                "Ignacio Morales Vera",
                "Santiago",
                "Concepción",
                "IN_TRANSIT",
                "EXPRESS",
                4.5,
                "2026-08-08",
                "2026-08-06",
                "Manejar con cuidado.",
                true
        );

        assertNotNull(shipment.getId());
        assertEquals("ENV-1001", shipment.getId());
        assertEquals("HMS-849201-CL", shipment.getTrackingCode());
        assertEquals("TechSolutions Chile SpA", shipment.getSenderName());
        assertEquals("Ignacio Morales Vera", shipment.getRecipientName());
        assertEquals("Santiago", shipment.getOriginCity());
        assertEquals("Concepción", shipment.getDestinationCity());
        assertEquals("IN_TRANSIT", shipment.getStatus());
        assertEquals("EXPRESS", shipment.getPriority());
        assertEquals(4.5, shipment.getWeightKg());
        assertTrue(shipment.isPriorityFeatured());
    }

    @Test
    @DisplayName("Should throw exception when sender name is empty")
    void shouldThrowExceptionWhenSenderNameIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Shipment("ENV-1001", "HMS-849201-CL", "", "Recipient", "Santiago", "Concepción", "PENDING", "STANDARD", 2.0, null, null, null, false)
        );
        assertEquals("Sender name cannot be null or empty", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when weight is invalid")
    void shouldThrowExceptionWhenWeightIsZeroOrNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Shipment("ENV-1001", "HMS-849201-CL", "Sender", "Recipient", "Santiago", "Concepción", "PENDING", "STANDARD", 0.0, null, null, null, false)
        );
        assertEquals("Weight must be greater than 0", ex.getMessage());
    }

    @Test
    @DisplayName("Should update status correctly")
    void shouldUpdateStatusCorrectly() {
        Shipment shipment = new Shipment(
                "ENV-1001",
                "HMS-849201-CL",
                "Sender",
                "Recipient",
                "Santiago",
                "Concepción",
                "PENDING",
                "STANDARD",
                2.0,
                null,
                null,
                null,
                false
        );

        shipment.updateStatus("DELIVERED");
        assertEquals("DELIVERED", shipment.getStatus());
    }
}
