package com.hermesafe.domain.entity;

import com.hermesafe.domain.exception.InsufficientStockException;
import com.hermesafe.domain.exception.InvalidOrderStatusException;
import com.hermesafe.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesTest {

    @Test
    void shouldCreateAndManageInventoryItem() {
        ProductId pid = new ProductId("P100");
        InventoryItem item = new InventoryItem(pid, 20);

        assertEquals(pid, item.getProductId());
        assertEquals(20, item.getStock());
        assertTrue(item.hasAvailableStock(15));
        assertFalse(item.hasAvailableStock(25));

        item.addStock(10);
        assertEquals(30, item.getStock());

        item.removeStock(5);
        assertEquals(25, item.getStock());
    }

    @Test
    void shouldCreateAndProcessOrder() {
        OrderId oid = new OrderId("O100");
        ProductId pid = new ProductId("P100");
        Order order = new Order(oid, pid, 3);

        assertEquals(oid, order.getId());
        assertEquals(pid, order.getProductId());
        assertEquals(3, order.getQuantity());
        assertFalse(order.isProcessed());

        order.markAsProcessed();
        assertTrue(order.isProcessed());
    }

    @Test
    void shouldManagePackageEntity() {
        PackageId pkgId = PackageId.generate();
        Weight weight = new Weight(5.0);
        Dimensions dims = new Dimensions(30, 20, 10);
        PostalCode postalCode = new PostalCode("12345");

        Package pkg = new Package(pkgId, weight, dims, postalCode);

        assertEquals(pkgId, pkg.getId());
        assertEquals(weight, pkg.getWeight());
        assertEquals(dims, pkg.getDimensions());
        assertEquals(postalCode, pkg.getDestinationPostalCode());
        assertEquals(ShipmentStatus.PENDING, pkg.getShipmentStatus());

        pkg.updateShipmentStatus(ShipmentStatus.IN_TRANSIT);
        assertEquals(ShipmentStatus.IN_TRANSIT, pkg.getShipmentStatus());

        assertEquals(5.0, pkg.calculateBillableWeight().value());
    }

    @Test
    void shouldManageRichOrderAggregate() {
        OrderId oid = new OrderId("ORD-99");
        CustomerId cid = new CustomerId("CUST-1");
        Package pkg1 = new Package(PackageId.generate(), new Weight(2.0), new Dimensions(10, 10, 10), new PostalCode("12345"));
        Package pkg2 = new Package(PackageId.generate(), new Weight(3.5), new Dimensions(10, 10, 10), new PostalCode("12345"));

        Order order = new Order(oid, cid, List.of(pkg1));
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertNotNull(order.getCreatedAt());

        order.addPackage(pkg2);
        assertEquals(2, order.getPackages().size());

        assertEquals(5.5, order.calculateTotalWeight().value());

        order.markAsProcessed();
        assertEquals(OrderStatus.PROCESSED, order.getStatus());
        assertThrows(InvalidOrderStatusException.class, order::cancel);

        Order cancelOrder = new Order(new OrderId("ORD-100"), cid, List.of());
        cancelOrder.cancel();
        assertEquals(OrderStatus.CANCELLED, cancelOrder.getStatus());
        assertThrows(InvalidOrderStatusException.class, cancelOrder::markAsProcessed);
    }

    @Test
    void shouldManageWarehouseAggregate() {
        WarehouseId whId = WarehouseId.generate();
        Location loc = new Location("Valparaiso", "Puerto 456");
        Warehouse warehouse = new Warehouse(whId, loc);

        ProductId prodId = new ProductId("PROD-ABC");
        warehouse.addProductStock(prodId, 50);

        assertTrue(warehouse.hasAvailableStock(prodId, 30));
        assertFalse(warehouse.hasAvailableStock(prodId, 100));

        warehouse.removeProductStock(prodId, 20);
        assertEquals(30, warehouse.getInventory().get(prodId).getStock());

        assertThrows(InsufficientStockException.class, () -> warehouse.removeProductStock(prodId, 50));
    }

    @Test
    void shouldManageRouteEntity() {
        RouteId routeId = RouteId.generate();
        WarehouseId origin = WarehouseId.generate();
        WarehouseId dest = WarehouseId.generate();
        Distance dist = new Distance(120);
        EstimatedTime time = new EstimatedTime(1.5);

        Route route = new Route(routeId, origin, dest, dist, time);

        assertEquals(routeId, route.getId());
        assertEquals(origin, route.getOriginWarehouseId());
        assertEquals(dest, route.getDestinationWarehouseId());
        assertEquals(120, route.getDistance().kilometers());
        assertEquals(1.5, route.getEstimatedTime().hours());
    }
}
