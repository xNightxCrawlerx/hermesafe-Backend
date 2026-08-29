package com.hermesafe.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_items")
public class InventoryItemEntity {

    @Id
    @Column(name = "product_id", nullable = false, length = 100)
    private String productId;

    @Column(name = "stock", nullable = false)
    private int stock;

    public InventoryItemEntity() {
    }

    public InventoryItemEntity(String productId, int stock) {
        this.productId = productId;
        this.stock = stock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
