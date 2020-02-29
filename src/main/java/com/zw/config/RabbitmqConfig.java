package com.zw.config;

import javax.annotation.Resource;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zw.constants.AMQPConstants;
import com.zw.mq.MqReceiver;

@Configuration
public class RabbitmqConfig {

	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.port}")
	private String port;
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;
	
	@Value("${spring.rabbitmq.username}")
	private String username;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.publisher-confirms}")
	private boolean publisherConfirms;
	
	@Resource
	private MqReceiver mqReceiver;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory=new CachingConnectionFactory();
		connectionFactory.setAddresses(host+":"+port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setPublisherConfirms(publisherConfirms);
		
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template=new RabbitTemplate(connectionFactory());
		template.setMandatory(true);
		template.setConfirmCallback(confirmCallback());
		template.setReturnCallback(returnCallback());
		return template;
	}
	
	@Bean
	public RabbitTemplate.ConfirmCallback confirmCallback(){
		return new RabbitTemplate.ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				if (ack) {
					System.out.println("生产者发送收到确认");
				} else {
					System.out.println("生产者收到确认失败，" + cause);
				}
			}
		};
	}
	
	@Bean
	public RabbitTemplate.ReturnCallback returnCallback(){
		return new RabbitTemplate.ReturnCallback() {
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
				System.out.println("无法路由的消息，需要考虑另外处理。");
				System.out.println("Returned replyText：" + replyText);
				System.out.println("Returned exchange：" + exchange);
				System.out.println("Returned routingKey：" + routingKey);
				String msgJson = new String(message.getBody());
				System.out.println("Returned Message：" + msgJson);
			}
		};
	}
	
	@Bean
	public Queue userQueue() {
		return new Queue(AMQPConstants.USER_QUEUE);
	}
	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(AMQPConstants.COMMON_EXCHANGE);
	}
	
	@Bean
	public Binding topicBinding() {
		return BindingBuilder.bind(userQueue() ).to(topicExchange()).with("info.*.user");
	}
	
	@Bean
	public SimpleMessageListenerContainer listenerContainer() {
		SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory());
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setMessageListener(mqReceiver);
		container.setQueues(userQueue());
		return container;
	}
	
}
