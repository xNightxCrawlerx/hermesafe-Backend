package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload to update shipment lifecycle status")
public record UpdateShipmentStatusRequest(
        @Schema(description = "New lifecycle status (PENDING, IN_TRANSIT, DELIVERED, CANCELLED)", example = "IN_TRANSIT", requiredMode = Schema.RequiredMode.REQUIRED)
        String status
) {
}
