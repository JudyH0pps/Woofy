package com.hackathon.woofy.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	StringRedisTemplate redisTemplate;
	
	public Long insertHashTableContent(String key, String subkey, String value) {
		String script = "return redis.call('hset', KEYS[1], KEYS[2], ARGV[1])";
	    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script);
	    redisScript.setResultType(Long.class);
	    List<String> keys = Arrays.asList(key, subkey);
	    Long result = redisTemplate.execute(redisScript, keys, value);
	    return result;
	}

	public Long setHashSetTimeLimit(String key, String subkey, int seconds) {
		String script = "return redis.call('expiremember', KEYS[1], KEYS[2], ARGV[1], ARGV[2])";
	    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script);
	    redisScript.setResultType(Long.class);
	    List<String> keys = Arrays.asList(key, subkey);
	    Long result = redisTemplate.execute(redisScript, keys, Integer.toString(seconds), "s");
	    return result;
	}

	public String getHashSetItem(String key, String subkey) {
		String script = "return redis.call('hget', KEYS[1], ARGV[1])";
	    DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(script);
	    redisScript.setResultType(String.class);
	    List<String> keys = Arrays.asList(key);
	    String result = redisTemplate.execute(redisScript, keys, subkey);
	    return result;
	}
}
