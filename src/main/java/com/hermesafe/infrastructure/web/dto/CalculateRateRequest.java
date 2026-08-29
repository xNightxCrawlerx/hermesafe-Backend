package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for calculating shipping rates based on package weight, distance, and zone")
public record CalculateRateRequest(
        @Schema(description = "Total weight of the shipment in kilograms", example = "3.0", minimum = "0.001")
        double weightKg,

        @Schema(description = "Total transport distance in kilometers", example = "50", minimum = "0")
        int distanceKm,

        @Schema(description = "Flag indicating if delivery destination is in a rural area (applies 15% surcharge)", example = "true")
        boolean rural
) {
}
