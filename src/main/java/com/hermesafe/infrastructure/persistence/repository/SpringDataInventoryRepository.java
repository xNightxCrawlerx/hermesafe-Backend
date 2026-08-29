package com.hermesafe.infrastructure.persistence.repository;

import com.hermesafe.infrastructure.persistence.entity.InventoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataInventoryRepository extends JpaRepository<InventoryItemEntity, String> {
}
