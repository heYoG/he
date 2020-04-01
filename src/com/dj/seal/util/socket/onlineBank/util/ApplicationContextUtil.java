package com.dj.seal.util.socket.onlineBank.util;

import com.dj.seal.util.spring.MyApplicationContextUtil;

public class ApplicationContextUtil {
	/**
	 *  获取bean工具方法
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		Object bean = MyApplicationContextUtil.getContext().getBean(name);
		return bean;
	}
}
