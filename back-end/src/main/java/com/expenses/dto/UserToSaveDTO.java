package com.expenses.dto;

public class UserToSaveDTO extends UserDTO {

	private String password;
	
	public UserToSaveDTO(){
		
	}

	public UserToSaveDTO(String id, String firstName, String lastName, String mail, String password, int age,
			String phone, String address) {
		super(id, firstName, lastName, mail, age, phone, address);
		setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
