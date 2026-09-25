package com.microservices.expense_service.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseResponseDTO {
	
	private Long id;
	private BigDecimal amount;
	private String category;
	private String description;
	private LocalDate createdAt;

}
