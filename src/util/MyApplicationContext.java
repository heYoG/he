package util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 	配置文件方式获取spring管理的bean,需要在容器中加载当前类
 * @author Administrator
 *
 */
public class MyApplicationContext implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		MyApplicationContext.context=context;
	}

	public static ApplicationContext getContext() {
		return context;
	}
	
}
