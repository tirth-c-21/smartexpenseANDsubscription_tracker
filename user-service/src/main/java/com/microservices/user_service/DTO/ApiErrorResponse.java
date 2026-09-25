package com.microservices.user_service.DTO;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

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
