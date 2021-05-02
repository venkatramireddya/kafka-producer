package com.ttech.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttech.kafka.model.Account;
import com.ttech.kafka.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AccountController {

	@Autowired
	AccountService accountervice;
	
	@PutMapping("/updateAccount")
	public Account updateAcoount(@RequestBody Account acct) throws Exception {
		log.info("Entering {} with Parameter :  {}", Thread.currentThread().getStackTrace()[1].getMethodName(), acct.getAccountName());
		accountervice.updateAcoount(acct);
		return null;
	}
}
