package test.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContext_init implements ApplicationContextAware {
	private static ApplicationContext app;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		ApplicationContext_init.app=arg0;
	}
	
	public static ApplicationContext getContext() {
		return app;
	}

	public ApplicationContext_init() {
		System.out.println("创建上下文工具类成功!");
	}
}
