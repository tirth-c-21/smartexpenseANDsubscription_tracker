package com.microservices.user_service.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservices.user_service.DTO.LoginRequest;
import com.microservices.user_service.DTO.RegisterRequest;
import com.microservices.user_service.entity.AppUser;
import com.microservices.user_service.exception.UserAlreadyExistsException;
import com.microservices.user_service.repo.UserRepo;

@Service
public class UserService {

	private final UserRepo userrepo;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepo userrepo, PasswordEncoder passwordEncoder) {
		super();
		this.userrepo = userrepo;
		this.passwordEncoder = passwordEncoder;
	}

	public void userRegister(RegisterRequest rRequest) {
		
		Optional<AppUser> getUser = userrepo.findByUserName(rRequest.getUsername());
		if(getUser.isPresent())
			throw new UserAlreadyExistsException("user already exist");
		else
		{
		AppUser newuserCreation = new AppUser();
		newuserCreation.setCreatedAt(LocalDateTime.now());
		newuserCreation.setEmailId(rRequest.getEmailId());
		newuserCreation.setPassword(passwordEncoder.encode(rRequest.getPassword()));
		newuserCreation.setUsername(rRequest.getUsername());
		userrepo.save(newuserCreation);
		}
	}

	public boolean userLogin(LoginRequest loginReq) {
		Optional<AppUser> getUser = userrepo.findByUserName(loginReq.getUsername());

		if (getUser.isPresent() && passwordEncoder.matches(loginReq.getPassword(), getUser.get().getPassword()))
			return true;
		else
			throw new BadCredentialsException("user credential mismatch");
	}

}
