package com.hermesafe.domain.entity;

import com.hermesafe.domain.valueobject.ProductId;

public class InventoryItem {
    private final ProductId productId;
    private int stock;

    public InventoryItem(ProductId productId, int initialStock) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial stock cannot be negative");
        }
        this.productId = productId;
        this.stock = initialStock;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getStock() {
        return stock;
    }

    public void addStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive :)");
        }
        this.stock += amount;
    }

    public void removeStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive :)");
        }
        if (amount > stock) {
            throw new IllegalStateException("Not enough stock available");
        }
        this.stock -= amount;
    }

    public boolean hasAvailableStock(int requestedQuantity) {
        return this.stock >= requestedQuantity;
    }
}
