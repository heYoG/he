package com.dj.seal.util.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 供普通类调用spring管理的bean
 * @author Administrator
 *
 */
public class MyApplicationContextUtil implements ApplicationContextAware {
	static Logger logger = LogManager.getLogger(MyApplicationContextUtil.class.getName());
	private static ApplicationContext context;// 声明一个静态变量保存

	@Override
	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		context = contex;
	}

	public static ApplicationContext getContext() {
		return context;
	}
}
