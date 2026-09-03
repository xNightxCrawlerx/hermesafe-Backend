package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.ShipmentRepository;

public class CreateShipmentUseCase {
    private final ShipmentRepository repository;

    public CreateShipmentUseCase(ShipmentRepository repository) {
        this.repository = repository;
    }

    public Shipment execute(Shipment shipment) {
        return repository.save(shipment);
    }
}
