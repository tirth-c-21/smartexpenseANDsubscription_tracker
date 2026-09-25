package com.microservices.expense_service.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.microservices.expense_service.DTO.ExpenseRequestDTO;
import com.microservices.expense_service.DTO.ExpenseResponseDTO;
import com.microservices.expense_service.entity.Expense;
import com.microservices.expense_service.exception.ExpenseNotFoundException;
import com.microservices.expense_service.repository.ExpenseRepo;

@Service
public class ExpenseService {
	
	private ExpenseRepo expenseRepo;
	
	public ExpenseService(ExpenseRepo expenseRepo) {
		super();
		this.expenseRepo = expenseRepo;
	}



	public void createExpense(ExpenseRequestDTO expensereqDTO)
	{
		
		Expense newexpense= new Expense();
		newexpense.setAmount(expensereqDTO.getAmount());
		newexpense.setCategory(expensereqDTO.getCategory());
		newexpense.setDescription(expensereqDTO.getDescription());
		newexpense.setUserid(expensereqDTO.getUserid());
		newexpense.setCreatedAt(LocalDate.now());
		expenseRepo.save(newexpense);
	}



	public ResponseEntity<?> getExpenseByuser(Long userid) 
	{
		List<Expense> userExpense= expenseRepo.findByUserid(userid);
		List<ExpenseResponseDTO> expenseResponde=new ArrayList();
		for(Expense x: userExpense)
		{
				ExpenseResponseDTO e= new ExpenseResponseDTO();
				e.setAmount(x.getAmount());
				e.setCategory(x.getCategory());
				e.setCreatedAt(x.getCreatedAt());
				e.setDescription(x.getDescription());
				e.setId(x.getId());
				expenseResponde.add(e);
		}	
		return new ResponseEntity(expenseResponde,HttpStatus.OK);
	}



	public void updateExpense(ExpenseRequestDTO expensereqDTO, Long id) 
	{
		
		
		Optional<Expense> userExpense= expenseRepo.findById(id);
		if(userExpense.isPresent())
		{
			userExpense.get().setAmount(expensereqDTO.getAmount());
			userExpense.get().setCategory(expensereqDTO.getCategory());
			userExpense.get().setCreatedAt(LocalDate.now());
			userExpense.get().setDescription(expensereqDTO.getDescription());
			userExpense.get().setUserid(expensereqDTO.getUserid());
			expenseRepo.save(userExpense.get());
		}
		else
			throw new ExpenseNotFoundException();
			
	}


	public void deleteExpense(Long id) {
		Optional<Expense> userExpense= expenseRepo.findById(id);
		if(userExpense.isPresent())
			expenseRepo.delete(userExpense.get());
		else
			throw new ExpenseNotFoundException();
	}
	
	

}
