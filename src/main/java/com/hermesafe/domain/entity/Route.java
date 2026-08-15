package com.hermesafe.domain.entity;

import com.hermesafe.domain.valueobject.Distance;
import com.hermesafe.domain.valueobject.EstimatedTime;
import com.hermesafe.domain.valueobject.RouteId;
import com.hermesafe.domain.valueobject.WarehouseId;

public class Route {
    private final RouteId id;
    private final WarehouseId originWarehouseId;
    private final WarehouseId destinationWarehouseId;
    private final Distance distance;
    private final EstimatedTime estimatedTime;

    public Route(RouteId id, WarehouseId originWarehouseId, WarehouseId destinationWarehouseId, Distance distance, EstimatedTime estimatedTime) {
        if (id == null) {
            throw new IllegalArgumentException("Route ID cannot be null");
        }
        if (originWarehouseId == null) {
            throw new IllegalArgumentException("Origin warehouse ID cannot be null");
        }
        if (destinationWarehouseId == null) {
            throw new IllegalArgumentException("Destination warehouse ID cannot be null");
        }
        if (distance == null) {
            throw new IllegalArgumentException("Distance cannot be null");
        }
        if (estimatedTime == null) {
            throw new IllegalArgumentException("Estimated time cannot be null");
        }
        this.id = id;
        this.originWarehouseId = originWarehouseId;
        this.destinationWarehouseId = destinationWarehouseId;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
    }

    public RouteId getId() {
        return id;
    }

    public WarehouseId getOriginWarehouseId() {
        return originWarehouseId;
    }

    public WarehouseId getDestinationWarehouseId() {
        return destinationWarehouseId;
    }

    public Distance getDistance() {
        return distance;
    }

    public EstimatedTime getEstimatedTime() {
        return estimatedTime;
    }
}
