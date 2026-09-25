package com.microservices.user_service.DTO;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Getter
@Setter
public class RegisterRequest {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@Email @NotBlank
	private String emailId;

}
