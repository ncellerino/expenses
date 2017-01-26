package com.expenses.util;

import com.expenses.dto.UserToAuthenticateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserToSaveDTO;

public class RestUtils {

	private static RestTemplate template = new RestTemplate();

	public static ResponseEntity<LoggedUserDTO> callLoginEndpoint(String host, UserToAuthenticateDTO userToAuthenticateDTO) {
		return executePost(host + "/login", userToAuthenticateDTO, LoggedUserDTO.class);
	}

	public static ResponseEntity<LoggedUserDTO> callRegisterEndpoint(String host, UserToSaveDTO userDTO) {
		return executePost(host + "/register", userDTO, LoggedUserDTO.class);
	}

	public static ResponseEntity<LoggedUserDTO> executePost(String url, Object userDTO, Class bodyObjectClass) {
		ResponseEntity<LoggedUserDTO> response = null;
		try {
			response = template.postForEntity(url, userDTO, bodyObjectClass);
		} catch (HttpStatusCodeException e) {			
			response = new ResponseEntity<>(e.getStatusCode());
		}
		return response;
	}
	
	public static String getDefaultUserToken(){
		ResponseEntity<LoggedUserDTO> response = callRegisterEndpoint(TestData.DEFAULT_HOST, TestData.getUserDTO());
		LoggedUserDTO loggedUserDTO = response.getBody();
		return loggedUserDTO.getToken();
	}

}
