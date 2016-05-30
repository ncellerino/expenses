package com.expenses.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bson.types.ObjectId;

import com.expenses.security.PasswordEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import de.svenkubiak.embeddedmongodb.EmbeddedMongo;

public class DBUtils {

	public static String insertDefaultUser()
			throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		DBObject userDocument = getDefaultUser();
		// convert JSON to DBObject directly
		return insertDocument("user", userDocument);
	}

	public static void addUserMailConstraint() {
		DB db = DBUtils.getDBClient();
		DBCollection myCollection = db.getCollection("user");
		myCollection.createIndex(new BasicDBObject("mail", 1), new BasicDBObject("unique", true));
	}

	private static String insertDocument(String collection, DBObject document) {
		getDBClient().getCollection(collection).insert(document);
		ObjectId id = (ObjectId) document.get("_id");
		return id.toString();
	}

	private static String insertJsonUser(String jsonUser) {
		// convert JSON to DBObject directly
		DBObject dbObject = (DBObject) JSON.parse(jsonUser);

		getDBClient().getCollection("user").insert(dbObject);
		ObjectId id = (ObjectId) dbObject.get("_id");
		return id.toString();
	}

	private static DBObject getDefaultUser()
			throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {
		String password = "pass";
		PasswordEncoder pe = new PasswordEncoder();
		byte[] salt = pe.generateSalt();
		byte[] encodedPass = pe.getEncryptedPassword(password, salt);

		DBObject documentDetail = new BasicDBObject();
		documentDetail.put("mail", "clark.kent@dc.com");
		documentDetail.put("username", "superman");
		documentDetail.put("passwordHash", encodedPass);
		documentDetail.put("passwordSalt", salt);
		documentDetail.put("firstName", "Clark");
		documentDetail.put("lastName", "Kent");
		documentDetail.put("age", 35);
		documentDetail.put("phone", "12395995");
		documentDetail.put("address", "543 fake street, Smallville");

		return documentDetail;
	}

	public static DB getDBClient() {
		MongoClient mongoClient = EmbeddedMongo.DB.port(29019).getMongoClient();
		DB db = mongoClient.getDB("test");
		return db;
	}

	public static void cleanUserCollection() {
		DB db = getDBClient();
		DBCollection myCollection = db.getCollection("user");
		myCollection.remove(new BasicDBObject());
	}

}
