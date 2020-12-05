package com.oblivion.watchdogs.rabbit.configurations.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Samuel D.
 *
 */
@Data
@Component
public class ThreadProperties {

	/**
	 * 
	 */
	@Value("${default.thread.corePoolSize:2}")
	private int defaultCorePoolSize;

	/**
	 * 
	 */
	@Value("${default.thread.maxPoolSize:10}")
	private int defaultMaxPoolSize;

	/**
	 * 
	 */
	@Value("${default.thread.queueCapacity:20}")
	private int defaultQueueCapacity;

	/**
	 * 
	 */
	@Value("${default.factory.concurrentConsumers:1}")
	private int defaultConcurrentConsumers;

	/**
	 * 
	 */
	@Value("${default.thread.maxConcurrentConsumers:10}")
	private int defaultMaxConcurrentConsumers;

}