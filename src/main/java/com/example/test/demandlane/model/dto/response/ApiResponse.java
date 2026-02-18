package com.example.test.demandlane.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

@Setter
@Getter
public class ApiResponse<T> {

    private boolean success;
    private String traceId;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.traceId = MDC.get("traceId");
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}
