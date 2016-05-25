package com.smartbusiness.util;

import com.expenses.dto.UserToSaveDTO;
import com.expenses.model.User;
import com.expenses.security.JwtAuthenticationService;
import com.expenses.security.JwtTokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TestData {
	
	private static final String DEFAULT_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYXRtYW4iLCJ1c2VySWQiOiI1NzQzMjE2NzQ0YWVhOTk2YmVjYzM2YjMiLCJyb2xlIjoiYWRtaW4ifQ.JpKWq2idA5VmY5325tftNwUDPWdDA35i8zmDVLlM0IQ";

	public static String getDefaultUserToken() {
		JwtTokenHandler handler = new JwtTokenHandler();
		
		return DEFAULT_USER_TOKEN;
	}

	public static UserToSaveDTO getUserDTO() {
		return new UserToSaveDTO(null, "Bruce", "Wayne", "bruce.wayne@dc.com", "batman", "admin", "mypass", 32,
				"13234534", "123 fake street, gotham");
	}

	public static String getUserJson(String mail, String username, byte[] password, byte[] salt, String role, String firstName, String lastName,
			int age, String phone, String address) throws JsonProcessingException {
		User user = new User.UserBuilder(null, mail, username, role, firstName, lastName, password, salt).age(age)
				.phone(phone).address(address).build();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(user);
	}
}
