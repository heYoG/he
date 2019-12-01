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
		Session session = getOpenedSession();
		Transaction transaction = session.beginTransaction();
		String sql="insert into "+TableManager.USERTABLE+" values(null,?,?,?,?,2,?,1)";//默认为普通用户、正常启用
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
		session.close();
		return i;
	}

	@Override
	public int delUser(T t,int userID) {//删除用户,仅改变状态，否则需要考虑外键关联的解除
		Session session = getOpenedSession();
		Transaction transaction = session.beginTransaction();
		String sql="update "+TableManager.USERTABLE+" set status=0 where id=?";
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		sqlQuery.setInteger(0, userID);
		sqlQuery.addEntity(t.getClass());
		int i = sqlQuery.executeUpdate();
		transaction.commit();
		session.close();
		return i;
	}

	
	@Override
	public List<UserVo> selectUser(T t,String userNo,int flag) {//仅查用户表信息，未获取部门及权限信息
		Session session = getOpenedSession();
		List<UserVo> list=new ArrayList<UserVo>();
		String sql="";
		if(flag==0)
			sql="select*from "+TableManager.USERTABLE+" where userNo=? and status=1";//防Sql注入
		else
			sql="select*from "+TableManager.USERTABLE+" where userNo=?";//查询所有记录
		SQLQuery query = session.createSQLQuery(sql);
		query.setString(0, userNo);
		query.addEntity(t.getClass());
		list = query.list();
		return  list;
	}

	@Override
	public List<UserVo> pageSelectUser(int currentPage, int pageSize, int countSize, UserVo entity, Object... obj) {
		Session session = getOpenedSession();
//		session.clear();//无效、、、
		List<UserVo> list=new ArrayList<UserVo>();
		String sql="select*from "+TableManager.USERTABLE+" where status=1";//sql
//		String sql2="from "+entity.getClass().getSimpleName()+" where status=1";//hql
//		System.out.println("getUserInfoSql:"+sql);
		SQLQuery query = session.createSQLQuery(sql);//sql
//		Query query2 = session.createQuery(sql2);
//		System.out.println(query2);
		query.setFirstResult((currentPage-1)*pageSize);
		query.setMaxResults(pageSize);
		query.addEntity(entity.getClass());
		list=query.list();
//		query2.list();
//		session.clear();//清除缓存
		session.close();
		return list;
	}

	@Override
	public int getCount(String userNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(UserVo user) {
		Session session = getOpenedSession();
		Transaction transaction = session.beginTransaction();
		String sql="update "+TableManager.USERTABLE+" set pwd=?,age=?,userName=? where userNo='"+user.getUserNo()+"'";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setString(0, user.getPwd());
		sqlQuery.setInteger(1, user.getAge());
		sqlQuery.setString(2, user.getUserName());
		sqlQuery.addEntity(user.getClass());
		int i = sqlQuery.executeUpdate();
		transaction.commit();
		session.close();
//		System.out.println("修改返回值:"+i);
		return i;
	}

}
