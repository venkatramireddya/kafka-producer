package com.ttech.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.ttech.kafka.model.User;
import com.ttech.kafka.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl  implements UserService{

	
	@Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${kafka.topic}")
	String topic;
	
	String response;
	
	@Override
	public String saveUser(User user) throws Exception{
		
		ListenableFuture<SendResult<String, Object>> feature= kafkaTemplate.send(topic, user);
		feature.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
			@Override
			public void  onSuccess(SendResult<String, Object> result) {
				log.info("Sent message: with offset [" + result.getRecordMetadata().offset()+"]" );
				response = "Sent message: with offset [" + result.getRecordMetadata().offset()+"]"; 
			}
			
			@Override
			public void  onFailure(Throwable error) {
				log.error("Error Sending Message", error);
				response = "Error Sending Message" + error;
			}
		});
		return response;
	}
}
