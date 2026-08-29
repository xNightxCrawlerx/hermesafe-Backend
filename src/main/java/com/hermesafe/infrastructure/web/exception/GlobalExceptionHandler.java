package com.hermesafe.infrastructure.web.exception;

import com.hermesafe.domain.exception.InsufficientStockException;
import com.hermesafe.domain.exception.InvalidDimensionsException;
import com.hermesafe.domain.exception.InvalidOrderStatusException;
import com.hermesafe.domain.exception.InvalidPostalCodeException;
import com.hermesafe.domain.exception.InvalidWeightException;
import com.hermesafe.infrastructure.web.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 422 Unprocessable Entity - Excepciones de negocio e invariantes de dominio
     */
    @ExceptionHandler({
            InsufficientStockException.class,
            InvalidDimensionsException.class,
            InvalidPostalCodeException.class,
            InvalidWeightException.class,
            InvalidOrderStatusException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorResponse> handleDomainBusinessExceptions(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.warn("Business domain exception intercepted: {} at {}", ex.getMessage(), request.getRequestURI());

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorResponse response = ErrorResponse.of(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }

    /**
     * 400 Bad Request - Argumentos inválidos, parámetros faltantes, JSON malformado o errores de tipo
     */
    @ExceptionHandler({
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(
            Exception ex,
            HttpServletRequest request
    ) {
        log.warn("Bad request exception intercepted: {} at {}", ex.getMessage(), request.getRequestURI());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = ErrorResponse.of(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }

    /**
     * 500 Internal Server Error - Fallback perimetral para errores técnicos no controlados.
     * Registra la traza internamente en el log y devuelve un mensaje seguro sin exponer información interna.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unhandled server exception at {}: ", request.getRequestURI(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.of(
                status.value(),
                status.getReasonPhrase(),
                "Ha ocurrido un error interno inesperado en el servidor.",
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }
}
