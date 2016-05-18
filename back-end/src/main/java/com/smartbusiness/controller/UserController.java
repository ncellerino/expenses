package com.smartbusiness.controller;

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
	public UserDTO getBasicGoal(@PathVariable String id) {
		return service.getUser(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserDTO saveBasicGoal(@RequestBody UserDTO userDTO) {
		UserDTO result = null;
		result = service.saveUser(userDTO);
		return result;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserDTO updateUser(@RequestBody UserDTO userDTO) {
		UserDTO result = null;
		result = service.saveUser(userDTO);
		return result;
	}

}
