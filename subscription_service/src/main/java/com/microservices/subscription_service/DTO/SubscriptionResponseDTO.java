package com.microservices.subscription_service.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.microservices.subscription_service.frequency.Frequency;

import lombok.Data;

@Data
public class SubscriptionResponseDTO {
	
	private long id;
	private String name;
	private BigDecimal amount;
	private LocalDate renewalDate;
	private Frequency frequency;
	private LocalDate createdAt;

}
