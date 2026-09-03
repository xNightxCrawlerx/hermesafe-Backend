package com.hermesafe.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipments")
public class ShipmentJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "tracking_code", nullable = false, length = 50)
    private String trackingCode;

    @Column(name = "sender_name", nullable = false, length = 150)
    private String senderName;

    @Column(name = "recipient_name", nullable = false, length = 150)
    private String recipientName;

    @Column(name = "origin_city", nullable = false, length = 100)
    private String originCity;

    @Column(name = "destination_city", nullable = false, length = 100)
    private String destinationCity;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "priority", nullable = false, length = 50)
    private String priority;

    @Column(name = "weight_kg", nullable = false)
    private double weightKg;

    @Column(name = "estimated_delivery", nullable = false, length = 50)
    private String estimatedDelivery;

    @Column(name = "created_at", nullable = false, length = 50)
    private String createdAt;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "is_priority_featured", nullable = false)
    private boolean isPriorityFeatured;

    public ShipmentJpaEntity() {
    }

    public ShipmentJpaEntity(
            String id,
            String trackingCode,
            String senderName,
            String recipientName,
            String originCity,
            String destinationCity,
            String status,
            String priority,
            double weightKg,
            String estimatedDelivery,
            String createdAt,
            String notes,
            boolean isPriorityFeatured
    ) {
        this.id = id;
        this.trackingCode = trackingCode;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.status = status;
        this.priority = priority;
        this.weightKg = weightKg;
        this.estimatedDelivery = estimatedDelivery;
        this.createdAt = createdAt;
        this.notes = notes;
        this.isPriorityFeatured = isPriorityFeatured;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getOriginCity() { return originCity; }
    public void setOriginCity(String originCity) { this.originCity = originCity; }

    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public String getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(String estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isPriorityFeatured() { return isPriorityFeatured; }
    public void setPriorityFeatured(boolean priorityFeatured) { isPriorityFeatured = priorityFeatured; }
}
