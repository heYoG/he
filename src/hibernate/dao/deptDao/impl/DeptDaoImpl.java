package hibernate.dao.deptDao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import hibernate.dao.deptDao.api.IDeptDao;

public class DeptDaoImpl<T> implements IDeptDao<T> {
	public Session getOpenedSession() {
		Configuration cfg = new Configuration();
		Configuration configure = cfg.configure();
		SessionFactory sessionFactory = configure.buildSessionFactory();
		return sessionFactory.openSession();
	}

	@Override
	public List<T> getDeptInfos(T t) {
		Session session=this.getOpenedSession();
		Query getDepts = session.createQuery("from DeptVo");
		List<T> list = getDepts.list();
		session.close();
		return list;
	}

	@Override
	public List<T> getDeptInfo(T t,String obj) {
		Session session = this.getOpenedSession();
		Query sqlQuery = session.createQuery("from DeptVo where deptName='"+obj+"'");
		List<T> list = sqlQuery.list();
		return list;
	}

	@Override
	public Serializable addDept(T t) {
		Session session=this.getOpenedSession();
		Transaction transaction = session.beginTransaction();
		Serializable save = session.save(t);//直接调用save接口
		transaction.commit();
		session.close();
		return save;
	}

	@Override
	public void delDept(T t,Serializable id) {
		Session session=this.getOpenedSession();
		Transaction transaction = session.beginTransaction();
		/*先根据条件获取要删除对象*/
		session.delete(session.get(t.getClass(), id));//id必须是主键
		transaction.commit();
		session.close();
	}

}
