package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a complete shipment package in the logistics network")
public record ShipmentDto(
        @Schema(description = "Shipment unique identifier", example = "ENV-1001")
        String id,

        @Schema(description = "Tracking tracking code", example = "HMS-849201-CL")
        String trackingCode,

        @Schema(description = "Sender company or person name", example = "TechSolutions Chile SpA")
        String senderName,

        @Schema(description = "Recipient person name", example = "Ignacio Morales Vera")
        String recipientName,

        @Schema(description = "Dispatch origin city", example = "Santiago")
        String originCity,

        @Schema(description = "Destination city", example = "Concepción")
        String destinationCity,

        @Schema(description = "Current lifecycle status", example = "IN_TRANSIT")
        String status,

        @Schema(description = "Shipping service priority", example = "EXPRESS")
        String priority,

        @Schema(description = "Package weight in kilograms", example = "4.5")
        double weightKg,

        @Schema(description = "Estimated delivery date (YYYY-MM-DD)", example = "2026-08-08")
        String estimatedDelivery,

        @Schema(description = "Creation date (YYYY-MM-DD)", example = "2026-08-06")
        String createdAt,

        @Schema(description = "Special instructions or notes", example = "Manejar con extremo cuidado.")
        String notes,

        @Schema(description = "Whether the shipment is featured in priority views", example = "true")
        Boolean isPriorityFeatured
) {
}
