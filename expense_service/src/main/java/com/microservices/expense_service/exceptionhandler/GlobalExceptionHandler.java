package com.microservices.expense_service.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservices.expense_service.DTO.ApiErrorResponse;
import com.microservices.expense_service.exception.ExpenseNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpenseNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> noExpenseFound() {

		return new ResponseEntity<>(new ApiErrorResponse("expense not found with Id you are seaching"), HttpStatus.NOT_FOUND);
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
