package com.microservices.subscription_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.subscription_service.DTO.SubscriptionRequestDTO;
import com.microservices.subscription_service.DTO.SubscriptionResponseDTO;
import com.microservices.subscription_service.service.SubscriptionService;

import jakarta.validation.Valid;

@RestController
public class SubscriptionController {
	
	private final SubscriptionService subscriptionService;
	
	public SubscriptionController(SubscriptionService subscriptionService) {
		super();
		this.subscriptionService = subscriptionService;
	}
	
	@GetMapping("/subscription_service/Hi")
	public String greetings() 
	{
		return "Hi Tirth, how are you?";
	}

	@PostMapping("/subscription_service/createsubs")
	public ResponseEntity<String> createSubscription(@Valid @RequestBody SubscriptionRequestDTO sreq)
	{
		subscriptionService.createASubscription(sreq);
		return new ResponseEntity<String>("Congrats your subscription has been added for "+sreq.getName()+"! ", HttpStatus.CREATED);
	}
	
	@GetMapping("/subscription_service/{userid}")
	public ResponseEntity<?> getSubscriptionsByUser(@PathVariable Long userid)
	{
		return subscriptionService.getSubscriptionsByuserid(userid);
	}
	
	@PostMapping("/subscription_service/{id}")
	public ResponseEntity<String> updateSubscription(@Valid @RequestBody SubscriptionRequestDTO sreq,
			@PathVariable Long id)
	{
		return subscriptionService.updateSubscriptionById(sreq,id);
	}
	
	@DeleteMapping("/subscription_service/delete/{id}")
	public ResponseEntity<String> deleteSubscription(@PathVariable Long id)
	{
		subscriptionService.deleteSubscriptionById(id);
		return new ResponseEntity<>("your Subscription Pack have been Deleted!", HttpStatus.OK);
	}
	
	

}
