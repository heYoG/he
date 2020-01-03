package hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 加载hibernate配置文件
 * @author Administrator
 *
 */
public class SessionClass {//使用hibernate.cfg.xml配置文件
//	public Session getOpenedSession() {
//		Configuration cfg = new Configuration();
//		Configuration configure = cfg.configure();
//		SessionFactory sessionFactory = configure.buildSessionFactory();
//		return sessionFactory.openSession();
//	}
	
	/**
	 * 	使用spring配置文件，其中已集成hibernate配置
	 */
//	 public Session getOpenedSession() {
//	    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//	    SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean(SessionFactory.class);
//	    Session openSession = sessionFactory.openSession();
//	    return openSession;
//	  }
	 
	public static Session openSession =null;
	/**
	 * 	使用静态代码块,使openSession仅执行一次,避免多次与数据库建立连接导致连接池被快速占满
	 * 	调用后不再在方法中关闭,连接释放交由配置文件进行
	 */
	static {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	    SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean(SessionFactory.class);
	    openSession = sessionFactory.openSession();
	}
}
