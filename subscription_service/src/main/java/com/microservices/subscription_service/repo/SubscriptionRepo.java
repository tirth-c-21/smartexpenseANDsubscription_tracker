package com.microservices.subscription_service.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.microservices.subscription_service.entity.Subscription;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long>{


	@Query("SELECT s FROM Subscription s WHERE s.userid=?1 ")
	List<Subscription> findByUserid(Long userid);
	
	@Query("SELECT s FROM Subscription s WHERE s.renewalDate>=?1 and s.renewalDate<=?2 ")
	List<Subscription> findByRenewalDateBetween(LocalDate date, LocalDate dateinNext_3days);
	
//	String findByName();
}
