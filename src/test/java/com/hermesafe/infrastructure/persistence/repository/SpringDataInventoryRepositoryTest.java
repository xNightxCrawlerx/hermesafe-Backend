package com.hermesafe.infrastructure.persistence.repository;

import com.hermesafe.infrastructure.persistence.entity.InventoryItemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SpringDataInventoryRepositoryTest {

    @Autowired
    private SpringDataInventoryRepository repository;

    @Test
    @DisplayName("Should save and retrieve InventoryItemEntity from SQL database")
    void shouldSaveAndFindInventoryItemEntity() {
        InventoryItemEntity entity = new InventoryItemEntity("PROD-TEST-100", 25);

        InventoryItemEntity saved = repository.save(entity);

        assertNotNull(saved);
        Optional<InventoryItemEntity> retrieved = repository.findById("PROD-TEST-100");
        assertTrue(retrieved.isPresent());
        assertEquals("PROD-TEST-100", retrieved.get().getProductId());
        assertEquals(25, retrieved.get().getStock());
    }

    @Test
    @DisplayName("Should update stock quantity in SQL database")
    void shouldUpdateStockQuantity() {
        InventoryItemEntity entity = new InventoryItemEntity("PROD-TEST-200", 50);
        repository.save(entity);

        entity.setStock(35);
        repository.save(entity);

        Optional<InventoryItemEntity> updated = repository.findById("PROD-TEST-200");
        assertTrue(updated.isPresent());
        assertEquals(35, updated.get().getStock());
    }

    @Test
    @DisplayName("Should delete InventoryItemEntity from SQL database")
    void shouldDeleteInventoryItemEntity() {
        InventoryItemEntity entity = new InventoryItemEntity("PROD-TEST-300", 10);
        repository.save(entity);

        repository.deleteById("PROD-TEST-300");

        Optional<InventoryItemEntity> retrieved = repository.findById("PROD-TEST-300");
        assertTrue(retrieved.isEmpty());
    }
}
