package com.oblivion.watchdogs.rabbit.configurations.async;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Future;

import static com.oblivion.watchdogs.common.logger.Log.trace;

/**
 * @author Samuel D. This class is used to add the request context to the tasks that it
 *          submits to the thread pool
 */
public class ContextBoundExecutor extends ThreadPoolTaskExecutor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8614833704716253296L;

	/**
	 * Submits a runnable task to the thread pool with a request context if it is
	 * retrievable.
	 *
	 * @param task
	 * @return
	 */
	@NonNull
	@Override
	public Future<?> submit(@NonNull Runnable task) {
		try {
			return super.submit(new ContextBoundRunnable(task, RequestContextHolder.currentRequestAttributes()));
		} catch (Exception e) {
			trace(this, "An exception occurred while trying to apply the request attributes: {}", e.getMessage());
			return super.submit(task);
		}
	}

	/**
	 * Submits a runnable task to the thread pool with a request context if it is
	 * retrievable.
	 *
	 * @param task
	 * @return
	 */
	@NonNull
	@Override
	public ListenableFuture<?> submitListenable(@NonNull Runnable task) {
		try {
			return super.submitListenable(
					new ContextBoundRunnable(task, RequestContextHolder.currentRequestAttributes()));
		} catch (Exception e) {
			trace(this, "An exception occurred while trying to apply the request attributes: {}", e.getMessage());
			return super.submitListenable(task);
		}
	}

}