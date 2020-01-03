package hibernate.dao.userDao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.jdbc.PreparedStatement;

import hibernate.dao.deptDao.impl.DeptDaoImpl;
import hibernate.dao.userDao.api.IUserDao;
import hibernate.utils.SessionClass;
import util.CommenClass;
import util.TableManager;
import vo.deptVo.DeptVo;
import vo.userVo.UserVo;

@SuppressWarnings(value= {"all"})
public class UserDaoImplHib<T> extends SessionClass implements IUserDao<T> {
	DeptVo dv=new DeptVo();
	@Override
	public int createTable() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addUser(UserVo entity) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		Transaction transaction = session.beginTransaction();
		String sql="insert into "+TableManager.USERTABLE+" values(null,?,?,?,?,2,?,2)";//默认为普通用户、待审批
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setString(0, entity.getUserNo());
		sqlQuery.setString(1, entity.getPwd());
		sqlQuery.setInteger(2, entity.getAge());
		sqlQuery.setString(3, entity.getUserName());
		sqlQuery.setInteger(4, entity.getDept().getDeptID());
		sqlQuery.addEntity(entity.getClass());
		int i = sqlQuery.executeUpdate();
//		Serializable save = session.save(entity);//HQL
		session.flush();
		transaction.commit();
//		session.close();
		return i;
	}

	@Override
	public int delUser(T t,int userID,int flag) {//删除用户,仅改变状态，否则需要考虑外键关联的解除
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		Transaction transaction = session.beginTransaction();
		String sql="";
		if(flag==1)//虚拟删除
			sql="update "+TableManager.USERTABLE+" set status='0' where id=?";
		else//彻底删除(级联删除)
			sql="delete from "+TableManager.USERTABLE+" where id=?";
		System.out.println("delSql:"+sql);
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		sqlQuery.setInteger(0, userID);
		sqlQuery.addEntity(t.getClass());
		int i = sqlQuery.executeUpdate();
		transaction.commit();
//		session.close();
		return i;
	}

	
	@Override
	public List<UserVo> selectUser(UserVo uv,int...isLogin) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		List<UserVo> list=new ArrayList<UserVo>();
		String sql="select * from "+TableManager.USERTABLE+" where userNo=?";
		if(isLogin.length>0)
			sql+=" and status='1'";//登录查询必须是正常用户
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setString(0, uv.getUserNo());
		sqlQuery.addEntity(uv.getClass());
		list = sqlQuery.list();
//		if(session!=null) {
//			session.close();
//			System.out.println("session已关闭!");
//		}
		return  list;
	}

	@Override
	public List<UserVo> pageSelectUser(UserVo uv,CommenClass cc) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		List<UserVo> list1=new ArrayList<UserVo>();
		List<UserVo> list2 = selectUser(uv);
		uv =list2.get(0);
		String sql="select t1.*,t2.authVal,t2.authName,t3.deptName from "+TableManager.USERTABLE+" t1,"+TableManager.AUTHORITY+" t2,"+TableManager.DEPT+ " t3 "
				+ "where t1.AuthorityID=t2.id and t1.dept_id=t3.deptID";
		if(cc.getType().equals("0")) 
			sql+=" and t1.status='0'";//查询注销用户
		else
			sql+=" and t1.status='1'";//查询正常用户
		SQLQuery query = session.createSQLQuery(sql);
		query.setFirstResult((cc.getCurrentPage()-1)*cc.getPageSize());
		query.setMaxResults(cc.getPageSize());
		query.addEntity(uv.getClass());
		list1=query.list();
//		session.close();
		return list1;
	}

	@Override
	public long getCount(String type) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		String hql="select count(id) from UserVo where 1=1";
		if(type.equals("0")) //非管理员只能查看正常用户
			hql+=" and status='0'";
		else//查询正常用户
			hql+=" and status='1'";
		Query query = session.createQuery(hql);
		long count = (long) query.uniqueResult();
//		session.close();
		return count;
	}

	@Override
	public int updateUser(UserVo user) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		Transaction transaction = session.beginTransaction();
		String sql="update "+TableManager.USERTABLE+" set pwd=?,age=?,userName=?,status=? where userNo='"+user.getUserNo()+"'";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setString(0, user.getPwd());
		sqlQuery.setInteger(1, user.getAge());
		sqlQuery.setString(2, user.getUserName());
		sqlQuery.setString(3, "2");
		sqlQuery.addEntity(user.getClass());
		int i = sqlQuery.executeUpdate();
		transaction.commit();
//		session.close();
		return i;
	}

	@Override
	public int updateStatus(UserVo user,int userID) {
//		Session session = getOpenedSession();//获取hibernate session
		Session session=SessionClass.openSession;
		Transaction transaction = session.beginTransaction();
		String sql="update "+TableManager.USERTABLE+" set status='1' where id=?";//sql
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setInteger(0, userID);
		sqlQuery.addEntity(user.getClass());//添加实例
		int i = sqlQuery.executeUpdate();//执行
		transaction.commit();//提交事务
//		session.close();//关闭session
		return i;
	}

	@Override
	public List<UserVo> pageApproveUser(UserVo vo,CommenClass cc) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		List<UserVo> list1=new ArrayList<UserVo>();
		String sql="select t1.*,t2.authVal,t2.authName,t3.deptName from "+TableManager.USERTABLE+" t1,"+TableManager.AUTHORITY+" t2,"+TableManager.DEPT+ " t3 "
				+ "where t1.AuthorityID=t2.id and t1.dept_id=t3.deptID and t1.status in('2','3')";
		SQLQuery query = session.createSQLQuery(sql);
		query.setFirstResult((cc.getCurrentPage()-1)*cc.getPageSize());
		query.setMaxResults(cc.getPageSize());
		query.addEntity(vo.getClass());
		list1=query.list();
//		session.close();
		return list1;
	}

	@Override
	public long getApproveCount() {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		String hql="select count(id) from UserVo where status='2' or status='3'";//待审批用户
		Query query = session.createQuery(hql);
		long count = (long) query.uniqueResult();
//		session.close();
		return count;
	}

	@Override
	public int updateApproveUserStatus(UserVo user) {
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		String hql="update "+user.getClass().getSimpleName()+" set status='3' where id="+user.getId();
		Transaction transaction = session.beginTransaction();
		Query hqlQuery = session.createQuery(hql);
		int i = hqlQuery.executeUpdate();
		transaction.commit();
//		session.close();
		return i;
	}

}
