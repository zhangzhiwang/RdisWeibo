package com.asiainfo.weibo.util;

import static com.asiainfo.weibo.consts.CommonConsts.LOGIN_USER;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import static com.asiainfo.weibo.consts.CommonConsts.*;

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
	
	/**
	 * 判断是否登陆
	 *  
	 * @return
	 * @author zhangzhiwang
	 * @date 2018年7月18日 下午1:40:43
	 */
	public static boolean isLoggedin() {
		return StringUtils.isNotBlank(JEDIS.get(LOGIN_USER));
	}
}
