package com.expenses.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserDTO;
import com.expenses.dto.UserToSaveDTO;

import java.util.Optional;

public interface UserService extends UserDetailsService{

	/**
	 * Get a existent user by its username.
	 * 
	 * @param username
	 * @return the user dto.
	 */
	public Optional<UserDTO> getUser(String username);

	/**
	 * Get an existent user by its username and password.
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return the user dto.
	 */
	public Optional<LoggedUserDTO> getUserByUsernameAndPassword(String username, String password);

	/**
	 * Get a existent user by mail.
	 * 
	 * @param mail
	 * @return the user dto.
	 */
	public Optional<UserDTO> getUserByMail(String mail);

	/**
	 * Save a new user in the system.
	 * 
	 * @param userDTO
	 * @return the user dto created.
	 */
	public LoggedUserDTO saveUser(UserToSaveDTO userDTO);

	/**
	 * Update an existent user in the system.
	 * 
	 * @param userDTO
	 */
	public void updateUser(UserDTO userDTO);

	/**
	 * Delete a existent user in the system.
	 * 
	 * @param username
	 */
	public void deleteUser(String username);
}
