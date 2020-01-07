package test.spring;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.sealImageService.impl.SealImageServiceImpl;
import util.CommenClass;
import util.MyApplicationContext;

public class TestFunction {
	@Test
	public void testSpringComponent() {
		ClassPathXmlApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringComponent springComponent1 = applicationContext1.getBean("springComponent", SpringComponent.class);
		springComponent1.execute();
	}
}
