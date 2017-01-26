package com.expenses.controller;

import com.expenses.dto.UserDTO;
import com.expenses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService service;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@PathVariable String username, HttpServletResponse response) {
        return service.getUser(username).map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String username) {
        service.deleteUser(username);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserDTO userDTO) {
        service.updateUser(userDTO);
    }

}
