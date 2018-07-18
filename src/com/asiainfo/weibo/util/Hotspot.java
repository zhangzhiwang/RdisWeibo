package com.asiainfo.weibo.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import static com.asiainfo.weibo.util.PreconditionUtil.*;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import static com.asiainfo.weibo.consts.CommonConsts.*;

/**
 * 热点
 *
 * @author zhangzhiwang
 * @date 2018年7月18日 上午8:21:18
 */
public class Hotspot {
	private static final Jedis JEDIS = JedisUtil.getJedis();
	
	public static void getHotspot() {
		String userId = JEDIS.get(LOGIN_USER);
		checkArguments(StringUtils.isNotBlank(userId), "未登陆");
		SortingParams sortingParameters = new SortingParams();
		sortingParameters.get(USER_USERID + "*" + USERNAME_SUFFIX);
		sortingParameters.desc();
		List<String> newUserIdList = JEDIS.sort(NEW_USER_LIST, sortingParameters);
		System.out.println(newUserIdList);
	}
	
	public static void main(String[] args) {
		getHotspot();
	}
}
