package com.expenses.service;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserDTO;
import com.expenses.dto.UserToSaveDTO;

public interface UserService {

	/**
	 * Get a existent user by its id.
	 * 
	 * @param id
	 * @return the user dto.
	 */
	public UserDTO getUser(String id);

	/**
	 * Get an existent user by its username and password.
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return the user dto.
	 */
	public LoggedUserDTO getUserByUsernameAndPassword(String username, String password);

	/**
	 * Get a existent user by mail.
	 * 
	 * @param mail
	 * @return the user dto.
	 */
	public UserDTO getUserByMail(String mail);

	/**
	 * Save a new user in the system.
	 * 
	 * @param userDTO
	 * @return the user dto created.
	 */
	public UserDTO saveUser(UserToSaveDTO userDTO);

	/**
	 * Update an existent user in the system.
	 * 
	 * @param userDTO
	 * @return the user dto updated.
	 */
	public UserDTO updateUser(UserDTO userDTO);

	/**
	 * Delete a existent user in the system.
	 * 
	 * @param id
	 */
	public void deleteUser(String id);
}
