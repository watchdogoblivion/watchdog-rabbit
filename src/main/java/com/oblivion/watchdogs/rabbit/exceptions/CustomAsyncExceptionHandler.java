package com.oblivion.watchdogs.rabbit.exceptions;

import static com.oblivion.watchdogs.common.logger.Log.prettyError;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oblivion.watchdogs.common.aop.LoggingAspect;

/**
 * @author Samuel D. This class is used to handle exceptions thrown by async
 *         methods
 */
@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	/**
	 * 
	 */
	private LoggingAspect loggingAspect;

	/**
	 * 
	 */
	@Autowired
	public void setLogginAspect(LoggingAspect loggingAspect) {
		this.loggingAspect = loggingAspect;
	}

	/**
	 * This method pretty prints uncaught exceptions for async methods.
	 *
	 * @param throwable
	 * @param method
	 * @param obj
	 */
	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
		String args = loggingAspect.getMethodArgs(loggingAspect.getFilteredArgs(objects));
		String message = "An exception occurred during a service transaction with reequest body: {}, {}";
		prettyError(method.getDeclaringClass(), method.toString(), message, throwable, args);
	}

}