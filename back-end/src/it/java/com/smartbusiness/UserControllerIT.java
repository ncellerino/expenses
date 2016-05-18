package com.smartbusiness;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.smartbusiness.dto.UserDTO;
import com.smartbusiness.util.TestData;

import de.svenkubiak.embeddedmongodb.EmbeddedMongo;

@ActiveProfiles({ "unit-test" })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({ "server.port=9000", "management.port=9001" })
public class UserControllerIT {

	private static String URL = "http://localhost:9000/users";

	RestTemplate template = new TestRestTemplate();

	@After
	public void tearDown() {
		DB db = getDBClient();
		DBCollection myCollection = db.getCollection("user");
		myCollection.remove(new BasicDBObject());
	}

	@Test
	public void saveUser() {
		UserDTO userDTO = TestData.getUserDTO();
		ResponseEntity<UserDTO> response = executePost(userDTO);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(userDTO, response.getBody());
	}

	@Test
	public void saveDuplicateUser() {
		UserDTO userDTO = TestData.getUserDTO();
		ResponseEntity<UserDTO> okResponse = executePost(userDTO);
		Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());

		// save the basic goal again --> should return an error
		ResponseEntity<UserDTO> conflictResponse = executePost(userDTO);
		Assert.assertEquals(HttpStatus.CONFLICT, conflictResponse.getStatusCode());
	}

	@Test
	public void getUser() throws JsonProcessingException {
		String id = insertUser();

		ResponseEntity<UserDTO> okResponse = executeGet(id);
		Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());
	}

	private ResponseEntity<UserDTO> executePost(UserDTO userDTO) {
		ResponseEntity<UserDTO> response = null;
		try {
			response = template.postForEntity(URL, userDTO, UserDTO.class);
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return response;
	}

	private ResponseEntity<UserDTO> executeGet(String userId) {
		return template.getForEntity(URL + "/" + userId, UserDTO.class);
	}

	private void executePut(UserDTO userDTO) {
		template.put(URL, userDTO);
	}

	private DB getDBClient() {
		MongoClient mongoClient = EmbeddedMongo.DB.port(29019).getMongoClient();
		DB db = mongoClient.getDB("test");
		return db;
	}

	private String insertUser() throws JsonProcessingException {
		String jsonUser = TestData.getUserJson();
		// convert JSON to DBObject directly
		DBObject dbObject = (DBObject) JSON.parse(jsonUser);

		getDBClient().getCollection("user").insert(dbObject);
		ObjectId id = (ObjectId) dbObject.get("_id");
		return id.toString();
	}

	// @Test
	// public void saveEmtpyBasicFinancialGoalBuilder() {
	// BasicFinancialGoalDTO expectedGoal = new
	// JsonFileReader().getBasicFinancialGoal(
	// "/json/basicFinancialGoalEmpty.json",
	// SmartMoneyConstants.SAVE_BASIC_FINANCIAL_GOAL, 2);
	// ResponseEntity<BasicFinancialGoalDTO> response =
	// executePost(expectedGoal);
	// Assert.assertEquals(expectedGoal, response.getBody());
	// }
	//
	// @Test(expected = ServiceException.class)
	// public void saveBasicFinancialGoalBuilderSameDateAndUser() {
	// BasicFinancialGoalDTO expectedGoal = new
	// JsonFileReader().getBasicFinancialGoal("/json/basicFinancialGoal.json",
	// SmartMoneyConstants.SAVE_BASIC_FINANCIAL_GOAL, 3L);
	// executePost(expectedGoal);
	//
	// // save the basic goal again --> should return an error
	// executePost(expectedGoal);
	// }
	//
	// @Test
	// public void saveBasicFinancialGoalBuilderNoRequiredFields() {
	// BasicFinancialGoalDTO expectedGoal = new
	// JsonFileReader().getBasicFinancialGoal(
	// "/json/basicFinancialGoalNoDate.json",
	// SmartMoneyConstants.SAVE_BASIC_FINANCIAL_GOAL, 4);
	// ResponseEntity<BasicFinancialGoalDTO> response =
	// executePost(expectedGoal);
	// Assert.assertNotNull(response);
	// Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	// }
	//
	// @Test
	// public void saveBasicFinancialGoalBuilderDateWrongFormat() {
	// BasicFinancialGoalDTO expectedGoal = new
	// JsonFileReader().getBasicFinancialGoal(
	// "/json/basicFinancialGoalDateWrongFormat.json",
	// SmartMoneyConstants.SAVE_BASIC_FINANCIAL_GOAL, 5);
	// ResponseEntity<BasicFinancialGoalDTO> response =
	// executePost(expectedGoal);
	// Assert.assertNotNull(response);
	// Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	// }
	//
	// @Test
	// public void saveBasicFinancialGoalBuilderNoVariableCosts() {
	// BasicFinancialGoalDTO expectedGoal = new
	// JsonFileReader().getBasicFinancialGoal(
	// "/json/basicFinancialGoalNoVariableCosts.json",
	// SmartMoneyConstants.SAVE_BASIC_FINANCIAL_GOAL, 6);
	// ResponseEntity<BasicFinancialGoalDTO> response =
	// executePost(expectedGoal);
	// Assert.assertNotNull(response);
	// Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	// }
	//

	//
	// @Test
	// public void updateBasicFinancialGoalBuilder() {
	// long userId = 8;
	// BasicFinancialGoalDTO goal = new
	// JsonFileReader().getBasicFinancialGoal("/json/basicFinancialGoal.json",
	// SmartMoneyConstants.SAVE_BASIC_FINANCIAL_GOAL, userId);
	// ResponseEntity<BasicFinancialGoalDTO> response = executePost(goal);
	// Assert.assertNotNull(response);
	// Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	// goal = response.getBody();
	//
	// BasicFinancialGoalDTO updatedExpectedGoal = new
	// JsonFileReader().getBasicFinancialGoal(
	// "/json/updatedBasicFinancialGoal.json",
	// SmartMoneyConstants.UPDATE_BASIC_FINANCIAL_GOAL, userId);
	// updatedExpectedGoal.setId(goal.getId());
	// executePut(updatedExpectedGoal);
	//
	// ResponseEntity<BasicFinancialGoalDTO> responseGet = executeGet(userId);
	// Assert.assertNotNull(responseGet);
	// Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	// BasicFinancialGoalDTO actualUpdatedGoal = responseGet.getBody();
	//
	// Assert.assertEquals(updatedExpectedGoal, actualUpdatedGoal);
	// }

}
