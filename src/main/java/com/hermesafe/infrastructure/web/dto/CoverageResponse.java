package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload indicating geographic delivery coverage for a requested city")
public record CoverageResponse(
        @Schema(description = "Name of the target city evaluated", example = "Santiago")
        String city,

        @Schema(description = "True if Hermesafe logistics network has warehouse coverage in the city", example = "true")
        boolean covered
) {
}
