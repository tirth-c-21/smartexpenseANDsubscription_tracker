package com.microservices.subscription_service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.microservices.subscription_service.DTO.SubscriptionRequestDTO;
import com.microservices.subscription_service.entity.Subscription;
import com.microservices.subscription_service.exception.SubscriptionNotFoundException;
import com.microservices.subscription_service.frequency.Frequency;
import com.microservices.subscription_service.repo.SubscriptionRepo;
import com.microservices.subscription_service.service.SubscriptionService;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepo subscriptionRepo;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void createSubscription_shouldSucceed_whenValid() {
    	
    	SubscriptionRequestDTO newSubscription= new SubscriptionRequestDTO();
    	newSubscription.setUserid(1);
		newSubscription.setRenewalDate(LocalDate.now());
		newSubscription.setName("Test subscription pack");
		newSubscription.setFrequency(Frequency.WEEKLY);
		newSubscription.setAmount(BigDecimal.valueOf(999.0));
		
		
		Subscription savedSubscription  = new Subscription();
		savedSubscription .setUserid(newSubscription.getUserid());
		savedSubscription .setRenewalDate(newSubscription.getRenewalDate());
		savedSubscription .setName(newSubscription.getName());
		savedSubscription .setFrequency(newSubscription.getFrequency());
		savedSubscription .setCreatedAt(LocalDate.now());
		savedSubscription .setAmount(BigDecimal.valueOf(999.0));
    	
		 when(subscriptionRepo.save(any(Subscription.class))).thenReturn(savedSubscription);

		    // Act — call the REAL method, capture its actual return value
		    subscriptionService.createASubscription(newSubscription);

		    // Assert — for a void method, you verify BEHAVIOR, not a return value
			/*
			 * it's not checking a return value, it's checking
			 * "was save() actually called, exactly once, with a Subscription object?"
			 */
		    verify(subscriptionRepo, times(1)).save(any(Subscription.class));
    }

    @Test
    void updateSubscription_shouldThrowNotFound_whenIdMissing() {
    	SubscriptionRequestDTO someRequestDTO= new SubscriptionRequestDTO();
    	someRequestDTO.setUserid(1);
    	someRequestDTO.setRenewalDate(LocalDate.now());
    	someRequestDTO.setName("Test subscription pack!!");
    	someRequestDTO.setFrequency(Frequency.WEEKLY);
    	someRequestDTO.setAmount(BigDecimal.valueOf(999.0));
    	
    	when(subscriptionRepo.findById(anyLong())).thenReturn(Optional.empty());
    	assertThrows(SubscriptionNotFoundException.class, () ->
             subscriptionService.updateSubscriptionById(someRequestDTO,999L)
         );
        // Arrange: when(subscriptionRepo.findById(anyLong())).thenReturn(Optional.empty());

        // Act + Assert:
        // assertThrows(SubscriptionNotFoundException.class, () ->
        //     subscriptionService.updateSubscription(999L, someRequestDTO)
        // );
    }

    @Test
    void deleteSubscription_shouldThrowNotFound_whenIdMissing() {
    	
    	when(subscriptionRepo.findById(anyLong())).thenReturn(Optional.empty());
    	assertThrows(SubscriptionNotFoundException.class, () ->
        subscriptionService.deleteSubscriptionById(999L)
    );
        // Same pattern as update — findById returns Optional.empty(), assert exception thrown
    }
}
