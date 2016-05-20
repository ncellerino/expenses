package com.expenses.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserDTO;
import com.expenses.dto.UserToSaveDTO;
import com.expenses.service.UserService;

@RestController
public class LoginController {

	@Autowired
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoggedUserDTO getUser(@RequestParam String username, @RequestParam String password,
			HttpServletResponse response) {
		LoggedUserDTO userDTO = service.getUserByUsernameAndPassword(username, password);
		if (userDTO == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return userDTO;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String id) {
		service.deleteUser(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserDTO saveUser(@RequestBody UserToSaveDTO userDTO, HttpServletResponse response) {
		UserDTO result = null;
		result = service.saveUser(userDTO);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return result;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserDTO updateUser(@RequestBody UserDTO userDTO) {
		UserDTO result = null;
		result = service.updateUser(userDTO);
		return result;
	}

}
