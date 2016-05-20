package com.expenses.dto;

public class LoggedUserDTO {

	private String username;
	private String token;
	private String role;

	public LoggedUserDTO() {

	}

	public LoggedUserDTO(String username, String token, String role) {
		this.username = username;
		this.token = token;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getToken() {
		return token;
	}

	public String getRole() {
		return role;
	}
}
