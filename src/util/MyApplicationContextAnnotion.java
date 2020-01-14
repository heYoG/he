package util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * @function	注解方式实现ApplicationContextAware接口
 * @author Administrator
 *
 */
@Component
public class MyApplicationContextAnnotion implements ApplicationContextAware {
	private static ApplicationContext app;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		MyApplicationContextAnnotion.app=arg0;
	}
	
	public static ApplicationContext getContext() {
		return app;
	}

	public MyApplicationContextAnnotion() {
		System.out.println("获取上下文工具类初始化成功!");
	}
}
