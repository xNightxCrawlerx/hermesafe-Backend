package com.hermesafe.infrastructure.persistence.adapter;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.repository.InventoryRepository;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.infrastructure.persistence.entity.InventoryItemEntity;
import com.hermesafe.infrastructure.persistence.mapper.InventoryItemMapper;
import com.hermesafe.infrastructure.persistence.repository.SpringDataInventoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class InventoryRepositoryAdapter implements InventoryRepository {

    private final SpringDataInventoryRepository springDataInventoryRepository;

    public InventoryRepositoryAdapter(SpringDataInventoryRepository springDataInventoryRepository) {
        this.springDataInventoryRepository = springDataInventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public int getStock(String productId) {
        if (productId == null) {
            return 0;
        }
        return springDataInventoryRepository.findById(productId)
                .map(InventoryItemEntity::getStock)
                .orElse(0);
    }

    @Override
    @Transactional
    public void removeStock(String productId, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        InventoryItemEntity entity = springDataInventoryRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Not enough stock available"));

        if (quantity > entity.getStock()) {
            throw new IllegalStateException("Not enough stock available");
        }

        entity.setStock(entity.getStock() - quantity);
        springDataInventoryRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryItem> findByProductId(ProductId productId) {
        if (productId == null) {
            return Optional.empty();
        }
        return springDataInventoryRepository.findById(productId.value())
                .map(InventoryItemMapper::toDomain);
    }

    @Override
    @Transactional
    public void save(InventoryItem item) {
        if (item == null) {
            return;
        }
        InventoryItemEntity entity = InventoryItemMapper.toEntity(item);
        springDataInventoryRepository.save(entity);
    }
}
