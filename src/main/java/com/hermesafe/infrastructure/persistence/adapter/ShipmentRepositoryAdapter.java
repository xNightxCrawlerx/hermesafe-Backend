package com.hermesafe.infrastructure.persistence.adapter;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.ShipmentRepository;
import com.hermesafe.infrastructure.persistence.entity.ShipmentJpaEntity;
import com.hermesafe.infrastructure.persistence.mapper.ShipmentMapper;
import com.hermesafe.infrastructure.persistence.repository.SpringDataShipmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@SuppressWarnings("null")
public class ShipmentRepositoryAdapter implements ShipmentRepository {

    private final SpringDataShipmentRepository jpaRepository;

    public ShipmentRepositoryAdapter(SpringDataShipmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Shipment save(Shipment shipment) {
        ShipmentJpaEntity entity = ShipmentMapper.toEntity(shipment);
        ShipmentJpaEntity saved = jpaRepository.save(entity);
        return ShipmentMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shipment> findAll() {
        return jpaRepository.findAll().stream()
                .map(ShipmentMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Shipment> findById(String id) {
        return jpaRepository.findById(id)
                .map(ShipmentMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return jpaRepository.count();
    }
}
