package com.expenses.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class UserDTO {

	private String id;
	private String firstName;
	private String lastName;
	private String mail;
	private String username;
	private String role;
	private int age;
	private String phone;
	private String address;

	public UserDTO() {

	};

	public UserDTO(String id, String firstName, String lastName, String mail, String username, String role, int age,
			String phone, String address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.username = username;
		this.role = role;
		this.age = age;
		this.phone = phone;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj1) {
		if (this == obj1)
			return true;
		if (obj1 == null || getClass() != obj1.getClass())
			return false;

		UserDTO fc1 = (UserDTO) obj1;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.getFirstName(), fc1.getFirstName());
		builder.append(this.getLastName(), fc1.getLastName());
		builder.append(this.getAddress(), fc1.getAddress());
		builder.append(this.getMail(), fc1.getMail());
		builder.append(this.getUsername(), fc1.getUsername());
		builder.append(this.getRole(), fc1.getRole());
		builder.append(this.getPhone(), fc1.getPhone());
		builder.append(this.getAge(), fc1.getAge());

		return builder.isEquals();
	}

}