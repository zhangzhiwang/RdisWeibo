package com.asiainfo.weibo.util;

import java.util.Collection;
import java.util.Map;

/**
 * 参数检查工具类
 * 
 * @author zhangzhiwang
 * @date 2017年9月26日 下午5:31:31
 */
public class PreconditionUtil {
	private PreconditionUtil() {
	}

	

	public static void checkArguments(boolean expression, String errMsg) {
		if (!expression) {
			throw new RuntimeException(errMsg);
		}
	}

	public static boolean isNull(Object o) {
		return null == o;
	}

	public static <T> boolean colIsEmpty(Collection<T> col) {
		return isNull(col) || col.isEmpty();
	}

	public static <T> boolean arrayIsEmpty(T[] ts) {
		return ts == null || ts.length == 0;
	}

	@SuppressWarnings("rawtypes")
	public static boolean mapIsEmpty(Map map) {
		return map == null || map.size() == 0;
	}

}
