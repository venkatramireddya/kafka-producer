package com.ttech.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.ttech.kafka.model.Account;
import com.ttech.kafka.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

	@Value("${kafka.accounts.topic}")
	private String accountTopic;
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	String response;
	
	public Account updateAcoount(Account acct) throws Exception {
		log.info("Entering {} with Parameter :  {}", Thread.currentThread().getStackTrace()[1].getMethodName(), acct.getAccountName());
		ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(accountTopic, acct);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
			
			@Override
			public void onSuccess(SendResult<String, Object> result) {
				log.info("Sent message: with offset [" + result.getRecordMetadata().offset()+"]" );
				response = "Sent message: with offset [" + result.getRecordMetadata().offset()+"]"; 
				
			}
			
			@Override
			public void onFailure(Throwable ex) {
				log.error("Error Sending Message", ex);
				response = "Error Sending Message" + ex;
			}
		});
		return null;
		
	}
}
