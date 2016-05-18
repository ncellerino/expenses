package com.smartbusiness.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.smartbusiness.dto.UserDTO;
import com.smartbusiness.model.User;

public class TestData {

	public static UserDTO getUserDTO() {
		return new UserDTO(null, "Bruce", "Wayne", "bruce.wayne@dc.com", 32, "13234534", "123 fake street, gotham");
	}

	public static String getUserJson() throws JsonProcessingException {
		User user = new User.UserBuilder(null, "clark.kent@dc.com", "Clark", "Kent", null, null).age(35).phone("12395995")
				.address("543 fake street, Smallville").build();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(user);
		return json;
	}
}
