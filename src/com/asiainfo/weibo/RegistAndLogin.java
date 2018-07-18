package com.asiainfo.weibo;

import static com.asiainfo.weibo.util.PreconditionUtil.*;
import static com.asiainfo.weibo.consts.CommonConsts.*;
import org.apache.commons.lang.StringUtils;
import com.asiainfo.weibo.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 模拟注册和登陆
 * 
 * @author zhangzhiwang
 * @date 2018年7月16日 下午1:59:35
 */
public class RegistAndLogin {
	private static final Jedis JEDIS = JedisUtil.getJedis();

	/**
	 * 注册
	 * 
	 * @param userName
	 * @param password
	 * @param confirmPassword
	 * @author zhangzhiwang
	 * @date 2018年7月17日 下午7:53:59
	 */
	public static void regist(String userName, String password, String confirmPassword) {
		checkArguments(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword), "用户名或密码或确认密码为空");
		checkArguments(password.equals(confirmPassword), "两次输入的密码不一致！");

		// 校验用户名是否已被注册

		checkArguments(StringUtils.isBlank(JEDIS.get(USER_USERNAME + userName + USERID_SUFFIX)), "用户名已存在");

		// 获取userid
		Long userId = JEDIS.incr(GLOBLE_USERID);
		JEDIS.set(USER_USERID + userId + USERNAME_SUFFIX, userName);
		JEDIS.set(USER_USERNAME + userName + USERID_SUFFIX, userId.toString());
		JEDIS.set(USER_USERID + userId + PASSWORD_SUFFIX, password);
		JEDIS.lpush(NEW_USER_LIST, userId.toString());

		System.out.println("注册成功！");
	}

	/**
	 * 登陆
	 * 
	 * @author zhangzhiwang
	 * @date 2018年7月18日 上午6:59:12
	 */
	public static void login(String userName, String password) {
		checkArguments(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password), "用户名或密码不能为空！");

		String userId = JEDIS.get(USER_USERNAME + userName + USERID_SUFFIX);
		checkArguments(StringUtils.isNotBlank(userId), "用户名或密码错误！");

		String pwd = JEDIS.get(USER_USERID + userId + PASSWORD_SUFFIX);
		checkArguments(StringUtils.isNotBlank(pwd) && pwd.equals(password), "用户名或密码错误！");

		String result = JEDIS.set(LOGIN_USER, userId);
		if (OK.equals(result)) {
			System.out.println("登陆成功！");
		} else {
			System.out.println("登陆失败！");
		}
	}

	public static void main(String[] args) {
//		 regist("wangwu", "1234", "1234");
		login("zhangsan", "1234");
	}
}
