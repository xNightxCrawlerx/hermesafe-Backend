package com.hermesafe.application.service;

import com.hermesafe.domain.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Test
    void shouldProcessOrderWhenEnoughStock() {
        InventoryRepository repo = mock(InventoryRepository.class);
        when(repo.getStock("P001")).thenReturn(10);

        OrderService service = new OrderService(repo);
        boolean result = service.processOrder("P001", 5);

        assertTrue(result);
        verify(repo).removeStock("P001", 5);
    }

    @Test
    void shouldNotProcessOrderWhenInsufficientStock() {
        InventoryRepository repo = mock(InventoryRepository.class);
        when(repo.getStock("P001")).thenReturn(3);

        OrderService service = new OrderService(repo);
        boolean result = service.processOrder("P001", 5);

        assertFalse(result);
        verify(repo, never()).removeStock(anyString(), anyInt());
    }
}
