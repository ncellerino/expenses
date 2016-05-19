package com.smartbusiness.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartbusiness.dto.UserDTO;
import com.smartbusiness.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable String id, HttpServletResponse response) {
		UserDTO userDTO = service.getUser(id);
		if (userDTO == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return userDTO;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String id) {
		service.deleteUser(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserDTO saveBasicGoal(@RequestBody UserDTO userDTO, HttpServletResponse response) {
		UserDTO result = null;
		result = service.saveUser(userDTO);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return result;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserDTO updateUser(@RequestBody UserDTO userDTO) {
		UserDTO result = null;
		result = service.saveUser(userDTO);
		return result;
	}

}
