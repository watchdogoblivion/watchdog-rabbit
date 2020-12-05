package com.oblivion.watchdogs.rabbit.configurations.async;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.oblivion.watchdogs.common.model.InMemoryTransaction;

/**
 * @author Samuel D. A callable task that wraps a passed in request attribute
 *         with an in memory variation, so that the context variables will be
 *         accessible even when the main thread services finish executing.
 */
public class ContextBoundRunnable implements Runnable {

	private final Runnable task;

	private final RequestAttributes requestAttributes;

	/**
	 * @param task
	 * @param requestAttributes
	 */
	public ContextBoundRunnable(Runnable task, RequestAttributes requestAttributes) {
		this.task = task;
		this.requestAttributes = new InMemoryTransaction(requestAttributes, null);
	}

	/**
	 * Sets the custom request attribute before running the task
	 */
	@Override
	public void run() {
		RequestContextHolder.setRequestAttributes(this.requestAttributes);
		try {
			task.run();
		} finally {
			RequestContextHolder.resetRequestAttributes();
		}
	}

}