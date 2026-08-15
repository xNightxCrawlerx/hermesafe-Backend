package com.hermesafe.domain.service;

public class InventoryManager {

    private int stock;

    public InventoryManager(int initialStock) {
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial stock cannot be negative");
        }
        this.stock = initialStock;
    }

    public int getStock() {
        return stock;
    }

    public void addStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive :)");
        }
        stock += amount;
    }

    public void removeStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive :)");
        }
        if (amount > stock) {
            throw new IllegalStateException("Not enough stock available");
        }
        stock -= amount;
    }
}
