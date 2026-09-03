package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.InventoryRepository;
import com.hermesafe.domain.repository.ShipmentRepository;
import com.hermesafe.domain.valueobject.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostgresIntegrationTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    @DisplayName("Should persist and retrieve inventory item in Docker PostgreSQL")
    void shouldPersistAndRetrieveItemInPostgres() {
        ProductId productId = new ProductId("PG-PROD-001");
        InventoryItem item = new InventoryItem(productId, 75);

        inventoryRepository.save(item);

        Optional<InventoryItem> retrieved = inventoryRepository.findByProductId(productId);
        assertTrue(retrieved.isPresent());
        assertEquals(75, retrieved.get().getStock());
        assertEquals("PG-PROD-001", retrieved.get().getProductId().value());

        // Test stock modification
        inventoryRepository.removeStock("PG-PROD-001", 25);
        assertEquals(50, inventoryRepository.getStock("PG-PROD-001"));
    }

    @Test
    @DisplayName("Should persist and retrieve shipment entity in Docker PostgreSQL")
    void shouldPersistAndRetrieveShipmentInPostgres() {
        String testId = "ENV-PG-TEST-001";
        Shipment shipment = new Shipment(
                testId,
                "HMS-999001-CL",
                "Integration Sender SpA",
                "Integration Recipient",
                "Santiago",
                "Valparaíso",
                "PENDING",
                "EXPRESS",
                3.5,
                "2026-08-15",
                "2026-08-12",
                "Test PostgreSQL direct persistence",
                true
        );

        shipmentRepository.save(shipment);

        Optional<Shipment> retrieved = shipmentRepository.findById(testId);
        assertTrue(retrieved.isPresent(), "Shipment should be saved and retrieved from PostgreSQL");
        assertEquals("Integration Sender SpA", retrieved.get().getSenderName());
        assertEquals("Valparaíso", retrieved.get().getDestinationCity());
        assertEquals(3.5, retrieved.get().getWeightKg());
        assertTrue(retrieved.get().isPriorityFeatured());
    }
}
