package com.asiainfo.weibo;

import static com.asiainfo.weibo.util.PreconditionUtil.*;
import static com.asiainfo.weibo.consts.CommonConsts.*;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.weibo.consts.CommonConsts;
import com.asiainfo.weibo.util.JedisUtil;

import redis.clients.jedis.Jedis;


/**
 * 模拟注册和登陆
 * 
 * @author zhangzhiwang
 * @date 2018年7月16日 下午1:59:35
 */
public class RegistAndLogin {
	
	public static void regist(String userName, String password, String confirmPassword) {
		checkArguments(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword), "用户名或密码或确认密码为空");
		checkArguments(password.equals(confirmPassword), "两次输入的密码不一致！");
		
		//校验改用户名是否已被注册
		Jedis jedis = JedisUtil.getJedis();
		checkArguments(StringUtils.isBlank(jedis.get(USER_USERNAME + userName + USERID_SUFFIX)), "用户名已存在");
		
		//获取userid
		Long userId = jedis.incr(GLOBLE_USERID);
		
	}
}
