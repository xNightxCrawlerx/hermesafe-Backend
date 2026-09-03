package com.hermesafe.infrastructure.web.controller;

import com.hermesafe.application.usecase.CalculateShippingRateUseCase;
import com.hermesafe.domain.valueobject.Distance;
import com.hermesafe.domain.valueobject.ShippingRate;
import com.hermesafe.domain.valueobject.Weight;
import com.hermesafe.infrastructure.web.dto.CalculateRateRequest;
import com.hermesafe.infrastructure.web.dto.CalculateRateResponse;
import com.hermesafe.infrastructure.web.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping-rates")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Tag(name = "Shipping Rate Calculation", description = "Endpoints for calculating freight and delivery shipping rates based on weight, distance, and zone surcharges")
public class ShippingRateController {

    private final CalculateShippingRateUseCase calculateShippingRateUseCase;

    public ShippingRateController(CalculateShippingRateUseCase calculateShippingRateUseCase) {
        this.calculateShippingRateUseCase = calculateShippingRateUseCase;
    }

    @Operation(
            summary = "Calculate shipping rate via JSON body",
            description = "Calculates total delivery cost based on parcel weight (kg), transport distance (km), and rural delivery area surcharge (15%)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Shipping rate calculated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalculateRateResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Missing or invalid payload parameters",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable Entity - Negative distance or non-positive weight domain violation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/calculate")
    public ResponseEntity<CalculateRateResponse> calculateRate(@RequestBody CalculateRateRequest request) {
        Weight weight = new Weight(request.weightKg());
        Distance distance = new Distance(request.distanceKm());
        ShippingRate rate = calculateShippingRateUseCase.execute(weight, distance, request.rural());

        return ResponseEntity.ok(
                new CalculateRateResponse(rate.amount(), request.weightKg(), request.distanceKm(), request.rural())
        );
    }

    @Operation(
            summary = "Calculate shipping rate via URL parameters",
            description = "Convenience GET endpoint calculating shipping cost using query parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Shipping rate calculated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalculateRateResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid query parameters",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable Entity - Domain invariant violated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/calculate")
    public ResponseEntity<CalculateRateResponse> calculateRateGet(
            @RequestParam double weight,
            @RequestParam int distance,
            @RequestParam(defaultValue = "false") boolean rural) {
        Weight w = new Weight(weight);
        Distance d = new Distance(distance);
        ShippingRate rate = calculateShippingRateUseCase.execute(w, d, rural);

        return ResponseEntity.ok(
                new CalculateRateResponse(rate.amount(), weight, distance, rural)
        );
    }
}
