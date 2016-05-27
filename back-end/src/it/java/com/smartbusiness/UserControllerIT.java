package com.smartbusiness;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.expenses.Application;
import com.expenses.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.smartbusiness.util.DBUtils;
import com.smartbusiness.util.RestUtils;
import com.smartbusiness.util.TestData;

@ActiveProfiles({ "unit-test" })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({ "server.port=9000", "management.port=9001" })
public class UserControllerIT {

	private static String URL = "http://localhost:9000/api/users";

	RestTemplate template = new TestRestTemplate();
	private String defaultUserToken;

	@Before
	public void setUp() {
		DBUtils.addUserMailConstraint();
		defaultUserToken = RestUtils.getDefaultUserToken();
	}

	@After
	public void tearDown() {
		DBUtils.cleanUserCollection();
	}

	@Test
	public void getUser() throws JsonProcessingException {
		ResponseEntity<UserDTO> okResponse = executeGet(TestData.DEFAULT_USERNAME);
		Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());
	}

	@Test
	public void deleteUser() throws JsonProcessingException {
		executeDelete(TestData.DEFAULT_USERNAME);
		ResponseEntity<UserDTO> notFoundResponse = executeGet(TestData.DEFAULT_USERNAME);
		Assert.assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
	}

	@Test
	public void updateUser() throws JsonProcessingException {
		ResponseEntity<UserDTO> okResponse = executeGet(TestData.DEFAULT_USERNAME);

		UserDTO user = okResponse.getBody();
		user.setAge(45);
		user.setFirstName("Mark");
		user.setAddress("any other address");
		user.setPhone("63636362");
		user.setLastName("Marker");
		user.setMail("newmail@mail.com");

		ResponseEntity<UserDTO> putResponse = executePut(user, defaultUserToken);
		Assert.assertEquals(HttpStatus.OK, putResponse.getStatusCode());
		Assert.assertEquals(user, putResponse.getBody());
	}

	private ResponseEntity<UserDTO> executeGet(String username) {
		return executeGet(username, defaultUserToken);
	}

	private ResponseEntity<UserDTO> executeGet(String username, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		return template.exchange(URL + "/" + username, HttpMethod.GET, entity, UserDTO.class);
	}

	private void executeDelete(String username) {
		template.delete(URL + "/" + username, UserDTO.class);
	}

	private ResponseEntity<UserDTO> executePut(UserDTO userDTO, String token) {
		template.put(URL, userDTO);
		return executeGet(userDTO.getUsername(), token);
	}

}
