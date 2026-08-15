package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.repository.InventoryRepository;
import com.hermesafe.domain.valueobject.ProductId;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInventoryRepository implements InventoryRepository {

    private final Map<String, Integer> stockMap = new ConcurrentHashMap<>();
    private final Map<ProductId, InventoryItem> itemsMap = new ConcurrentHashMap<>();

    public void addStockToCatalog(String productId, int quantity) {
        stockMap.put(productId, stockMap.getOrDefault(productId, 0) + quantity);
    }

    @Override
    public int getStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }

    @Override
    public void removeStock(String productId, int quantity) {
        int current = getStock(productId);
        if (quantity > current) {
            throw new IllegalStateException("Not enough stock available");
        }
        stockMap.put(productId, current - quantity);
    }

    @Override
    public Optional<InventoryItem> findByProductId(ProductId productId) {
        return Optional.ofNullable(itemsMap.get(productId));
    }

    @Override
    public void save(InventoryItem item) {
        itemsMap.put(item.getProductId(), item);
        stockMap.put(item.getProductId().value(), item.getStock());
    }
}
