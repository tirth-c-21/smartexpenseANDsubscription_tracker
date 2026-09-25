package com.microservices.user_service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservices.user_service.DTO.LoginRequest;
import com.microservices.user_service.entity.AppUser;
import com.microservices.user_service.repo.UserRepo;
import com.microservices.user_service.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userrepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void userLogin_shouldSucceed_whenCredentialsValid() {
        AppUser user = new AppUser();
        user.setUsername("demoUser");
        user.setPassword("hashedPassword");

        when(userrepo.findByUserName("demoUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("demoPassword", "hashedPassword")).thenReturn(true);

        // Act — call the REAL method
        boolean result = userService.userLogin(loginRequestWith("demoUser", "demoPassword"));

        assertTrue(result); // or whatever your actual return type/behavior is
    }

    @Test
    void userLogin_shouldThrowBadCredentials_whenPasswordWrong() {
        AppUser user = new AppUser();
        user.setUsername("demoUser");
        user.setPassword("hashedPassword");

        when(userrepo.findByUserName("demoUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () ->
            userService.userLogin(loginRequestWith("demoUser", "wrongPassword"))
        );
    }

    @Test
    void userLogin_shouldThrowBadCredentials_whenUserNotFound() {
        when(userrepo.findByUserName("ghostUser")).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () ->
            userService.userLogin(loginRequestWith("ghostUser", "anyPassword"))
        );
    }
    
    private LoginRequest loginRequestWith(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }
}
