package com.hermesafe.domain.repository;

import com.hermesafe.domain.entity.Route;
import com.hermesafe.domain.valueobject.RouteId;
import com.hermesafe.domain.valueobject.WarehouseId;
import java.util.List;
import java.util.Optional;

public interface RouteRepository {
    void save(Route route);
    Optional<Route> findById(RouteId id);
    List<Route> findByOriginWarehouseId(WarehouseId originId);
    List<Route> findAll();
}
