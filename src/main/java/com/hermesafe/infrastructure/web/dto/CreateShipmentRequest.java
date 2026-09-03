package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload to register a new shipment in the logistics system")
public record CreateShipmentRequest(
        @Schema(description = "Tracking code (optional, auto-generated if empty)", example = "HMS-849201-CL")
        String trackingCode,

        @Schema(description = "Sender company or person name", example = "TechSolutions Chile SpA", requiredMode = Schema.RequiredMode.REQUIRED)
        String senderName,

        @Schema(description = "Recipient person name", example = "Ignacio Morales Vera", requiredMode = Schema.RequiredMode.REQUIRED)
        String recipientName,

        @Schema(description = "Dispatch origin city", example = "Santiago", requiredMode = Schema.RequiredMode.REQUIRED)
        String originCity,

        @Schema(description = "Destination city", example = "Concepción", requiredMode = Schema.RequiredMode.REQUIRED)
        String destinationCity,

        @Schema(description = "Service priority", example = "EXPRESS", defaultValue = "STANDARD")
        String priority,

        @Schema(description = "Initial status", example = "PENDING", defaultValue = "PENDING")
        String status,

        @Schema(description = "Package weight in kg", example = "4.5", minimum = "0.1")
        double weightKg,

        @Schema(description = "Special shipping notes", example = "Manejar con cuidado")
        String notes
) {
}
