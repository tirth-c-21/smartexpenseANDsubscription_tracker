package com.microservices.user_service.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.microservices.user_service.security.JwtUtil;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "SECRET", "someLongTestSecretKeyThatIs32CharsOrMore");
        ReflectionTestUtils.setField(jwtUtil, "expirationTime", 86400000L);
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        String token = jwtUtil.generateToken("testuser");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromJwtToken_shouldReturnCorrectUsername() {
        String token = jwtUtil.generateToken("testuser");

        String username = jwtUtil.getUsernameFromJwtToken(token);
        assertEquals("testuser", username);
    }

    @Test
    void validateJwtToken_shouldReturnFalse_whenTokenTampered() {
        String token = jwtUtil.generateToken("testuser");
        String tamperedToken = token + "abc";

        boolean isValid = jwtUtil.validateJwtToken(tamperedToken);

        assertFalse(isValid);
    }
}