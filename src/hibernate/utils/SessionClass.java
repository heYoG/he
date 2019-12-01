package hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 加载hibernate配置文件
 * @author Administrator
 *
 */
public class SessionClass {
	public Session getOpenedSession() {
		Configuration cfg = new Configuration();
		Configuration configure = cfg.configure();
		SessionFactory sessionFactory = configure.buildSessionFactory();
//		sessionFactory.getCurrentSession().clear();//清除缓存
		return sessionFactory.openSession();
	}
}
