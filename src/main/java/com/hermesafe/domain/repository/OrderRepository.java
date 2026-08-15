package com.hermesafe.domain.repository;

import com.hermesafe.domain.entity.Order;
import com.hermesafe.domain.valueobject.OrderId;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(OrderId id);
}
