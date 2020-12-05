package com.oblivion.watchdogs.rabbit.configurations.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.oblivion.watchdogs.rabbit.exceptions.CustomAsyncExceptionHandler;

/**
 * 
 * @author Samuel D. This class is used to customize springs async
 *         configurations.
 *
 */
@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

	/**
	 * This method is used to set a custom class for handling async exceptions
	 * 
	 * @return
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CustomAsyncExceptionHandler();
	}

}