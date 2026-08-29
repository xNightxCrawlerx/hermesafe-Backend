package com.hermesafe.infrastructure.web.controller;

import com.hermesafe.application.usecase.ProcessOrderUseCase;
import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.repository.InventoryRepository;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.infrastructure.web.dto.ErrorResponse;
import com.hermesafe.infrastructure.web.dto.ProcessOrderRequest;
import com.hermesafe.infrastructure.web.dto.ProcessOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order & Inventory Management", description = "Endpoints for processing customer orders and managing warehouse product inventory stock")
public class OrderController {

    private final ProcessOrderUseCase processOrderUseCase;
    private final InventoryRepository inventoryRepository;

    public OrderController(ProcessOrderUseCase processOrderUseCase, InventoryRepository inventoryRepository) {
        this.processOrderUseCase = processOrderUseCase;
        this.inventoryRepository = inventoryRepository;
    }

    @Operation(
            summary = "Process a product order",
            description = "Validates inventory availability and deducts the requested units from the warehouse stock."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order processed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessOrderResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid payload parameters",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Insufficient inventory stock available",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessOrderResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable Entity - Business rule violation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/process")
    public ResponseEntity<ProcessOrderResponse> processOrder(@RequestBody ProcessOrderRequest request) {
        if (request.productId() == null || request.productId().isBlank()) {
            return ResponseEntity.badRequest().body(
                    new ProcessOrderResponse(false, "Product ID cannot be empty", request.productId(), 0));
        }
        if (request.quantity() <= 0) {
            return ResponseEntity.badRequest().body(
                    new ProcessOrderResponse(false, "Quantity must be greater than 0", request.productId(), 0));
        }

        ProductId productId = new ProductId(request.productId());
        boolean success = processOrderUseCase.execute(productId, request.quantity());

        int remainingStock = inventoryRepository.getStock(request.productId());

        if (success) {
            return ResponseEntity.ok(
                    new ProcessOrderResponse(true, "Order processed successfully", request.productId(),
                            remainingStock));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ProcessOrderResponse(false, "Insufficient stock available", request.productId(),
                            remainingStock));
        }
    }

    @Operation(
            summary = "Get available product stock",
            description = "Retrieves the current available inventory stock quantity for a given product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Stock level retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid product ID parameter"
            )
    })
    @GetMapping("/stock/{productId}")
    public ResponseEntity<Map<String, Object>> getStock(@PathVariable String productId) {
        int stock = inventoryRepository.getStock(productId);
        return ResponseEntity.ok(Map.of(
                "productId", productId,
                "availableStock", stock));
    }

    @Operation(
            summary = "Add stock to inventory catalog",
            description = "Increases the available inventory quantity for a specified product ID in the warehouse catalog."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Stock added successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid quantity or parameters"
            )
    })
    @PostMapping("/stock")
    public ResponseEntity<Map<String, Object>> addStock(@RequestParam String productId, @RequestParam int quantity) {
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Quantity must be positive"));
        }
        ProductId pid = new ProductId(productId);
        InventoryItem item = inventoryRepository.findByProductId(pid)
                .orElse(new InventoryItem(pid, 0));
        item.addStock(quantity);
        inventoryRepository.save(item);

        return ResponseEntity.ok(Map.of(
                "message", "Stock added successfully",
                "productId", productId,
                "currentStock", item.getStock()));
    }
}
