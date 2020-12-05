package com.oblivion.watchdogs.rabbit.services.implementations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oblivion.watchdogs.common.annotations.LoggingAspectEnabled;
import com.oblivion.watchdogs.rabbit.services.interfaces.RabbitMQService;

/**
 * 
 * @author Samuel D.
 *
 */
@Service
@LoggingAspectEnabled
public class RabbitMQServiceImpl implements RabbitMQService {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private RabbitMQServiceImpl proxy;

	/**
	 * Used to set this Spring proxied class in order to leverage aspects through
	 * call stack instead of the first call only.
	 * 
	 * @param proxy
	 */
	@Autowired
	public void setProxy(RabbitMQServiceImpl proxy) {
		this.proxy = proxy;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	@Override
	public String delegateTask(String message) throws Exception {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> sendMessage(String message) throws Exception {
		return null;
	}

}