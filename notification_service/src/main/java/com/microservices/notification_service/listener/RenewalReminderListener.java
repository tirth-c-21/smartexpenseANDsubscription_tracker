package com.microservices.notification_service.listener;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RenewalReminderListener {

	@Value("${spring.mail.username}")
	private String mailUsername;
	
	private final JavaMailSender mailSender;

	public RenewalReminderListener(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

    @KafkaListener(topics = "renewal-reminders", groupId = "notification-service-group")
    public void handleRenewalReminder(String message) {
    	 	
//    	System.out.println("Here's the received message "+message);
    	
    	SimpleMailMessage newmessage = new SimpleMailMessage();
    	newmessage.setTo(mailUsername);
    	newmessage.setSubject("Subscription Renewal: ");

    	String content = "Hi,\n\n" + message + "\n\n— Smart Expense & Subscription Tracker Team";

		newmessage.setText(content);

		mailSender.send(newmessage);
    }
}