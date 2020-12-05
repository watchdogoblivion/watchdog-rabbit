package com.oblivion.watchdogs.rabbit.services.interfaces;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 
 * @author Samuel D.
 *
 */
public interface RabbitMQService {

	/**
	 * 
	 * @param message
	 * @return
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public String delegateTask(String message) throws Exception;

	/**
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> sendMessage(String message) throws Exception;

}