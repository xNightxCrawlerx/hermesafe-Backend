package com.hermesafe.domain.repository;

import com.hermesafe.domain.entity.Warehouse;
import com.hermesafe.domain.valueobject.WarehouseId;
import java.util.List;
import java.util.Optional;

public interface WarehouseRepository {
    void save(Warehouse warehouse);
    Optional<Warehouse> findById(WarehouseId id);
    List<Warehouse> findAll();
}
