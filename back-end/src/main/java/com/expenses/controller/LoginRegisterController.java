package com.expenses.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserToSaveDTO;
import com.expenses.service.UserService;

@RestController
public class LoginRegisterController {

	@Autowired
	@Qualifier("userService")
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

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public LoggedUserDTO saveUser(@RequestBody UserToSaveDTO userDTO, HttpServletResponse response) {
		LoggedUserDTO result = null;
		result = service.saveUser(userDTO);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return result;
	}
}
