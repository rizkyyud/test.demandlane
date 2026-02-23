package com.example.test.demandlane.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;


public record ApiResponse<T>(
        boolean success,
        String traceId,
        String message,
        T data
) {

    public static <T> ApiResponse<T> success(String traceId, String message, T data) {
        return new ApiResponse<>(true, traceId, message, data);
    }

    public static <T> ApiResponse<T> error(String traceId, String message, T data) {
        return new ApiResponse<>(false, traceId, message, data);
    }
}
