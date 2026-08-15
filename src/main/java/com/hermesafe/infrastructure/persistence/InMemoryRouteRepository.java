package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.Route;
import com.hermesafe.domain.repository.RouteRepository;
import com.hermesafe.domain.valueobject.RouteId;
import com.hermesafe.domain.valueobject.WarehouseId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryRouteRepository implements RouteRepository {

    private final Map<RouteId, Route> routes = new ConcurrentHashMap<>();

    @Override
    public void save(Route route) {
        if (route != null) {
            routes.put(route.getId(), route);
        }
    }

    @Override
    public Optional<Route> findById(RouteId id) {
        return Optional.ofNullable(routes.get(id));
    }

    @Override
    public List<Route> findByOriginWarehouseId(WarehouseId originId) {
        return routes.values().stream()
                .filter(route -> route.getOriginWarehouseId().equals(originId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> findAll() {
        return new ArrayList<>(routes.values());
    }
}
