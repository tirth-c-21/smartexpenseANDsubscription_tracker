package com.microservices.expense_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.expense_service.entity.Expense;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long>{
	
	List<Expense> findByUserid(Long userid);

}
