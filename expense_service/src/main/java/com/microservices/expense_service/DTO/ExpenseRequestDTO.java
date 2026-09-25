package com.microservices.expense_service.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpenseRequestDTO {
	
	@NotNull
	private Long userid;
	@NotNull
	private BigDecimal amount;
	@NotBlank
	private String category;
	@NotBlank
	private String description;
}
