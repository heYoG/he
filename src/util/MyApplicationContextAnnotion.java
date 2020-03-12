package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static Logger log=LogManager.getLogger(MyApplicationContextAnnotion.class.getName());

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		MyApplicationContextAnnotion.app=arg0;
	}
	
	public static ApplicationContext getContext() {
		return app;
	}

	public MyApplicationContextAnnotion() {
		log.info("获取上下文工具类初始化成功!");
	}
}
