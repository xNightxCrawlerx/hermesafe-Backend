package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload to process an order and deduct inventory stock")
public record ProcessOrderRequest(
        @Schema(description = "Unique identifier of the product to order", example = "ITEM-100")
        String productId,

        @Schema(description = "Quantity of units to order and deduct from inventory", example = "5", minimum = "1")
        int quantity
) {
}
