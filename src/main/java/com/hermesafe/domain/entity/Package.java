package com.hermesafe.domain.entity;

import com.hermesafe.domain.valueobject.Dimensions;
import com.hermesafe.domain.valueobject.PackageId;
import com.hermesafe.domain.valueobject.PostalCode;
import com.hermesafe.domain.valueobject.Weight;

public class Package {
    private final PackageId id;
    private final Weight weight;
    private final Dimensions dimensions;
    private final PostalCode destinationPostalCode;
    private ShipmentStatus shipmentStatus;

    public Package(PackageId id, Weight weight, Dimensions dimensions, PostalCode destinationPostalCode) {
        if (id == null) {
            throw new IllegalArgumentException("Package ID cannot be null");
        }
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null");
        }
        if (dimensions == null) {
            throw new IllegalArgumentException("Dimensions cannot be null");
        }
        if (destinationPostalCode == null) {
            throw new IllegalArgumentException("Destination postal code cannot be null");
        }
        this.id = id;
        this.weight = weight;
        this.dimensions = dimensions;
        this.destinationPostalCode = destinationPostalCode;
        this.shipmentStatus = ShipmentStatus.PENDING;
    }

    public PackageId getId() {
        return id;
    }

    public Weight getWeight() {
        return weight;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public PostalCode getDestinationPostalCode() {
        return destinationPostalCode;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void updateShipmentStatus(ShipmentStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Shipment status cannot be null");
        }
        this.shipmentStatus = newStatus;
    }

    public Weight calculateBillableWeight() {
        Weight volWeight = dimensions.calculateVolumetricWeight();
        return weight.value() >= volWeight.value() ? weight : volWeight;
    }
}
