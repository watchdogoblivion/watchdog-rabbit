package com.oblivion.watchdogs.rabbit.mq.implementations;

import static com.oblivion.watchdogs.common.logger.Log.debug;
import static com.oblivion.watchdogs.common.logger.Log.prettyError;
import static com.oblivion.watchdogs.rabbit.constants.RabbitConstants.REFRESH_EXCHANGE;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.oblivion.watchdogs.common.annotations.LoggingAspectEnabled;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * 
 * @author Samuel D.
 *
 */
@Component
@LoggingAspectEnabled
@RabbitListener(bindings = @QueueBinding(value = @Queue(durable = "true", exclusive = "false", autoDelete = "false"), exchange = @Exchange(name = REFRESH_EXCHANGE, type = "fanout", durable = "false")))
public class RabbitMQReceiverImpl {

	/**
	 * 
	 * @param byteMessage
	 * @param channel
	 * @param correlationId
	 * @param tag
	 * @param replyTo
	 * @throws IOException
	 * @throws TimeoutException
	 */
	@RabbitHandler
	public void receive(byte[] byteMessage, Channel channel, @Header(AmqpHeaders.CORRELATION_ID) String correlationId,
			@Header(AmqpHeaders.CONSUMER_TAG) String consumerTag, @Header(AmqpHeaders.DELIVERY_TAG) long tag,
			@Header(AmqpHeaders.REPLY_TO) String replyTo, @Header(AmqpHeaders.CONSUMER_QUEUE) String consumerName)
			throws IOException, TimeoutException {
		AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder().correlationId(correlationId).build();
		String response = "";
		String message = "";
		try {
			message = new String(byteMessage, "UTF-8");
			debug(this, "Message is {}", message);
			response += "Message received successfully";
		} catch (RuntimeException e) {
			String args = String.format(
					"Message: %s, Channel: %s, CorrelationId: %s, Tag: %s, ReplyTo: %s, Queue name: %s", message,
					channel.toString(), correlationId, tag, replyTo, consumerName);
			String errorMessage = "An exception occurred while receieving a RabbitMQ message with arguments: {}, {}";
			prettyError(this, "receive", errorMessage, e, args);
			response = String.format(
					"An error occurred while processing a message. Consumer Tag: %s, Consumer Queue: %s, Connection: %s,%s",
					consumerTag, consumerName, channel.getConnection(), channel.getChannelNumber());
		} finally {
			channel.basicPublish("", replyTo, replyProps, response.getBytes("UTF-8"));
			channel.basicAck(tag, false);
		}
	}

}