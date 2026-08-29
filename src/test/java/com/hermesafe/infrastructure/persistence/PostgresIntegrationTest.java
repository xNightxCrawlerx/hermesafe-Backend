package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.repository.InventoryRepository;
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
}
