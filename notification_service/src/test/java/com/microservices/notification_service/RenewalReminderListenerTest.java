package com.microservices.notification_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.microservices.notification_service.listener.RenewalReminderListener;

@ExtendWith(MockitoExtension.class)
public class RenewalReminderListenerTest {
	
	@Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private RenewalReminderListener listener;

    @Test
    void handleRenewalReminder_shouldSendEmail_whenMessageReceived() {
    	
    	listener.handleRenewalReminder("Your subscription is going to be ended soon!");
    	verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
}
}
