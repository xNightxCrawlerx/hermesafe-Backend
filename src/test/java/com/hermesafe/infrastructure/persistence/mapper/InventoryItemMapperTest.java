package com.hermesafe.infrastructure.persistence.mapper;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.infrastructure.persistence.entity.InventoryItemEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemMapperTest {

    @Test
    void shouldMapDomainToEntityCorrectly() {
        ProductId productId = new ProductId("PROD-001");
        InventoryItem domain = new InventoryItem(productId, 50);

        InventoryItemEntity entity = InventoryItemMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals("PROD-001", entity.getProductId());
        assertEquals(50, entity.getStock());
    }

    @Test
    void shouldMapEntityToDomainCorrectly() {
        InventoryItemEntity entity = new InventoryItemEntity("PROD-002", 100);

        InventoryItem domain = InventoryItemMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals("PROD-002", domain.getProductId().value());
        assertEquals(100, domain.getStock());
    }

    @Test
    void shouldHandleNullValuesGracefully() {
        assertNull(InventoryItemMapper.toDomain(null));
        assertNull(InventoryItemMapper.toEntity(null));
    }
}
