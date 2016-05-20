package com.expenses.security;

/**
 * Enum with the roles managed by the application.
 * 
 * @author ncellerino
 *
 */
public enum ApplicationRole {

	ADMIN("administrator", "This user is allowed to perform CRUD operations on users and meals"), 
	USER_MANAGER("user-manager",
			"This user is allowed to execute CRUD operations on users");

	private String name;
	private String description;

	private ApplicationRole(String name, String description) {
		this.description = description;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
