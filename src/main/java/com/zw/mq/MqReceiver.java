package com.zw.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class MqReceiver implements ChannelAwareMessageListener {

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			String msg = new String(message.getBody());
			System.out.println("MqReceiver收到消息>>>>>>>>>>>>" + msg);
			try {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				System.out.println("MqReceiver>>>>>>>>>>>>消息已消费");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
				System.out.println("MqReceiver>>>>>>>>>>>>拒绝消息，要求重发");
				throw e;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
