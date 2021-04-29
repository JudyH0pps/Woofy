package com.hackathon.woofy;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.listener.ChannelTopic;

import com.hackathon.woofy.service.RedisService;

@SpringBootTest
public class RedisTest {

	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Autowired
	RedisService redisService;
	
	@Test
	void dummyTest() {
		System.out.println("HELLO WORLD!");
	}
	
	@Test 
	void justInputData() {
		// Redis Just Works!
	    final String key = "sabarada";

	    final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

	    stringStringValueOperations.set(key, "1"); // redis set 명령어
	    final String result_1 = stringStringValueOperations.get(key); // redis get 명령어

	    System.out.println("result_1 = " + result_1);

	    stringStringValueOperations.increment(key); // redis incr 명령어
	    final String result_2 = stringStringValueOperations.get(key);

	    System.out.println("result_2 = " + result_2);
	}
	
	@Test
	void testRedisScript() {
		String script = "return redis.call('set',KEYS[1],ARGV[1])";
	    DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(script);
	    redisScript.setResultType(String.class);
	    List<String> keys = Arrays.asList("apple");
	    String result = redisTemplate.execute(redisScript, keys, "1");
	    System.out.println(result);
	}

	@Test
	void hashSetInsertScriptTest() {
		String script = "return redis.call('hset', KEYS[1], KEYS[2], ARGV[1])";
	    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script);
	    redisScript.setResultType(Long.class);
	    List<String> keys = Arrays.asList("practice", "fruit1");
	    List<String> infos = Arrays.asList("apple");
	    Long result = redisTemplate.execute(redisScript, keys, "apple");
	    System.out.println(result);
	}

	@Test
	void hashSetTimeLimitScriptTest() {
		// 핵심은 Lua Script 였다 -_-;;
		String script = "return redis.call('expiremember', KEYS[1], KEYS[2], ARGV[1], ARGV[2])";
	    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script);
	    redisScript.setResultType(Long.class);
	    List<String> keys = Arrays.asList("practice", "fruit1");
	    List<String> args = Arrays.asList("60", "s");
	    Long result = redisTemplate.execute(redisScript, keys, "60", "s");
	    System.out.println(result);
	}

	@Test
	void getHashSetItemTest() {
		// 핵심은 Lua Script 였다 -_-;;
		String script = "return redis.call('hget', KEYS[1], ARGV[1])";
	    DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(script);
	    redisScript.setResultType(String.class);
	    List<String> keys = Arrays.asList("practice");
	    String result = redisTemplate.execute(redisScript, keys, "fruit1");
	    System.out.println(result);
	}
	
	@Test
	void redisServiceTest1() {
		System.out.println(redisService.insertHashTableContent("test1", "content1", "apple"));
		System.out.println(redisService.insertHashTableContent("test1", "content2", "pizza"));
	}

	@Test
	void redisServiceTest2() {
		System.out.println(redisService.getHashSetItem("test1", "content1"));
		System.out.println(redisService.getHashSetItem("test1", "content2"));
		System.out.println(redisService.getHashSetItem("test1", "content3"));
	}

	@Test
	void redisServiceTest3() {
		// Insert Item
		System.out.println(redisService.insertHashTableContent("test2", "content1", "apple"));
		System.out.println(redisService.insertHashTableContent("test2", "content2", "pizza"));
		
		// set Time Limit to child Key
		redisService.setHashSetTimeLimit("test2", "content1", 30);
		redisService.setHashSetTimeLimit("test2", "content2", 60);
		
		System.out.println(redisService.getHashSetItem("test2", "content1"));
		System.out.println(redisService.getHashSetItem("test2", "content2"));
	}
	
	
}
