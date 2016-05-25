package com.expenses.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthenticatedUser implements Authentication {

	private final User user;
	private boolean authenticated = true;

	private static final long serialVersionUID = 1332325882730453250L;

	public AuthenticatedUser(BaseUser user, String token, List<GrantedAuthority> authorityList) {
		this.user = new User(user.getUsername(), token, authorityList);
	}

	private String id;

	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return user.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}
}
