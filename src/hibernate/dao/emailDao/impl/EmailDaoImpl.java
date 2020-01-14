package hibernate.dao.emailDao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import hibernate.dao.emailDao.api.IEmailDao;
import hibernate.utils.SessionClass;
import util.CommenClass;
import util.TableManager;
import vo.emailVo.EmailVo;

@Repository(value="emailDao")//指定dao层的bean值为emailDao
public class EmailDaoImpl extends SessionClass implements IEmailDao<EmailVo> {

	@Override
	public Serializable newEmail(EmailVo ev) {
		Session session = getOpenedSession();
		Transaction beginTransaction = session.beginTransaction();
		String sql="insert into "+TableManager.EMAIL_TABLE+" values(null,?,?,?,?,?,?,?,?,?)";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setString(0, ev.getSender());
		sqlQuery.setTimestamp(1, ev.getSendTime());
		sqlQuery.setString(2, ev.getAddressee());
		sqlQuery.setText(3, ev.getText());
		sqlQuery.setInteger(4, ev.getStatus());
		sqlQuery.setInteger(5, ev.getUser().getId());
		sqlQuery.setString(6, ev.getTheme());
		sqlQuery.setString(7, ev.getAccessory());
		sqlQuery.setInteger(8, ev.getType());
//		Serializable save = session.save(ev);//无法保存到外键user_id
		int saveRet = sqlQuery.executeUpdate();
		beginTransaction.commit();
		session.close();
		return saveRet;
	}

	@Override
	public int deleteEmail(EmailVo t, Serializable id) {
		Session session = getOpenedSession();
		Transaction beginTransaction = session.beginTransaction();
		session.delete(session.get(t.getClass(), id));
		beginTransaction.commit();//必须提交事务
		session.close();
		return 1;
	}

	@Override
	public int update(EmailVo t, int id) {
		Session session = getOpenedSession();
		Transaction beginTransaction = session.beginTransaction();
		String hql="update "+t.getClass().getSimpleName()+" e set e.type=3 where e.id=?";
		Query hqlQuery = session.createQuery(hql);
		hqlQuery.setInteger(0, id);
		int update = hqlQuery.executeUpdate();
		beginTransaction.commit();
		session.close();
		return update;
	}

	@Override
	public EmailVo emailInfo(EmailVo t, int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<EmailVo> emailPageList(EmailVo t, CommenClass cc,String flag) {
		Session session = getOpenedSession();
		String hql="from EmailVo where 1=1";
		if(flag.equals("0"))
			hql+=" and type="+flag;
		else if(flag.equals("1"))
			hql+=" and type="+flag;
		else if(flag.equals("2"))
			hql+=" and type="+flag;
		else
			hql+=" and type="+flag;
		Query hqlQuery = session.createQuery(hql);
		hqlQuery.setFirstResult((cc.getCurrentPage()-1)*cc.getPageSize());//起始记录
		hqlQuery.setMaxResults(cc.getPageSize());//总记录
		List<EmailVo> list = hqlQuery.list();
		session.close();
		return list;
	}

	@Override
	public long getEmailCount(String flag) {
		Session session = getOpenedSession();
		String hql="select count(e.id) from EmailVo e where 1=1";
		if(flag.equals("0"))
			hql+=" and e.type="+flag;
		else if(flag.equals("1"))
			hql+=" and e.type="+flag;
		else if(flag.equals("2"))
			hql+=" and e.type="+flag;
		else
			hql+=" and e.type="+flag;
		Query hqlQuery = session.createQuery(hql);
		long countEmail = (long) hqlQuery.uniqueResult();
		session.close();
		return countEmail;
	}

}
