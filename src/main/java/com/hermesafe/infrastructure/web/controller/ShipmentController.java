package com.hermesafe.infrastructure.web.controller;

import com.hermesafe.application.usecase.CreateShipmentUseCase;
import com.hermesafe.application.usecase.GetShipmentUseCase;
import com.hermesafe.application.usecase.ListShipmentsUseCase;
import com.hermesafe.application.usecase.UpdateShipmentStatusUseCase;
import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.infrastructure.persistence.mapper.ShipmentMapper;
import com.hermesafe.infrastructure.web.dto.CreateShipmentRequest;
import com.hermesafe.infrastructure.web.dto.ErrorResponse;
import com.hermesafe.infrastructure.web.dto.ShipmentDto;
import com.hermesafe.infrastructure.web.dto.UpdateShipmentStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Tag(name = "Shipment & Package Tracking", description = "Endpoints for managing logistics shipments, tracking packages, and status transitions")
public class ShipmentController {

    private final CreateShipmentUseCase createShipmentUseCase;
    private final ListShipmentsUseCase listShipmentsUseCase;
    private final GetShipmentUseCase getShipmentUseCase;
    private final UpdateShipmentStatusUseCase updateShipmentStatusUseCase;

    public ShipmentController(
            CreateShipmentUseCase createShipmentUseCase,
            ListShipmentsUseCase listShipmentsUseCase,
            GetShipmentUseCase getShipmentUseCase,
            UpdateShipmentStatusUseCase updateShipmentStatusUseCase
    ) {
        this.createShipmentUseCase = createShipmentUseCase;
        this.listShipmentsUseCase = listShipmentsUseCase;
        this.getShipmentUseCase = getShipmentUseCase;
        this.updateShipmentStatusUseCase = updateShipmentStatusUseCase;
    }

    @Operation(summary = "Get all shipments", description = "Retrieves all active and historical shipments persisted in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of shipments retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShipmentDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<ShipmentDto>> getAllShipments() {
        List<ShipmentDto> dtos = listShipmentsUseCase.execute().stream()
                .map(ShipmentMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get shipment by ID", description = "Retrieves a specific shipment by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShipmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Shipment not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getShipmentById(@PathVariable String id) {
        Optional<Shipment> opt = getShipmentUseCase.execute(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ErrorResponse.of(404, "Not Found", "Shipment not found with ID: " + id, "/api/shipments/" + id)
            );
        }
        return ResponseEntity.ok(ShipmentMapper.toDto(opt.get()));
    }

    @Operation(summary = "Register a new shipment", description = "Creates and stores a new package shipment with validated tracking metadata into PostgreSQL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Shipment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShipmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Missing or invalid payload parameters",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createShipment(@RequestBody CreateShipmentRequest request) {
        if (request.senderName() == null || request.senderName().isBlank()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of(400, "Bad Request", "Sender name cannot be empty", "/api/shipments")
            );
        }
        if (request.recipientName() == null || request.recipientName().isBlank()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of(400, "Bad Request", "Recipient name cannot be empty", "/api/shipments")
            );
        }
        if (request.originCity() == null || request.originCity().isBlank()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of(400, "Bad Request", "Origin city cannot be empty", "/api/shipments")
            );
        }
        if (request.destinationCity() == null || request.destinationCity().isBlank()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of(400, "Bad Request", "Destination city cannot be empty", "/api/shipments")
            );
        }
        if (request.weightKg() <= 0) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of(400, "Bad Request", "Weight must be greater than 0", "/api/shipments")
            );
        }

        String id = "ENV-" + (1011 + new Random().nextInt(8900));
        String trackingCode = (request.trackingCode() != null && !request.trackingCode().isBlank())
                ? request.trackingCode().trim()
                : "HMS-" + (100000 + new Random().nextInt(900000)) + "-CL";

        String status = (request.status() != null && !request.status().isBlank())
                ? request.status().toUpperCase().trim()
                : "PENDING";

        String priority = (request.priority() != null && !request.priority().isBlank())
                ? request.priority().toUpperCase().trim()
                : "STANDARD";

        LocalDate now = LocalDate.now();
        String createdAt = now.toString();
        String estimatedDelivery = now.plusDays(3).toString();
        boolean isPriority = "EXPRESS".equalsIgnoreCase(priority) || "OVERNIGHT".equalsIgnoreCase(priority);

        Shipment domain = new Shipment(
                id,
                trackingCode,
                request.senderName().trim(),
                request.recipientName().trim(),
                request.originCity().trim(),
                request.destinationCity().trim(),
                status,
                priority,
                request.weightKg(),
                estimatedDelivery,
                createdAt,
                request.notes() != null ? request.notes().trim() : "",
                isPriority
        );

        Shipment saved = createShipmentUseCase.execute(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(ShipmentMapper.toDto(saved));
    }

    @Operation(summary = "Update shipment status", description = "Updates the lifecycle status of an existing shipment in PostgreSQL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShipmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid status value",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Shipment not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateShipmentStatus(
            @PathVariable String id,
            @RequestBody UpdateShipmentStatusRequest request) {

        if (request.status() == null || request.status().isBlank()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of(400, "Bad Request", "Status cannot be empty", "/api/shipments/" + id + "/status")
            );
        }

        String normalizedStatus = request.status().toUpperCase().trim();
        List<String> validStatuses = List.of("PENDING", "IN_TRANSIT", "DELIVERED", "CANCELLED");
        if (!validStatuses.contains(normalizedStatus)) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of(400, "Bad Request", "Invalid status value: " + normalizedStatus + ". Valid values are: " + validStatuses, "/api/shipments/" + id + "/status")
            );
        }

        Optional<Shipment> updated = updateShipmentStatusUseCase.execute(id, normalizedStatus);
        if (updated.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ErrorResponse.of(404, "Not Found", "Shipment not found with ID: " + id, "/api/shipments/" + id + "/status")
            );
        }

        return ResponseEntity.ok(ShipmentMapper.toDto(updated.get()));
    }
}
