package com.asiainfo.weibo;

import com.asiainfo.weibo.util.JedisUtil;

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
 * @date 2018年7月18日 下午12:34:34
 */
public class HotSpot {
	private static final Jedis JEDIS = JedisUtil.getJedis();
	
	/**
	 * 获取或有已注册用户（除登录用户自己外）
	 *  
	 * @author zhangzhiwang
	 * @date 2018年7月18日 下午12:35:50
	 */
	public static List<String> getAllRegistUserExceptMe() {
		String userId = JEDIS.get(LOGIN_USER);
		checkArguments(StringUtils.isNotBlank(userId), "未登录");

		SortingParams sortingParameters = new SortingParams();
		sortingParameters.get(USER_USERID + "*" + USERNAME_SUFFIX);
		sortingParameters.desc();
		List<String> allRegitUserlist = JEDIS.sort(NEW_USER_LIST, sortingParameters);
		
		//获取登录用户的姓名
		String userName = JEDIS.get(USER_USERID + userId + USERNAME_SUFFIX);
		allRegitUserlist.remove(userName);
		
		return allRegitUserlist;
	}
	
	/**
	 * 关注或取消关注
	 *  
	 * @param userName
	 * @param isfollow true-关注，false-取消关注
	 * @author zhangzhiwang
	 * @date 2018年7月18日 下午2:01:33
	 */
	public static void follow(String userName, boolean isfollow) {
		checkArguments(StringUtils.isNotBlank(userName), "被关注用户名称为空");
		checkArguments(JedisUtil.isLoggedin(), "尚未登录");
		String followedUserId = JEDIS.get(USER_USERNAME + userName + USERID_SUFFIX);
		checkArguments(StringUtils.isNotBlank(followedUserId), "无被关注用户信息");
		
		String loginUserId = JEDIS.get(LOGIN_USER);
		if(isfollow) {
			JEDIS.sadd(FOLLOWING + loginUserId, followedUserId);
			JEDIS.sadd(FOLLOWERS + followedUserId, loginUserId);
			System.out.println("关注成功");
		} else {
			JEDIS.srem(FOLLOWING + loginUserId, followedUserId);
			JEDIS.srem(FOLLOWERS + followedUserId, loginUserId);
			System.out.println("取消关注成功");
		}
		
	}
	
	/**
	 * A是否关注B
	 *  
	 * @param userId A的userId
	 * @param userName B的userName
	 * @return
	 * @author zhangzhiwang
	 * @date 2018年7月18日 下午1:50:45
	 */
	public static boolean isFollowedSomeone(String userId, String userName) {
		checkArguments(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userId), "入參为空");
		
		String bUserId = JEDIS.get(USER_USERNAME + userName + USERID_SUFFIX);
		checkArguments(StringUtils.isNotBlank(bUserId), "无此被关注人的信息");
		
		return JEDIS.sismember(FOLLOWING + userId, bUserId);
	}
	
	public static void main(String[] args) {
//		List<String> allRegisterdUser = getAllRegistUserExceptMe();
//		System.out.println(allRegisterdUser);
		
//		follow("lisi", false);
		
		System.out.println(isFollowedSomeone("1", "lisi"));
	}
}
