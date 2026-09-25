package com.microservices.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.microservices.user_service.service.UserService;

import jakarta.validation.Valid;

import com.microservices.user_service.DTO.LoginRequest;
import com.microservices.user_service.DTO.LoginResponse;
import com.microservices.user_service.DTO.RegisterRequest;
import com.microservices.user_service.security.JwtUtil;

@RestController
public class Usercontroller {

	private final UserService userService;
	private final JwtUtil jwtUtil;

	public Usercontroller(UserService userService, JwtUtil jwtUtil) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/smartEtracker/hi")
	public String greetings() {
		return "Hi Tirth";
	}

	@PostMapping("/public/auth/register")
	public ResponseEntity<String> userRegistration(@Valid @RequestBody RegisterRequest rRequest) {
		userService.userRegister(rRequest);
		return new ResponseEntity<String>("Registration successful!", HttpStatus.CREATED);
	}

	@PostMapping("/public/auth/login")
	public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginReq) {
		LoginResponse loginresp = new LoginResponse();
		if (userService.userLogin(loginReq)) {
			String token = jwtUtil.generateToken(loginReq.getUsername());
			loginresp.setToken(token);
			loginresp.setUsername(loginReq.getUsername());
		}
		return new ResponseEntity<>(loginresp, HttpStatus.OK);
	}

	@GetMapping("/api/test/secure")
	public ResponseEntity<String> secureTest() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok("Hello " + username + ", you are authenticated!");
	}

}
