package com.hermesafe.domain.entity;

import com.hermesafe.domain.exception.InsufficientStockException;
import com.hermesafe.domain.valueobject.Location;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.domain.valueobject.WarehouseId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final WarehouseId id;
    private final Location location;
    private final Map<ProductId, InventoryItem> inventory;

    public Warehouse(WarehouseId id, Location location) {
        if (id == null) {
            throw new IllegalArgumentException("Warehouse ID cannot be null");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        this.id = id;
        this.location = location;
        this.inventory = new HashMap<>();
    }

    public WarehouseId getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Map<ProductId, InventoryItem> getInventory() {
        return Collections.unmodifiableMap(inventory);
    }

    public void addProductStock(ProductId productId, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        InventoryItem item = inventory.computeIfAbsent(productId, id -> new InventoryItem(id, 0));
        item.addStock(quantity);
    }

    public void removeProductStock(ProductId productId, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        InventoryItem item = inventory.get(productId);
        if (item == null || !item.hasAvailableStock(quantity)) {
            throw new InsufficientStockException("Not enough stock available for product: " + productId.value());
        }
        item.removeStock(quantity);
    }

    public boolean hasAvailableStock(ProductId productId, int quantity) {
        if (productId == null || !inventory.containsKey(productId)) {
            return false;
        }
        return inventory.get(productId).hasAvailableStock(quantity);
    }
}
