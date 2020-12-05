package com.oblivion.watchdogs.rabbit.mq.interfaces;

import java.util.Map;

/**
 * 
 * @author Samuel D.
 *
 */
public interface RabbitMQSender extends AutoCloseable {

	/**
	 * 
	 */
	public void initialize() throws Exception;

	/**
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> call(String message) throws Exception;

}