package com.expenses.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthenticatedUser extends User {

	private static final long serialVersionUID = 1332325882730453250L;

	public AuthenticatedUser(String id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	private String id;

	public String getId() {
		return id;
	}
}
