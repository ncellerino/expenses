package com.smartbusiness;

import org.bson.types.ObjectId;
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
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.smartbusiness.util.DBUtils;
import com.smartbusiness.util.TestData;

import de.svenkubiak.embeddedmongodb.EmbeddedMongo;

@ActiveProfiles({ "unit-test" })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({ "server.port=9000", "management.port=9001" })
public class UserControllerIT {

	private static String URL = "http://localhost:9000/api/users";

	RestTemplate template = new TestRestTemplate();

	@Before
	public void setUp() {
		DBUtils.addUserMailConstraint();
	}

	@After
	public void tearDown() {
		DBUtils.cleanUserCollection();
	}

	@Test
	public void getUser() throws JsonProcessingException {
		String id = insertDefaultUser();

		ResponseEntity<UserDTO> okResponse = executeGet(id);
		Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());
	}

	@Test
	public void deleteUser() throws JsonProcessingException {
		String id = insertDefaultUser();
		ResponseEntity<UserDTO> okResponse = executeGet(id);
		Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());

		executeDelete(id);
		ResponseEntity<UserDTO> notFoundResponse = executeGet(id);
		Assert.assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
	}

	@Test
	public void updateUser() throws JsonProcessingException {
		String id = insertDefaultUser();
		ResponseEntity<UserDTO> okResponse = executeGet(id);

		UserDTO user = okResponse.getBody();
		user.setAge(45);
		user.setFirstName("Mark");
		user.setAddress("any other address");
		user.setPhone("63636362");
		user.setLastName("Marker");
		user.setMail("newmail@mail.com");

		ResponseEntity<UserDTO> putResponse = executePut(user, TestData.getDefaultUserToken());
		Assert.assertEquals(HttpStatus.OK, putResponse.getStatusCode());
		Assert.assertEquals(user, putResponse.getBody());
	}

	@Test
	public void updateUserSameMail() throws JsonProcessingException {
		insertDefaultUser();

		String id = insertUser("anymail@mail.com", "pieman", "admin", "Homer", "Simpson", 45, "1232134",
				"123 fake st, Springfield");
		ResponseEntity<UserDTO> okResponse = executeGet(id);

		UserDTO user = okResponse.getBody();
		user.setAge(45);
		user.setFirstName("Mark");
		user.setAddress("any other address");
		user.setPhone("63636362");
		user.setLastName("Marker");
		// use the mail of the existent user
		user.setMail("clark.kent@dc.com");

		ResponseEntity<UserDTO> putResponse = executePut(user, TestData.getDefaultUserToken());
		Assert.assertEquals(HttpStatus.CONFLICT, putResponse.getStatusCode());
		Assert.assertEquals(user, putResponse.getBody());
	}

	private ResponseEntity<UserDTO> executeGet(String userId) {
		return executeGet(userId, TestData.getDefaultUserToken());
	}

	private ResponseEntity<UserDTO> executeGet(String userId, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		return template.exchange(URL + "/" + userId, HttpMethod.GET, entity, UserDTO.class);
	}

	private void executeDelete(String userId) {
		template.delete(URL + "/" + userId, UserDTO.class);
	}

	private ResponseEntity<UserDTO> executePut(UserDTO userDTO, String token) {
		template.put(URL, userDTO);
		return executeGet(userDTO.getId(), token);
	}

	private static DB getDBClient() {
		MongoClient mongoClient = EmbeddedMongo.DB.port(29019).getMongoClient();
		DB db = mongoClient.getDB("test");
		return db;
	}

	private String insertDefaultUser() throws JsonProcessingException {
		String jsonUser = getDefaultUser();
		// convert JSON to DBObject directly
		return insertJsonUser(jsonUser);
	}

	private String insertJsonUser(String jsonUser) {
		// convert JSON to DBObject directly
		DBObject dbObject = (DBObject) JSON.parse(jsonUser);

		getDBClient().getCollection("user").insert(dbObject);
		ObjectId id = (ObjectId) dbObject.get("_id");
		return id.toString();
	}

	private String insertUser(String mail, String username, String role, String firstName, String lastName, int age,
			String phone, String address) throws JsonProcessingException {
		String jsonUser = TestData.getUserJson(mail, username, null, null, role, firstName, lastName, age, phone,
				address);
		return insertJsonUser(jsonUser);
	}

	private String getDefaultUser() throws JsonProcessingException {
		return TestData.getUserJson("clark.kent@dc.com", "superman", null, null, "admin", "Clark", "Kent", 35,
				"12395995", "543 fake street, Smallville");
	}
}
