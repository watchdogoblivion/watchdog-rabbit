package com.oblivion.watchdogs.rabbit.mq.implementations;

import static com.oblivion.watchdogs.common.logger.Log.debug;
import static com.oblivion.watchdogs.rabbit.constants.GeneralConstants.staticAppInstanceCount;
import static com.oblivion.watchdogs.rabbit.constants.RabbitConstants.REFRESH_EXCHANGE;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.oblivion.watchdogs.common.annotations.LoggingAspectEnabled;
import com.oblivion.watchdogs.rabbit.mq.interfaces.RabbitMQSender;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 
 * @author Samuel D.
 *
 */
@LoggingAspectEnabled
public class RabbitMQSenderImpl implements RabbitMQSender {

	/**
	 * 
	 */
	private Connection connection;

	/**
	 * 
	 */
	private Channel channel;

	/**
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public RabbitMQSenderImpl() throws IOException, TimeoutException {
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		connection = factory.newConnection();
		channel = connection.createChannel();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param message
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public Map<String, String> call(String message) throws IOException, InterruptedException {
		final String correlationId = UUID.randomUUID().toString();
		final String PRODUCER_QUEUE = UUID.randomUUID().toString();

		channel.queueDeclare(PRODUCER_QUEUE, true, false, false, null);
		channel.queuePurge(PRODUCER_QUEUE);
		channel.basicQos(1);
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(correlationId)
				.replyTo(PRODUCER_QUEUE).build();
		channel.exchangeDeclare(REFRESH_EXCHANGE, "fanout");
		channel.basicPublish(REFRESH_EXCHANGE, "N/A", props, message.getBytes("UTF-8"));

		final Map<String, String> consumerResponses = new TreeMap<>();
		AtomicInteger tagCount = new AtomicInteger(0);
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			debug(this, "Inside deliveryCallback - correlationId: {}, deliveryCorrelationId: {}, deliveryTag: {}",
					correlationId, delivery.getProperties().getCorrelationId(),
					delivery.getEnvelope().getDeliveryTag());
			if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
				consumerResponses.put(
						"Success for delivery tag - " + String.valueOf(delivery.getEnvelope().getDeliveryTag()),
						new String(delivery.getBody(), "UTF-8"));
				tagCount.incrementAndGet();
			}
		};
		CancelCallback cancelCallback = consumerTag -> {
			debug(this, "Callback canceled - consumerTag: {}", consumerTag);
		};

		String consumerTag = channel.basicConsume(PRODUCER_QUEUE, true, deliverCallback, cancelCallback);
		consumerResponses.put("Expected tag count", String.valueOf(staticAppInstanceCount));
		consumerResponses.put("Actual tag count", String.valueOf(tagCount.get()));
		channel.basicCancel(consumerTag);
		return consumerResponses;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		connection.close();
	}

}