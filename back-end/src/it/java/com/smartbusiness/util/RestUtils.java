package com.smartbusiness.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserToSaveDTO;

public class RestUtils {

	private static RestTemplate template = new RestTemplate();

	public static ResponseEntity<LoggedUserDTO> callLoginEndpoint(String host, String username, String password) {
		return executePost(host + "/login?username=" + username + "&password=" + password, null);
	}

	public static ResponseEntity<LoggedUserDTO> callRegisterEndpoint(String host, UserToSaveDTO userDTO) {
		return executePost(host + "/register", userDTO);
	}

	public static ResponseEntity<LoggedUserDTO> executePost(String url, UserToSaveDTO userDTO) {
		ResponseEntity<LoggedUserDTO> response = null;
		try {
			response = template.postForEntity(url, userDTO, LoggedUserDTO.class);
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
