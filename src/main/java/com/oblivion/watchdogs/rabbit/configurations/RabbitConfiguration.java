package com.oblivion.watchdogs.rabbit.configurations;

import static com.oblivion.watchdogs.rabbit.constants.RabbitConstants.REFRESH_EXCHANGE;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.oblivion.watchdogs.rabbit.configurations.async.ContextBoundExecutor;
import com.oblivion.watchdogs.rabbit.configurations.async.ThreadProperties;;

/**
 * 
 * @author Samuel D.
 *
 */
@EnableRabbit
@Configuration
public class RabbitConfiguration {

	/**
	 * 
	 */
	private ThreadProperties threadProperties;

	/**
	 * 
	 * @return
	 */
	@Bean
	public FanoutExchange refreshExchange() {
		return new FanoutExchange(REFRESH_EXCHANGE, false, false);
	}

	/**
	 * 
	 * @param threadProperties
	 */
	@Autowired
	public void setThreadProperties(ThreadProperties threadProperties) {
		this.threadProperties = threadProperties;
	}

	/**
	 * 
	 * @param configurer
	 * @return
	 */
	@Bean
	@SuppressWarnings("rawtypes")
	public RabbitListenerContainerFactory rabbitListenerContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, rabbitConnectionFactory());
		factory.setConcurrentConsumers(threadProperties.getDefaultConcurrentConsumers());
		factory.setMaxConcurrentConsumers(threadProperties.getDefaultMaxConcurrentConsumers());
		factory.setTaskExecutor(rabbitListenerTaskExecutor());
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return factory;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost("localhost");
		return connectionFactory;
	}

	/**
	 * 
	 * @return
	 */
	public ThreadPoolTaskExecutor rabbitListenerTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ContextBoundExecutor();
		executor.setCorePoolSize(threadProperties.getDefaultCorePoolSize());
		executor.setMaxPoolSize(threadProperties.getDefaultMaxPoolSize());
		executor.setQueueCapacity(threadProperties.getDefaultQueueCapacity());
		executor.setThreadNamePrefix("RabbitThread -");
		executor.initialize();
		return executor;
	}

}