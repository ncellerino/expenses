package com.smartbusiness;

import java.net.HttpRetryException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.expenses.Application;
import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserToSaveDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.smartbusiness.util.DBUtils;
import com.smartbusiness.util.TestData;

@ActiveProfiles({ "unit-test" })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({ "server.port=9000", "management.port=9001" })
public class LoginRegisterControllerIT {

	private static String URL = "http://localhost:9000";

	RestTemplate template = new TestRestTemplate();

	@Before
	public void setUp() throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		DBUtils.addUserMailConstraint();
		DBUtils.insertDefaultUser();
	}

	@After
	public void tearDown() {
		DBUtils.cleanUserCollection();
	}

	@Test
	public void login() throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		String username = "superman";
		String password = "pass";
		ResponseEntity<LoggedUserDTO> response = executePost(username, password);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		LoggedUserDTO loggedUserDTO = response.getBody();
		Assert.assertNotNull(loggedUserDTO);
		Assert.assertEquals(username, loggedUserDTO.getUsername());
		Assert.assertNotNull(loggedUserDTO.getToken());	
	}

	@Test
	public void loginWrongPassword() throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		String username = "superman";
		String password = "wrongPass";
		ResponseEntity<LoggedUserDTO> response = executePost(username, password);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void loginNonExistentUser()
			throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		String username = "fakeUser";
		String password = "pass";
		ResponseEntity<LoggedUserDTO> response = executePost(username, password);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void registerUser() {
		UserToSaveDTO userToSaveDTO = TestData.getUserDTO();
		ResponseEntity<LoggedUserDTO> response = executePost(userToSaveDTO);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		LoggedUserDTO loggedUserDTO = response.getBody();
		Assert.assertNotNull(loggedUserDTO);
		Assert.assertEquals(userToSaveDTO.getUsername(), loggedUserDTO.getUsername());
		Assert.assertEquals(userToSaveDTO.getRole(), loggedUserDTO.getRole());
		Assert.assertNotNull(loggedUserDTO.getToken());
	}

	@Test
	public void registerDuplicateUser() {
		UserToSaveDTO userDTO = TestData.getUserDTO();
		ResponseEntity<LoggedUserDTO> okResponse = executePost(userDTO);
		Assert.assertEquals(HttpStatus.CREATED, okResponse.getStatusCode());

		// save the basic goal again --> should return an error
		ResponseEntity<LoggedUserDTO> conflictResponse = executePost(userDTO);
		Assert.assertEquals(HttpStatus.CONFLICT, conflictResponse.getStatusCode());
	}

	private ResponseEntity<LoggedUserDTO> executePost(String username, String password) {
		return template.postForEntity(URL + "/login?username=" + username + "&password=" + password, null,
				LoggedUserDTO.class);
	}

	private ResponseEntity<LoggedUserDTO> executePost(UserToSaveDTO userDTO) {
		ResponseEntity<LoggedUserDTO> response = null;
		try {
			response = template.postForEntity(URL + "/register", userDTO, LoggedUserDTO.class);
		} catch (ResourceAccessException e) {
			HttpRetryException re = (HttpRetryException) e.getCause();
			HttpStatus.valueOf(re.responseCode());
			response = new ResponseEntity<>(HttpStatus.valueOf(re.responseCode()));
		}
		return response;
	}

}
