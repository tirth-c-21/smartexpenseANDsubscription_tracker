package com.microservices.subscription_service.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.microservices.subscription_service.entity.Subscription;
import com.microservices.subscription_service.repo.SubscriptionRepo;

@Component
public class RenewalReminderScheduler {
	
	private final SubscriptionRepo subscriptionRepo; 
	private final KafkaTemplate<String, String> kafkaTemplate;

	public RenewalReminderScheduler(SubscriptionRepo subscriptionRepo, KafkaTemplate<String, String> kafkaTemplate) {
	    this.subscriptionRepo = subscriptionRepo;
	    this.kafkaTemplate = kafkaTemplate;
	}

//    @Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(fixedRate = 100000) // for testing purpose, checking in every 5 second */
    public void checkUpcomingRenewals() {
    	LocalDate date = LocalDate.now();
    	LocalDate dateinNext_3days= LocalDate.now().plusDays(3);
    	List<Subscription> subscriptions=subscriptionRepo.findByRenewalDateBetween(date,dateinNext_3days);
    	
    	for( Subscription subscription : subscriptions)
    	{
    		String message = "User " + subscription.getUserid() + ": " + subscription.getName() + " subscription is renewing soon on " + subscription.getRenewalDate();
    	    kafkaTemplate.send("renewal-reminders", message);
    	    System.out.println("message "+message);
    	}
    }
}