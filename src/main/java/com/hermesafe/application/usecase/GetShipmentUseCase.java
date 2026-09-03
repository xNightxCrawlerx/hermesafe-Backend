package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.ShipmentRepository;
import java.util.Optional;

public class GetShipmentUseCase {
    private final ShipmentRepository repository;

    public GetShipmentUseCase(ShipmentRepository repository) {
        this.repository = repository;
    }

    public Optional<Shipment> execute(String id) {
        return repository.findById(id);
    }
}
