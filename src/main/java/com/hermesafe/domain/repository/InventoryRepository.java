package com.hermesafe.domain.repository;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.valueobject.ProductId;
import java.util.Optional;

public interface InventoryRepository {
    int getStock(String productId);
    void removeStock(String productId, int quantity);
    Optional<InventoryItem> findByProductId(ProductId productId);
    void save(InventoryItem item);
}
