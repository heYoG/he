package hibernate.dao.userDao.api;

import java.util.List;

import vo.userVo.AuthorityVo;

public interface IAuthDao {
	/**
	 * 查询权限数
	 * @return 权限数
	 */
	public int getCount();
	
	/**
	 * 查询所有权限信息
	 * @param id 查询条件
	 * @return
	 */
	public AuthorityVo getAuthInfo(int id);
	
	/**
	 * 分页查询权限
	 * @param currentPage 当前页
	 * @param pageSize	   每页记录条数
	 * @param countSize	   总记录数
	 * @param entity      实体类
	 * @param obj         备用
	 * @return
	 */
	public List<AuthorityVo> pageListAuth(int currentPage,int pageSize,int countSize,AuthorityVo entity,Object ...obj);
}