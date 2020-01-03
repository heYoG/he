package hibernate.dao.deptDao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import hibernate.dao.deptDao.api.IDeptDao;
import hibernate.utils.SessionClass;
import util.CommenClass;

@SuppressWarnings(value= {"all"})
public class DeptDaoImpl<T> extends SessionClass implements IDeptDao<T> {
	
	@Override
	public List<T> getDeptInfos(T t,CommenClass cc) {
//		Session session=getOpenedSession();
		Session session=SessionClass.openSession;
		Query getDepts = session.createQuery("from " + t.getClass().getSimpleName());
		/*设置分页查询参数*/
		getDepts.setFirstResult((cc.getCurrentPage() - 1) * cc.getPageSize());
		getDepts.setMaxResults(cc.getPageSize());
		List<T> list = getDepts.list();
//		session.close();
		return list;
	}

	@Override
	public List<T> getDeptInfos() {
//		Session session=getOpenedSession();
		Session session=SessionClass.openSession;
		Query getDepts = session.createQuery("from DeptVo");
		List<T> list = getDepts.list();
//		session.close();
		return list;
	}
	
	@Override
	public List<T> getDeptInfo(T t,String obj) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		Query sqlQuery = session.createQuery("from "+t.getClass().getSimpleName()+" where deptName='"+obj+"'");
		List<T> list = sqlQuery.list();
//		session.close();
		return list;
	}

	@Override
	public Serializable addDept(T t) {
//		Session session=getOpenedSession();
		Session session=SessionClass.openSession;
		Transaction transaction = session.beginTransaction();
		Serializable save = session.save(t);//直接调用save接口
		transaction.commit();
//		session.close();
		return save;
	}

	@Override
	public void delDept(T t,Serializable id) {
//		Session session=getOpenedSession();
		Session session=SessionClass.openSession;
		Transaction transaction = session.beginTransaction();
		/*先根据条件获取要删除对象*/
		session.delete(session.get(t.getClass(), id));//id必须是主键
		transaction.commit();
//		session.close();
	}

	@Override
	public long getCount(T t) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		String sql="select count(deptID) from "+t.getClass().getSimpleName();
		
		Query query = session.createQuery(sql);
		long count = (long) query.uniqueResult();
//		session.close();//关闭session
		return count;
	}

}
