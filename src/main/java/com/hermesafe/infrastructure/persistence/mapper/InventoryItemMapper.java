package com.hermesafe.infrastructure.persistence.mapper;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.infrastructure.persistence.entity.InventoryItemEntity;

public final class InventoryItemMapper {

    private InventoryItemMapper() {
    }

    public static InventoryItem toDomain(InventoryItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return new InventoryItem(new ProductId(entity.getProductId()), entity.getStock());
    }

    public static InventoryItemEntity toEntity(InventoryItem domain) {
        if (domain == null) {
            return null;
        }
        return new InventoryItemEntity(domain.getProductId().value(), domain.getStock());
    }
}
