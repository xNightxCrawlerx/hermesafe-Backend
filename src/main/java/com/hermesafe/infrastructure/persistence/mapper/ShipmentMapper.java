package com.hermesafe.infrastructure.persistence.mapper;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.infrastructure.persistence.entity.ShipmentJpaEntity;
import com.hermesafe.infrastructure.web.dto.ShipmentDto;

public final class ShipmentMapper {

    private ShipmentMapper() {
    }

    public static ShipmentJpaEntity toEntity(Shipment domain) {
        if (domain == null) return null;
        return new ShipmentJpaEntity(
                domain.getId(),
                domain.getTrackingCode(),
                domain.getSenderName(),
                domain.getRecipientName(),
                domain.getOriginCity(),
                domain.getDestinationCity(),
                domain.getStatus(),
                domain.getPriority(),
                domain.getWeightKg(),
                domain.getEstimatedDelivery(),
                domain.getCreatedAt(),
                domain.getNotes(),
                domain.isPriorityFeatured()
        );
    }

    public static Shipment toDomain(ShipmentJpaEntity entity) {
        if (entity == null) return null;
        return new Shipment(
                entity.getId(),
                entity.getTrackingCode(),
                entity.getSenderName(),
                entity.getRecipientName(),
                entity.getOriginCity(),
                entity.getDestinationCity(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getWeightKg(),
                entity.getEstimatedDelivery(),
                entity.getCreatedAt(),
                entity.getNotes(),
                entity.isPriorityFeatured()
        );
    }

    public static ShipmentDto toDto(Shipment domain) {
        if (domain == null) return null;
        return new ShipmentDto(
                domain.getId(),
                domain.getTrackingCode(),
                domain.getSenderName(),
                domain.getRecipientName(),
                domain.getOriginCity(),
                domain.getDestinationCity(),
                domain.getStatus(),
                domain.getPriority(),
                domain.getWeightKg(),
                domain.getEstimatedDelivery(),
                domain.getCreatedAt(),
                domain.getNotes(),
                domain.isPriorityFeatured()
        );
    }

    public static ShipmentDto entityToDto(ShipmentJpaEntity entity) {
        if (entity == null) return null;
        return new ShipmentDto(
                entity.getId(),
                entity.getTrackingCode(),
                entity.getSenderName(),
                entity.getRecipientName(),
                entity.getOriginCity(),
                entity.getDestinationCity(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getWeightKg(),
                entity.getEstimatedDelivery(),
                entity.getCreatedAt(),
                entity.getNotes(),
                entity.isPriorityFeatured()
        );
    }
}
