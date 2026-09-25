package com.microservices.subscription_service.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.microservices.subscription_service.frequency.Frequency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Subscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long userid;
	private String name;
	private BigDecimal amount;
	private LocalDate renewalDate;
	private Frequency frequency;
	private LocalDate createdAt;

}
