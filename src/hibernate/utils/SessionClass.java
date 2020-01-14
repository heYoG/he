package hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.MyApplicationContext;

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
	 * 	①ClassPathXmlApplicationContext会每次初始化配置文件，创建新的连接，导致mysql连接爆满,不合适在实际应用使用
	 * 	②通过工具类不会每次创建新的连接，适用
	 */
	 public Session getOpenedSession() {
//	    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");//会再次实例化配置文件,影响系统性能
		SessionFactory sessionFactory = MyApplicationContext.getContext().getBean("sessionFactory",SessionFactory.class);//仅启动实例化一次配置文件
//	    SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean(SessionFactory.class);
	    Session openSession = sessionFactory.openSession();
	    return openSession;
	  }
	 
	public static Session openSession =null;
	/**
	 * 	使用静态代码块,使openSession仅执行一次,避免多次与数据库建立连接导致连接池被快速占满
	 * 	调用后不再在方法中关闭,连接释放交由配置文件进行
	 * 	单例，限制并发量
	 */
//	static {
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//	    SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean(SessionFactory.class);
//	    openSession = sessionFactory.openSession();
//	}
}
