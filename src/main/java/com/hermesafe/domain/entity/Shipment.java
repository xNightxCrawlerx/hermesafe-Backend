package com.hermesafe.domain.entity;

import java.time.LocalDate;

public class Shipment {
    private final String id;
    private final String trackingCode;
    private final String senderName;
    private final String recipientName;
    private final String originCity;
    private final String destinationCity;
    private String status;
    private final String priority;
    private final double weightKg;
    private final String estimatedDelivery;
    private final String createdAt;
    private final String notes;
    private final boolean isPriorityFeatured;

    public Shipment(
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
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Shipment ID cannot be null or empty");
        }
        if (senderName == null || senderName.isBlank()) {
            throw new IllegalArgumentException("Sender name cannot be null or empty");
        }
        if (recipientName == null || recipientName.isBlank()) {
            throw new IllegalArgumentException("Recipient name cannot be null or empty");
        }
        if (originCity == null || originCity.isBlank()) {
            throw new IllegalArgumentException("Origin city cannot be null or empty");
        }
        if (destinationCity == null || destinationCity.isBlank()) {
            throw new IllegalArgumentException("Destination city cannot be null or empty");
        }
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }

        this.id = id.trim();
        this.trackingCode = (trackingCode != null && !trackingCode.isBlank())
                ? trackingCode.trim()
                : "HMS-" + (100000 + (long)(Math.random() * 900000)) + "-CL";
        this.senderName = senderName.trim();
        this.recipientName = recipientName.trim();
        this.originCity = originCity.trim();
        this.destinationCity = destinationCity.trim();
        this.status = (status != null && !status.isBlank()) ? status.trim().toUpperCase() : "PENDING";
        this.priority = (priority != null && !priority.isBlank()) ? priority.trim().toUpperCase() : "STANDARD";
        this.weightKg = weightKg;
        this.estimatedDelivery = (estimatedDelivery != null && !estimatedDelivery.isBlank())
                ? estimatedDelivery.trim()
                : LocalDate.now().plusDays(3).toString();
        this.createdAt = (createdAt != null && !createdAt.isBlank())
                ? createdAt.trim()
                : LocalDate.now().toString();
        this.notes = notes != null ? notes.trim() : "";
        this.isPriorityFeatured = isPriorityFeatured;
    }

    public String getId() { return id; }
    public String getTrackingCode() { return trackingCode; }
    public String getSenderName() { return senderName; }
    public String getRecipientName() { return recipientName; }
    public String getOriginCity() { return originCity; }
    public String getDestinationCity() { return destinationCity; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public double getWeightKg() { return weightKg; }
    public String getEstimatedDelivery() { return estimatedDelivery; }
    public String getCreatedAt() { return createdAt; }
    public String getNotes() { return notes; }
    public boolean isPriorityFeatured() { return isPriorityFeatured; }

    public void updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        this.status = newStatus.trim().toUpperCase();
    }
}
