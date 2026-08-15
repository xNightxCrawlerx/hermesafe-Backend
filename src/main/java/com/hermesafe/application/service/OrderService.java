package com.hermesafe.application.service;

import com.hermesafe.domain.repository.InventoryRepository;

public class OrderService {

    private final InventoryRepository inventoryRepository;

    public OrderService(InventoryRepository inventoryRepository) {
        if (inventoryRepository == null) {
            throw new IllegalArgumentException("Inventory repository cannot be null");
        }
        this.inventoryRepository = inventoryRepository;
    }

    public boolean processOrder(String productId, int quantity) {
        int available = inventoryRepository.getStock(productId);
        if (available < quantity) {
            return false; // not enough stock
        }
        inventoryRepository.removeStock(productId, quantity);
        return true;
    }
}
