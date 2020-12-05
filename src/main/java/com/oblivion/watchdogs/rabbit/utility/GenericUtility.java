package com.oblivion.watchdogs.rabbit.utility;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 
 * @author Samuel D.
 *
 */
public class GenericUtility {

	/**
	 * 
	 * @param <I>
	 * @param instance
	 * @param instanceInterface
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static <I> I getProxy(Object instance, Class<I> instanceInterface)
			throws InstantiationException, IllegalAccessException {
		ProxyFactory factory = new ProxyFactory(instance);
		factory.addInterface(instanceInterface);
		return (I) factory.getProxy();
	}

	/**
	 * 
	 * @param <I>
	 * @param <A>
	 * @param instance
	 * @param instanceInterface
	 * @param aspect
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <I, A> I getProxy(Object instance, Class<I> instanceInterface, Class<A> aspect)
			throws InstantiationException, IllegalAccessException {
		AspectJProxyFactory factory = new AspectJProxyFactory(instance);
		factory.addInterface(instanceInterface);
		factory.addAspect(aspect);
		return factory.getProxy();
	}

}