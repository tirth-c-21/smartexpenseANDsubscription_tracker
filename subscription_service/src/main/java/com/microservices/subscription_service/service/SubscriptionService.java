package com.microservices.subscription_service.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservices.subscription_service.DTO.ApiErrorResponse;
import com.microservices.subscription_service.DTO.SubscriptionRequestDTO;
import com.microservices.subscription_service.DTO.SubscriptionResponseDTO;
import com.microservices.subscription_service.entity.Subscription;
import com.microservices.subscription_service.exception.SubscriptionNotFoundException;
import com.microservices.subscription_service.repo.SubscriptionRepo;

import jakarta.validation.Valid;

@Service
public class SubscriptionService {

	private final SubscriptionRepo subscriptionRepo;
	
	public SubscriptionService(SubscriptionRepo subscriptionRepo) {
		super();
		this.subscriptionRepo = subscriptionRepo;
	}

	public void createASubscription(@Valid SubscriptionRequestDTO sreq) {
		Subscription newSubscription = new Subscription();
		newSubscription.setUserid(sreq.getUserid());
		newSubscription.setRenewalDate(sreq.getRenewalDate());
		newSubscription.setName(sreq.getName());
		newSubscription.setFrequency(sreq.getFrequency());
		newSubscription.setCreatedAt(LocalDate.now());
		newSubscription.setAmount(sreq.getAmount());
		
		subscriptionRepo.save(newSubscription);
	}

	public ResponseEntity<?> getSubscriptionsByuserid(Long userid){
		List<Subscription> getfromDB= subscriptionRepo.findByUserid(userid);
		
		List<SubscriptionResponseDTO> SubscriptionResponselist= new ArrayList<>();
		for(Subscription eachsubscription: getfromDB)
		{
			SubscriptionResponseDTO responsedto= new SubscriptionResponseDTO();
			responsedto.setAmount(eachsubscription.getAmount());
			responsedto.setCreatedAt(eachsubscription.getCreatedAt());
			responsedto.setFrequency(eachsubscription.getFrequency());
			responsedto.setName(eachsubscription.getName());
			responsedto.setRenewalDate(eachsubscription.getRenewalDate());
			responsedto.setId(eachsubscription.getId());
			SubscriptionResponselist.add(responsedto);
		}
		if(SubscriptionResponselist.size()==0)
			throw new SubscriptionNotFoundException("No Subscription found for the logged in user");
		return new ResponseEntity<>(SubscriptionResponselist, HttpStatus.OK);	
	}

	public ResponseEntity<String> updateSubscriptionById(@Valid SubscriptionRequestDTO sreq,
			Long id) {
		
		Optional<Subscription> getfromDB= subscriptionRepo.findById(id);
		if(getfromDB.isPresent())
		{
			getfromDB.get().setAmount(sreq.getAmount());
			getfromDB.get().setFrequency(sreq.getFrequency());
			getfromDB.get().setName(sreq.getName());
			getfromDB.get().setRenewalDate(sreq.getRenewalDate());
			getfromDB.get().setUserid(sreq.getUserid());
			
			subscriptionRepo.save(getfromDB.get());
			return new ResponseEntity<>("your Subscription Pack have been updated!", HttpStatus.OK);
		}
		throw new SubscriptionNotFoundException("No Subscription has been found for the id");
	}

	public void deleteSubscriptionById(Long id) {
		Optional<Subscription> getfromDB= subscriptionRepo.findById(id);
		if(getfromDB.isPresent())
			subscriptionRepo.delete(getfromDB.get());
		else
			throw new SubscriptionNotFoundException("No Subscription have been found for the Requestd id");
		
		
	}
	
	

}
