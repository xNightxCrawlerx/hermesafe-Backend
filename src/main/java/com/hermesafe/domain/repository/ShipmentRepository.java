package com.hermesafe.domain.repository;

import com.hermesafe.domain.entity.Shipment;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    List<Shipment> findAll();
    Optional<Shipment> findById(String id);
    void deleteById(String id);
    long count();
}
