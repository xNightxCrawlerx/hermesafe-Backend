package com.hermesafe.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload indicating order processing result and remaining stock")
public record ProcessOrderResponse(
        @Schema(description = "Whether the order was successfully processed", example = "true")
        boolean success,

        @Schema(description = "Descriptive outcome message", example = "Order processed successfully")
        String message,

        @Schema(description = "Identifier of the ordered product", example = "ITEM-100")
        String productId,

        @Schema(description = "Remaining available stock in inventory after the operation", example = "15")
        int remainingStock
) {
}
