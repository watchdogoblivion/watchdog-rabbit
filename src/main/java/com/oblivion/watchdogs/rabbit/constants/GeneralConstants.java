package com.oblivion.watchdogs.rabbit.constants;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Samuel D.
 *
 */
@Component
public class GeneralConstants {

	/**
	 * 
	 */
	@Value("${app.instance.count:1}")
	private Integer appInstanceCount;
	
	/**
	 * 
	 */
	@Value("${rabbitMQ.producer.timeout:3000}")
	private Integer rabbitMQProducerTimeout;
	
	/**
	 * 
	 */
	public static Integer staticAppInstanceCount;
	
	/**
	 * 
	 */
	public static Integer staticRabbitMQProducerTimeout;
	
	/**
	 * 
	 */
	@PostConstruct
	private void postConstruction() {
		staticAppInstanceCount = appInstanceCount;
		staticRabbitMQProducerTimeout = rabbitMQProducerTimeout;
	}
	
}