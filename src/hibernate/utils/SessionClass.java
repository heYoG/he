package hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 加载hibernate配置文件
 * @author Administrator
 *
 */
public class SessionClass {
//	public Session getOpenedSession() {
//		Configuration cfg = new Configuration();
//		Configuration configure = cfg.configure();
//		SessionFactory sessionFactory = configure.buildSessionFactory();
//		return sessionFactory.openSession();
//	}
	
	 public Session getOpenedSession() {
	    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	    SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean(SessionFactory.class);
	    return sessionFactory.openSession();
	  }
}
