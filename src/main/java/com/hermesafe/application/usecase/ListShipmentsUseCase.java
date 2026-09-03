package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.ShipmentRepository;
import java.util.List;

public class ListShipmentsUseCase {
    private final ShipmentRepository repository;

    public ListShipmentsUseCase(ShipmentRepository repository) {
        this.repository = repository;
    }

    public List<Shipment> execute() {
        return repository.findAll();
    }
}
