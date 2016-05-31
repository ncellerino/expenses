package com.expenses.model;

public enum UserRoles {

	DEFAULT("default"), ADMIN("admin");

	private UserRoles(String role) {
		this.name = role;
	}

	private String name;

	public String getName() {
		return name;
	}

}
