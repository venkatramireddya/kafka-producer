package com.ttech.kafka.service;

import com.ttech.kafka.model.User;

public interface UserService {

	public String saveUser(User user) throws Exception;
}
