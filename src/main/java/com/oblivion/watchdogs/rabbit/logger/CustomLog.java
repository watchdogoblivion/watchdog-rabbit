package com.oblivion.watchdogs.rabbit.logger;

import com.oblivion.watchdogs.common.logger.Log;
import com.oblivion.watchdogs.common.utility.GenericUtility;

/**
 * 
 * @author Samuel D.
 *
 */
public class CustomLog extends Log{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getContextInfo() {
		String contextInfo = GenericUtility.getContextInfo();
		if(contextInfo != null && !contextInfo.isEmpty()) {
			contextInfo = contextInfo.substring(1);
			return "[WatchDogOblivion - ".concat(contextInfo);
		} 
		return "[WatchDogOblivion]";
	}
	
}
