package com.microservices.subscription_service.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.microservices.subscription_service.frequency.Frequency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionRequestDTO {
	
	@NotNull
	private long userid;
	@NotBlank
	private String name;
	
	@NotNull
	private BigDecimal amount;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate renewalDate;
	private Frequency frequency;

}
