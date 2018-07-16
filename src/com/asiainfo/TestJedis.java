package com.asiainfo;

import redis.clients.jedis.Jedis;

public class TestJedis {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("test", "test123");
		String result = jedis.get("test");
		System.out.println(result);
		
		jedis.lpush("test2", "a");
		jedis.rpush("test2", "b");
		System.out.println(jedis.lrange("test2", 0, -1));
		
	}
}
