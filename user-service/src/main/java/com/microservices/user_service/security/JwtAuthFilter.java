package com.microservices.user_service.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	// hint: inject JwtUtil here (constructor injection, same pattern as your other
	// classes)
	private final JwtUtil jwtUtil;

	public JwtAuthFilter(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Step 1: get "Authorization" header
		// Step 2: check if it starts with "Bearer "
		// Step 3: if yes, extract the token (strip "Bearer " prefix)
		// Step 4: validate token using jwtUtil.validateJwtToken(token)
		// Step 5: if valid, extract username using
		// jwtUtil.getUsernameFromJwtToken(token)
		// Step 6: create a UsernamePasswordAuthenticationToken(username, null, empty
		// authorities list)
		// Step 7: set it into SecurityContextHolder.getContext().setAuthentication(...)
		// Step 8: always call filterChain.doFilter(request, response) at the end —
		// don't skip this even if token is missing/invalid

		String headerAuth = request.getHeader("Authorization");

		if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
			String token = headerAuth.substring(7);

			if (jwtUtil.validateJwtToken(token)) {

				String username = jwtUtil.getUsernameFromJwtToken(token);

				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		}

		filterChain.doFilter(request, response);
	}

}
