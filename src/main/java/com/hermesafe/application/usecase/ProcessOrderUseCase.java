package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.repository.InventoryRepository;
import com.hermesafe.domain.valueobject.ProductId;
import java.util.Optional;

public class ProcessOrderUseCase {

    private final InventoryRepository inventoryRepository;

    public ProcessOrderUseCase(InventoryRepository inventoryRepository) {
        if (inventoryRepository == null) {
            throw new IllegalArgumentException("Inventory repository cannot be null");
        }
        this.inventoryRepository = inventoryRepository;
    }

    public boolean execute(ProductId productId, int quantity) {
        Optional<InventoryItem> itemOpt = inventoryRepository.findByProductId(productId);
        if (itemOpt.isPresent()) {
            InventoryItem item = itemOpt.get();
            if (!item.hasAvailableStock(quantity)) {
                return false;
            }
            item.removeStock(quantity);
            inventoryRepository.save(item);
            return true;
        } else {
            int available = inventoryRepository.getStock(productId.value());
            if (available < quantity) {
                return false;
            }
            inventoryRepository.removeStock(productId.value(), quantity);
            return true;
        }
    }
}
