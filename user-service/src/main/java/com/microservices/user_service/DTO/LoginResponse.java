package com.microservices.user_service.DTO;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LoginResponse {

	private String username;
	private String token;

}
