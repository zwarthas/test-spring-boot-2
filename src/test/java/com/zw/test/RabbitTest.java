package com.zw.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zw.App;
import com.zw.mq.MqSender;

@SpringBootTest(classes= {App.class})
@RunWith(SpringRunner.class)
public class RabbitTest {

	@Resource
	MqSender mqSender;
	
	@Test
	public void testMq() {
		mqSender.send("消息内容");
	}
}
