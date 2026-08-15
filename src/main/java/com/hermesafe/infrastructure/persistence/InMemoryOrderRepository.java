package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.Order;
import com.hermesafe.domain.repository.OrderRepository;
import com.hermesafe.domain.valueobject.OrderId;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<OrderId, Order> orders = new ConcurrentHashMap<>();

    @Override
    public void save(Order order) {
        if (order != null) {
            orders.put(order.getId(), order);
        }
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return Optional.ofNullable(orders.get(id));
    }
}
