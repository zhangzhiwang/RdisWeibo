package com.asiainfo.weibo;

import static com.asiainfo.weibo.util.PreconditionUtil.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.weibo.util.JedisUtil;

import redis.clients.jedis.Jedis;
import sun.security.ssl.HandshakeMessage;

import static com.asiainfo.weibo.consts.CommonConsts.*;

/**
 * 发布微博
 *
 * @author zhangzhiwang
 * @date 2018年7月18日 上午7:13:20
 */
public class PostWeibo {
	private static final Jedis JEDIS = JedisUtil.getJedis();
	
	/**
	 * 发布微博
	 * 
	 * @param content
	 * @author zhangzhiwang
	 * @date 2018年7月18日 上午7:29:18
	 */
	public static void postWeibo(String content) {
		checkArguments(StringUtils.isNotBlank(content), "微博内容不能为空！");
		
		String userId = JEDIS.get(LOGIN_USER);
		checkArguments(StringUtils.isNotBlank(userId), "用户尚未登陆！");
		
		Long postId = JEDIS.incr(GLOBLE_POSTID);
		
		Map<String, String> map = new HashMap<>();
		map.put("content", content);
		map.put("postUserId", userId);
		map.put("createTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		JEDIS.hmset(POST_POSTID + postId, map);
		
		System.out.println("发布成功！");
	}
	
	public static void main(String[] args) {
		postWeibo("test content1");
	}
}
