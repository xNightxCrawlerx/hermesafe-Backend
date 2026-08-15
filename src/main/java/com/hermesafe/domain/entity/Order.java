package com.hermesafe.domain.entity;

import com.hermesafe.domain.exception.InvalidOrderStatusException;
import com.hermesafe.domain.valueobject.CustomerId;
import com.hermesafe.domain.valueobject.OrderId;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.domain.valueobject.Weight;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final OrderId id;
    private final CustomerId customerId;
    private final List<Package> packages;
    private final Instant createdAt;
    private OrderStatus status;

    private final ProductId productId;
    private final int quantity;

    public Order(OrderId id, CustomerId customerId, List<Package> packages) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        this.id = id;
        this.customerId = customerId;
        this.packages = new ArrayList<>(packages != null ? packages : Collections.emptyList());
        this.createdAt = Instant.now();
        this.status = OrderStatus.CREATED;
        this.productId = null;
        this.quantity = 0;
    }

    public Order(OrderId id, ProductId productId, int quantity) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.id = id;
        this.customerId = new CustomerId("DEFAULT_CUSTOMER");
        this.packages = new ArrayList<>();
        this.createdAt = Instant.now();
        this.status = OrderStatus.CREATED;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderId getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public List<Package> getPackages() {
        return Collections.unmodifiableList(packages);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isProcessed() {
        return status == OrderStatus.PROCESSED;
    }

    public void markAsProcessed() {
        if (status == OrderStatus.CANCELLED) {
            throw new InvalidOrderStatusException("Cannot process a cancelled order");
        }
        this.status = OrderStatus.PROCESSED;
    }

    public void cancel() {
        if (status == OrderStatus.PROCESSED) {
            throw new InvalidOrderStatusException("Cannot cancel an already processed order");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void addPackage(Package pkg) {
        if (pkg == null) {
            throw new IllegalArgumentException("Package cannot be null");
        }
        if (status != OrderStatus.CREATED) {
            throw new InvalidOrderStatusException("Cannot add packages to an order that is not in CREATED status");
        }
        this.packages.add(pkg);
    }

    public Weight calculateTotalWeight() {
        double total = 0.0;
        for (Package pkg : packages) {
            total += pkg.getWeight().value();
        }
        if (total == 0.0) {
            return new Weight(0.001);
        }
        return new Weight(total);
    }
}
