package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.Warehouse;
import com.hermesafe.domain.repository.WarehouseRepository;
import com.hermesafe.domain.valueobject.WarehouseId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryWarehouseRepository implements WarehouseRepository {

    private final Map<WarehouseId, Warehouse> warehouses = new ConcurrentHashMap<>();

    @Override
    public void save(Warehouse warehouse) {
        if (warehouse != null) {
            warehouses.put(warehouse.getId(), warehouse);
        }
    }

    @Override
    public Optional<Warehouse> findById(WarehouseId id) {
        return Optional.ofNullable(warehouses.get(id));
    }

    @Override
    public List<Warehouse> findAll() {
        return new ArrayList<>(warehouses.values());
    }
}
