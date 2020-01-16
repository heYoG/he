package test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import aop.CommenAop;
import service.sealImageService.impl.SealImageServiceImpl;
import test.aop.PointcutClass;
import util.CommenClass;
import util.MyApplicationContext;

public class TestFunction {
	@Test
	public void testSpringComponent() {
		ClassPathXmlApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("applicationContext.xml");
		PointcutClass springComponent1 = applicationContext1.getBean("pointcut", PointcutClass.class);//获取切入点bean
		springComponent1.add();//切入点，在调用前会执行切面中的增强/通知
	}
}
