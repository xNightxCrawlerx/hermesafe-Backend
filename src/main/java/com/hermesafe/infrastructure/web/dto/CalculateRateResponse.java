package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload containing the calculated shipping rate breakdown")
public record CalculateRateResponse(
        @Schema(description = "Total calculated shipping cost amount", example = "138.0")
        double amount,

        @Schema(description = "Evaluated shipment weight in kilograms", example = "3.0")
        double weightKg,

        @Schema(description = "Evaluated travel distance in kilometers", example = "50")
        int distanceKm,

        @Schema(description = "Whether the rural surcharge was applied", example = "true")
        boolean rural
) {
}
