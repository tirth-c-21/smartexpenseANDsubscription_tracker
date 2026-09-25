package com.microservices.expense_service.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.expense_service.DTO.ExpenseRequestDTO;
import com.microservices.expense_service.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
public class ExpenseController {
	
	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		super();
		this.expenseService = expenseService;
	}

	@PostMapping("expense_service/expenses")
	public ResponseEntity<String> createExpense( @Valid @RequestBody ExpenseRequestDTO expensereqDTO)
	{
		expenseService.createExpense(expensereqDTO);
		return new ResponseEntity<String>("Expense added", HttpStatus.CREATED);
	}
	
	@GetMapping("expense_service/{userid}")
	public ResponseEntity<?> getExpensesByUser(@PathVariable Long userid)
	{
		return expenseService.getExpenseByuser(userid);
	}
	
	@PostMapping("expense_service/updateexpenses/{id}")
	public ResponseEntity<String> updateExpense( @Valid @RequestBody ExpenseRequestDTO expensereqDTO,@PathVariable Long id)
	{
		expenseService.updateExpense(expensereqDTO,id);
		return new ResponseEntity<String>("Expense updated", HttpStatus.OK);
	}
	
	@DeleteMapping("expense_service/deleteexpense/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable Long id)
	{
		expenseService.deleteExpense(id);
		return new ResponseEntity<String>("Expense deleted", HttpStatus.OK);
	}
	
	@GetMapping("/expense_service/Hi")
	public String greetings() 
	{
		return "Hi Tirth";
	}
	
}
