package com.microservices.subscription_service.exception;

import lombok.Data;

@Data
public class SubscriptionNotFoundException extends RuntimeException{
	private String message;
	
	public SubscriptionNotFoundException(String message) {
		super(message);
		this.message=message;
	}

}
