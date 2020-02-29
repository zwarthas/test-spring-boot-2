package com.zw.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.zw.App;

import redis.clients.jedis.Jedis;

@SpringBootTest(classes= {App.class})
@RunWith(SpringRunner.class)
public class RedisTest {

	@Resource
	private RedisTemplate<String,String> redisTemplate;
	
	@Resource
	private Jedis jedis;
	
	@Test
	public void testRedis() {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set("name", "ahu");
		String v = opsForValue.get("name");
		System.out.println(v);
	}
	
	@Test
	public void testSimpleRedisStarter() {
		jedis.set("testJedis", "success");
		System.out.println(jedis.get("testJedis"));
	}
	
}
