package com.expenses.model;

public class BaseUser {

	private final String id;
	private final String username;
	private final String role;

	public BaseUser(String id, String username, String role) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

}
