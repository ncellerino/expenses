package com.expenses.util;

import com.expenses.dto.UserToSaveDTO;
import com.expenses.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TestData {
	
	public static final String DEFAULT_USERNAME = "batman";
	public static final String DEFAULT_PASSWORD = "password";
	
	public static final String DEFAULT_HOST = "http://localhost:9000";

	public static User getDefaultUser() {
		return new User.UserBuilder(null, DEFAULT_USERNAME, "batman", "admin", "Bruce", "Wayne", null, null)
				.address("123 fake street, gotham").phone("13234534").age(32).build();
	}
	
	public static String getDefaultUserJson() throws JsonProcessingException{
		User user = getDefaultUser();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(user);
	}

	public static UserToSaveDTO getUserDTO() {
		return new UserToSaveDTO(null, "Bruce", "Wayne", "bruce.wayne@dc.com", "batman", "admin", "mypass", 32,
				"13234534", "123 fake street, gotham");
	}

	public static String getUserJson(String mail, String username, byte[] password, byte[] salt, String role,
			String firstName, String lastName, int age, String phone, String address) throws JsonProcessingException {
		User user = buildUser(mail, username, password, salt, role, firstName, lastName, age, phone, address);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(user);
	}
	
	private static User buildUser(String mail, String username, byte[] password, byte[] salt, String role,
			String firstName, String lastName, int age, String phone, String address){
		return  new User.UserBuilder(null, mail, username, role, firstName, lastName, password, salt).age(age)
				.phone(phone).address(address).build();
	}
}
