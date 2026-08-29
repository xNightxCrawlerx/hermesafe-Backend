package com.hermesafe.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Standardized error response payload for centralized exception handling")
public record ErrorResponse(
        @Schema(description = "Timestamp when the error occurred", example = "2026-08-29T18:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,

        @Schema(description = "HTTP status code", example = "422")
        int status,

        @Schema(description = "HTTP status description", example = "Unprocessable Entity")
        String error,

        @Schema(description = "Specific business or technical error message", example = "Not enough stock available")
        String message,

        @Schema(description = "Target API endpoint path where error was triggered", example = "/api/orders/process")
        String path
) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, path);
    }
}
