package com.hermesafe.domain.service;

import com.hermesafe.domain.entity.Route;
import java.util.*;

public class RouteOptimizer {

    private final Map<String, Integer> warehouseDistances;

    public RouteOptimizer(Map<String, Integer> warehouseDistances) {
        if (warehouseDistances == null || warehouseDistances.isEmpty()) {
            throw new IllegalArgumentException("Warehouse distances cannot be null or empty");
        }
        this.warehouseDistances = new HashMap<>(warehouseDistances);
    }

    public boolean isCityCovered(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        return warehouseDistances.containsKey(city);
    }

    public List<String> getClosestWarehouses() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(warehouseDistances.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        List<String> sortedWarehouses = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedWarehouses.add(entry.getKey());
        }
        return sortedWarehouses;
    }

    public Optional<Route> findShortestRoute(List<Route> routes) {
        if (routes == null || routes.isEmpty()) {
            return Optional.empty();
        }
        return routes.stream()
                .min(Comparator.comparingInt(r -> r.getDistance().kilometers()));
    }
}
