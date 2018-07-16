package com.asiainfo.weibo.util;

import redis.clients.jedis.Jedis;

/**
 * Jedis工具类
 * 
 * @author zhangzhiwang
 * @date 2018年7月16日 下午7:22:35
 */
public class JedisUtil {
	private static final Jedis JEDIS = new Jedis("127.0.0.1", 6379);
	private JedisUtil() {}
	
	public static synchronized Jedis getJedis() {
		return JEDIS;
	}
}
