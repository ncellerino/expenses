package com.smartbusiness.service;

import com.smartbusiness.dto.UserDTO;

public interface UserService {

	/**
	 * Get a existent user by its id.
	 * 
	 * @param id
	 * @return the user dto.
	 */
	public UserDTO getUser(String id);

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
	public UserDTO saveUser(UserDTO userDTO);

	/**
	 * Delete a existent user in the system.
	 * 
	 * @param id
	 */
	public void deleteUser(String id);
}
