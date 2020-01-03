package hibernate.dao.userDao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.dao.userDao.api.IAuthDao;
import hibernate.utils.SessionClass;
import vo.userVo.AuthorityVo;

public class AuthDaoImpl extends SessionClass implements IAuthDao {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AuthorityVo getAuthInfo(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthorityVo> pageListAuth(int currentPage, int pageSize, int countSize, AuthorityVo entity,
			Object... obj) {//分页查询
		List<AuthorityVo> list=new ArrayList<AuthorityVo>();
		String sql="from "+entity.getClass().getSimpleName();
//		Session session = getOpenedSession();
		Session session=SessionClass.openSession;
		Query createQuery = session.createQuery(sql);
		createQuery.setFirstResult((currentPage-1)*pageSize);
		createQuery.setMaxResults(pageSize);
		list = createQuery.list();
//		session.close();
		return list;
	}

}
