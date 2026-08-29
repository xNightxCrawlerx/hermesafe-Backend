package com.hermesafe.infrastructure.persistence.adapter;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.infrastructure.persistence.entity.InventoryItemEntity;
import com.hermesafe.infrastructure.persistence.repository.SpringDataInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryRepositoryAdapterTest {

    @Mock
    private SpringDataInventoryRepository springDataInventoryRepository;

    private InventoryRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new InventoryRepositoryAdapter(springDataInventoryRepository);
    }

    @Test
    @DisplayName("Should return correct stock when product exists")
    void shouldReturnStockWhenProductExists() {
        when(springDataInventoryRepository.findById("PROD-01"))
                .thenReturn(Optional.of(new InventoryItemEntity("PROD-01", 42)));

        int stock = adapter.getStock("PROD-01");

        assertEquals(42, stock);
        verify(springDataInventoryRepository, times(1)).findById("PROD-01");
    }

    @Test
    @DisplayName("Should return 0 stock when product does not exist")
    void shouldReturnZeroStockWhenProductDoesNotExist() {
        when(springDataInventoryRepository.findById("PROD-NONEXISTENT"))
                .thenReturn(Optional.empty());

        int stock = adapter.getStock("PROD-NONEXISTENT");

        assertEquals(0, stock);
    }

    @Test
    @DisplayName("Should return 0 when productId is null")
    void shouldReturnZeroWhenProductIdIsNull() {
        assertEquals(0, adapter.getStock(null));
        verifyNoInteractions(springDataInventoryRepository);
    }

    @Test
    @DisplayName("Should save domain InventoryItem converting to entity")
    void shouldSaveDomainInventoryItem() {
        InventoryItem item = new InventoryItem(new ProductId("PROD-99"), 15);

        adapter.save(item);

        ArgumentCaptor<InventoryItemEntity> captor = ArgumentCaptor.forClass(InventoryItemEntity.class);
        verify(springDataInventoryRepository).save(captor.capture());

        InventoryItemEntity captured = captor.getValue();
        assertEquals("PROD-99", captured.getProductId());
        assertEquals(15, captured.getStock());
    }

    @Test
    @DisplayName("Should find domain entity by ProductId value object")
    void shouldFindDomainEntityByProductId() {
        when(springDataInventoryRepository.findById("PROD-55"))
                .thenReturn(Optional.of(new InventoryItemEntity("PROD-55", 30)));

        Optional<InventoryItem> result = adapter.findByProductId(new ProductId("PROD-55"));

        assertTrue(result.isPresent());
        assertEquals("PROD-55", result.get().getProductId().value());
        assertEquals(30, result.get().getStock());
    }

    @Test
    @DisplayName("Should remove stock when sufficient stock is available")
    void shouldRemoveStockSuccessfully() {
        InventoryItemEntity entity = new InventoryItemEntity("PROD-88", 20);
        when(springDataInventoryRepository.findById("PROD-88")).thenReturn(Optional.of(entity));

        adapter.removeStock("PROD-88", 5);

        assertEquals(15, entity.getStock());
        verify(springDataInventoryRepository).save(entity);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when removing more stock than available")
    void shouldThrowWhenRemovingExcessStock() {
        InventoryItemEntity entity = new InventoryItemEntity("PROD-88", 5);
        when(springDataInventoryRepository.findById("PROD-88")).thenReturn(Optional.of(entity));

        assertThrows(IllegalStateException.class, () -> adapter.removeStock("PROD-88", 10));
        verify(springDataInventoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw IllegalStateException when product not found on removeStock")
    void shouldThrowWhenProductNotFoundOnRemoveStock() {
        when(springDataInventoryRepository.findById("PROD-MISSING")).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> adapter.removeStock("PROD-MISSING", 5));
    }
}
