package com.oblivion.watchdogs.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Samuel D.
 *
 */
@SpringBootApplication(scanBasePackages = { "com.oblivion.watchdogs.common", "com.oblivion.watchdogs.rabbit" })
public class RabbitApplication {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RabbitApplication.class, args);
	}

}
