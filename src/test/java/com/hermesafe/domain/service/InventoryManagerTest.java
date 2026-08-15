package com.hermesafe.domain.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {

    @Test
    void shouldInitializeWithValidStock() {
        InventoryManager manager = new InventoryManager(10);
        assertEquals(10, manager.getStock());
    }

    @Test
    void shouldThrowExceptionForNegativeInitialStock() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryManager(-5));
    }

    @Test
    void shouldAddStockCorrectly() {
        InventoryManager manager = new InventoryManager(10);
        manager.addStock(5);
        assertEquals(15, manager.getStock());
    }

    @Test
    void shouldThrowExceptionForInvalidAddStock() {
        InventoryManager manager = new InventoryManager(10);
        assertThrows(IllegalArgumentException.class, () -> manager.addStock(0));
    }

    @Test
    void shouldRemoveStockCorrectly() {
        InventoryManager manager = new InventoryManager(10);
        manager.removeStock(4);
        assertEquals(6, manager.getStock());
    }

    @Test
    void shouldThrowExceptionForZeroRemoveStock() {
        InventoryManager manager = new InventoryManager(10);
        assertThrows(IllegalArgumentException.class, () -> manager.removeStock(0));
    }

    @Test
    void shouldThrowExceptionForNegativeRemoveStock() {
        InventoryManager manager = new InventoryManager(10);
        assertThrows(IllegalArgumentException.class, () -> manager.removeStock(-3));
    }

    @Test
    void shouldThrowExceptionWhenRemovingMoreThanAvailable() {
        InventoryManager manager = new InventoryManager(5);
        assertThrows(IllegalStateException.class, () -> manager.removeStock(10));
    }
}
