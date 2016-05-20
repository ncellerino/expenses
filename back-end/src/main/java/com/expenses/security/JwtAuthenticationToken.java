package com.expenses.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private String token;

	public JwtAuthenticationToken(String jwtToken) {	
		super(null, null);
		this.token = jwtToken;
	}


	public String getToken() {
		return token;
	}



}
