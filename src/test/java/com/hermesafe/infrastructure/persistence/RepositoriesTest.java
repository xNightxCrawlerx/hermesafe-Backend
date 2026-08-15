package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.Order;
import com.hermesafe.domain.entity.Route;
import com.hermesafe.domain.entity.Warehouse;
import com.hermesafe.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoriesTest {

    @Test
    void shouldSaveAndFindOrderInInMemoryRepository() {
        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        OrderId oid = new OrderId("ORD-999");
        ProductId pid = new ProductId("PROD-999");
        Order order = new Order(oid, pid, 5);

        repo.save(order);
        assertTrue(repo.findById(oid).isPresent());
        assertEquals(5, repo.findById(oid).get().getQuantity());
    }

    @Test
    void shouldManageStockInInMemoryInventoryRepository() {
        InMemoryInventoryRepository repo = new InMemoryInventoryRepository();
        repo.addStockToCatalog("PROD-10", 15);

        assertEquals(15, repo.getStock("PROD-10"));
        repo.removeStock("PROD-10", 5);
        assertEquals(10, repo.getStock("PROD-10"));
    }

    @Test
    void shouldSaveAndFindWarehouseInInMemoryRepository() {
        InMemoryWarehouseRepository repo = new InMemoryWarehouseRepository();
        WarehouseId whId = WarehouseId.generate();
        Location loc = new Location("Concepcion", "Calle 1");
        Warehouse wh = new Warehouse(whId, loc);

        repo.save(wh);
        assertTrue(repo.findById(whId).isPresent());
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void shouldSaveAndFindRoutesInInMemoryRepository() {
        InMemoryRouteRepository repo = new InMemoryRouteRepository();
        RouteId routeId = RouteId.generate();
        WarehouseId origin = WarehouseId.generate();
        WarehouseId dest = WarehouseId.generate();
        Route route = new Route(routeId, origin, dest, new Distance(80), new EstimatedTime(1.0));

        repo.save(route);
        assertTrue(repo.findById(routeId).isPresent());
        assertEquals(1, repo.findByOriginWarehouseId(origin).size());
        assertEquals(1, repo.findAll().size());
    }
}
