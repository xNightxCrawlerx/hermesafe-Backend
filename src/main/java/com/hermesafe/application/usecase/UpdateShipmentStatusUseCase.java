package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.ShipmentRepository;
import java.util.Optional;

public class UpdateShipmentStatusUseCase {
    private final ShipmentRepository repository;

    public UpdateShipmentStatusUseCase(ShipmentRepository repository) {
        this.repository = repository;
    }

    public Optional<Shipment> execute(String id, String newStatus) {
        Optional<Shipment> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        Shipment shipment = opt.get();
        shipment.updateStatus(newStatus);
        return Optional.of(repository.save(shipment));
    }
}
