package com.oblivion.watchdogs.rabbit.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oblivion.watchdogs.common.annotations.LoggingAspectEnabled;
import com.oblivion.watchdogs.rabbit.services.implementations.RabbitMQServiceImpl;
import com.oblivion.watchdogs.rabbit.services.interfaces.RabbitMQService;

/**
 * 
 * @author Samuel
 *
 */
@RestController
@LoggingAspectEnabled
@RequestMapping("/rabbit")
public class RabbitMQController {

	/**
	 * Rabbit MQ service
	 */
	final RabbitMQService rabbitMQService;

	/**
	 * Constructor injection
	 * 
	 * @param rabbitMQServiceImpl
	 */
	@Autowired
	RabbitMQController(RabbitMQServiceImpl rabbitMQServiceImpl) {
		this.rabbitMQService = rabbitMQServiceImpl;
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sendMessage")
	public ResponseEntity<Map<String, String>> sendMessage(@RequestBody String message) throws Exception {
		return new ResponseEntity<>(rabbitMQService.sendMessage(message), HttpStatus.OK);
	}

}