package com.expenses.controller;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserToAuthenticateDTO;
import com.expenses.dto.UserToSaveDTO;
import com.expenses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginRegisterController {

    @Autowired
    @Qualifier("userService")
    private UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoggedUserDTO> getUser(@RequestBody UserToAuthenticateDTO userToAuthenticateDTO,
            HttpServletResponse response) {
        return service
                .getUserByUsernameAndPassword(userToAuthenticateDTO.getUsername(), userToAuthenticateDTO.getPassword())
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<LoggedUserDTO> saveUser(@RequestBody UserToSaveDTO userDTO, HttpServletResponse response) {
        LoggedUserDTO result = null;
        result = service.saveUser(userDTO);

        return new ResponseEntity(result, HttpStatus.CREATED);
    }
}
