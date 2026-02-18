package com.example.test.demandlane.exception;

import com.example.test.demandlane.model.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(BadRequestException e) {
        String traceId = MDC.get("traceId");
        log.warn("Validation Error [{}]: {}", traceId, e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String traceId = MDC.get("traceId");
        String message = e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        log.warn("Validation Request Error [{}]: {}", traceId, message);
        return ResponseEntity.badRequest().body(ApiResponse.error(message, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        String traceId = MDC.get("traceId");
        log.error("Unhandled Exception [{}]: {}", traceId, e.getMessage());
        return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage(), null));
    }

}
