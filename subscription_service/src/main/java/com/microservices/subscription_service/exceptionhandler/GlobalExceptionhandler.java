package com.microservices.subscription_service.exceptionhandler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservices.subscription_service.DTO.ApiErrorResponse;
import com.microservices.subscription_service.exception.SubscriptionNotFoundException;


@RestControllerAdvice
public class GlobalExceptionhandler {

	@ExceptionHandler(SubscriptionNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> noExpenseFound(SubscriptionNotFoundException ex) {

		return new ResponseEntity<>(new ApiErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
		return new ResponseEntity<>(new ApiErrorResponse("something went wrong"), 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

