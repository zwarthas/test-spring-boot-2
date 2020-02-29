package com.zw.mq;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.zw.constants.AMQPConstants;

@Component
public class MqSender {

	@Resource
	private RabbitTemplate rabbitTemplate;
	
	public void send(String msg) {
		rabbitTemplate.convertAndSend(AMQPConstants.COMMON_EXCHANGE,"info.hello.user", msg);
	}
	
}
