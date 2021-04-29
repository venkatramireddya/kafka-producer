package com.ttech.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttech.kafka.model.User;
import com.ttech.kafka.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {
	
	@Autowired
	UserService UserService;
	
	@PostMapping("/user")
	public String saveUser(@RequestBody User user) throws Exception {
		log.debug("Entering {} with Parameter :  {}", Thread.currentThread().getStackTrace()[1].getMethodName(), user);
		return UserService.saveUser(user);
	}

}
