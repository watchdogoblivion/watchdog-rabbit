package com.oblivion.watchdogs.rabbit.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oblivion.watchdogs.common.annotations.LoggingAspectEnabled;

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
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sendMessage")
	public ResponseEntity<Map<String, String>> sendMessage(@RequestBody String message) throws Exception {
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}