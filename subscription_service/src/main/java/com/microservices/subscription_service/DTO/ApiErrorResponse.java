package com.microservices.subscription_service.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(String message) {

        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
