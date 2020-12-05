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
	public static Integer staticAppInstanceCount;
	
	/**
	 * 
	 */
	@PostConstruct
	private void postConstruction() {
		staticAppInstanceCount = appInstanceCount;
	}
	
}