package com.hermesafe.infrastructure.web.controller;

import com.hermesafe.application.usecase.OptimizeRouteUseCase;
import com.hermesafe.infrastructure.web.dto.CoverageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Tag(name = "Route Optimization & Coverage", description = "Endpoints for finding optimal warehouse dispatch paths and checking delivery zone coverage")
public class RouteController {

    private final OptimizeRouteUseCase optimizeRouteUseCase;

    public RouteController(OptimizeRouteUseCase optimizeRouteUseCase) {
        this.optimizeRouteUseCase = optimizeRouteUseCase;
    }

    @Operation(
            summary = "Get prioritized closest warehouses",
            description = "Returns an ordered list of closest dispatch warehouses evaluated by the route optimization algorithm."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of warehouses retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(type = "string", example = "Santiago")))
            )
    })
    @GetMapping("/closest-warehouses")
    public ResponseEntity<List<String>> getClosestWarehouses() {
        List<String> warehouses = optimizeRouteUseCase.execute();
        return ResponseEntity.ok(warehouses);
    }

    @Operation(
            summary = "Check city delivery coverage",
            description = "Checks whether the requested city is supported by the Hermesafe distribution logistics network."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Coverage status retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CoverageResponse.class))
            )
    })
    @GetMapping("/coverage/{city}")
    public ResponseEntity<CoverageResponse> checkCoverage(@PathVariable String city) {
        boolean covered = optimizeRouteUseCase.isCovered(city);
        return ResponseEntity.ok(new CoverageResponse(city, covered));
    }
}
