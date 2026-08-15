package com.hermesafe.domain.service;

import com.hermesafe.domain.entity.Route;
import com.hermesafe.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class RouteOptimizerTest {

    @Test
    void shouldThrowExceptionForNullWarehouseDistances() {
        assertThrows(IllegalArgumentException.class, () -> new RouteOptimizer(null));
    }

    @Test
    void shouldThrowExceptionForEmptyWarehouseDistances() {
        assertThrows(IllegalArgumentException.class, () -> new RouteOptimizer(Collections.emptyMap()));
    }

    @Test
    void shouldReturnTrueIfCityIsCovered() {
        Map<String, Integer> distances = Map.of("Santiago", 10, "Valparaiso", 50);
        RouteOptimizer optimizer = new RouteOptimizer(distances);
        assertTrue(optimizer.isCityCovered("Santiago"));
    }

    @Test
    void shouldReturnFalseIfCityIsNotCovered() {
        Map<String, Integer> distances = Map.of("Santiago", 10);
        RouteOptimizer optimizer = new RouteOptimizer(distances);
        assertFalse(optimizer.isCityCovered("Valparaiso"));
    }

    @Test
    void shouldThrowExceptionForNullCity() {
        Map<String, Integer> distances = Map.of("Santiago", 10);
        RouteOptimizer optimizer = new RouteOptimizer(distances);
        assertThrows(IllegalArgumentException.class, () -> optimizer.isCityCovered(null));
    }

    @Test
    void shouldThrowExceptionWhenCityIsBlank() {
        Map<String, Integer> distances = Map.of("Santiago", 10);
        RouteOptimizer optimizer = new RouteOptimizer(distances);
        assertThrows(IllegalArgumentException.class, () -> optimizer.isCityCovered(" "));
    }

    @Test
    void shouldReturnWarehousesSortedByDistance() {
        Map<String, Integer> distances = Map.of("Santiago", 10, "Valparaiso", 50, "Concepcion", 30);
        RouteOptimizer optimizer = new RouteOptimizer(distances);
        List<String> result = optimizer.getClosestWarehouses();
        assertEquals(Arrays.asList("Santiago", "Concepcion", "Valparaiso"), result);
    }

    @Test
    void shouldFindShortestRouteFromList() {
        Map<String, Integer> distances = Map.of("Santiago", 10);
        RouteOptimizer optimizer = new RouteOptimizer(distances);

        WarehouseId w1 = WarehouseId.generate();
        WarehouseId w2 = WarehouseId.generate();

        Route r1 = new Route(RouteId.generate(), w1, w2, new Distance(200), new EstimatedTime(2.0));
        Route r2 = new Route(RouteId.generate(), w1, w2, new Distance(120), new EstimatedTime(1.2));
        Route r3 = new Route(RouteId.generate(), w1, w2, new Distance(350), new EstimatedTime(4.0));

        Optional<Route> shortest = optimizer.findShortestRoute(List.of(r1, r2, r3));
        assertTrue(shortest.isPresent());
        assertEquals(120, shortest.get().getDistance().kilometers());

        assertTrue(optimizer.findShortestRoute(Collections.emptyList()).isEmpty());
    }
}
