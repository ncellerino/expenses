package com.smartbusiness.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@CompoundIndexes(value = { @CompoundIndex(name = "user_mail_idx", def = "{'mail':1}", unique = true) })
public class User {

	private final String id;
	private final String firstName;
	private final String lastName;
	private final String mail;
	private final byte[] passwordHash;
	private final byte[] passwordSalt;
	private final int age;
	private final String phone;
	private final String address;
	
	@PersistenceConstructor
	protected User(String id, String mail, String firstName, String lastName, byte[] passwordHash,
			byte[] passwordSalt, String phone, String address, int age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.passwordHash = passwordHash;
		this.passwordSalt = passwordSalt;
		this.phone = phone;
		this.address = address;
		this.age = age;
	}

	private User(UserBuilder userBuilder) {
		id = userBuilder.id;
		firstName = userBuilder.firstName;
		lastName = userBuilder.lastName;
		mail = userBuilder.mail;
		passwordHash = userBuilder.passwordHash;
		passwordSalt = userBuilder.passwordSalt;
		age = userBuilder.age;
		phone = userBuilder.phone;
		address = userBuilder.address;
	}

	public static class UserBuilder {
		private final String id;
		private final String firstName;
		private final String lastName;
		private final String mail;
		private final byte[] passwordHash;
		private final byte[] passwordSalt;
		private int age;
		private String phone;
		private String address;

		public UserBuilder(String id, String mail, String firstName, String lastName, byte[] passwordHash,
				byte[] passwordSalt) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.mail = mail;
			this.passwordHash = passwordHash;
			this.passwordSalt = passwordSalt;
		}

		public UserBuilder phone(String phone) {
			this.phone = phone;
			return this;
		}

		public UserBuilder address(String address) {
			this.address = address;
			return this;
		}

		public UserBuilder age(int age) {
			this.age = age;
			return this;
		}

		public User build() {
			return new User(this);
		}

	}

	public String getFirstName() {
		return firstName;
	}

	public String getMail() {
		return mail;
	}

	public int getAge() {
		return age;
	}

	public String getLastName() {
		return lastName;
	}

	public String getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public byte[] getPasswordHash() {
		return passwordHash;
	}

	public byte[] getPasswordSalt() {
		return passwordSalt;
	}
}
