package com.hermesafe.infrastructure.persistence.repository;

import com.hermesafe.infrastructure.persistence.entity.ShipmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataShipmentRepository extends JpaRepository<ShipmentJpaEntity, String> {
}
