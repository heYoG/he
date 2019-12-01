package hibernate.dao.userDao.api;

import java.io.Serializable;
import java.util.List;

import vo.userVo.AuthorityVo;
import vo.userVo.UserVo;
/**
 * 用户数据库连接层(接口)
 * @author Administrator
 *
 */
	public interface  IUserDao<T> {
	/**
	 * 创建表
	 * @return 
	 */
	public abstract int createTable();
	
	/**
	 * 添加用户
	 * @param entity 用户对象
	 * @return
	 */
	public abstract int addUser(UserVo entity);
	
	/**
	 * 删除用户
	 * @param userID 用户id
	 * @return       删除用户返回值
	 */
	public int delUser(T t,int userID);
	
	/**
	 * 一般查询用户信息
	 * @param userNo 用户名
	 * @return
	 */
	public List<UserVo> selectUser(T t,String userNo,int flag);
	
	
	
	/**
	 * 分页查询用户信息
	 * @param currentPage 当前页
	 * @param pageSize	   每页记录条数
	 * @param countSize	   总记录数
	 * @param entity      实体类
	 * @param obj         备用
	 * @return
	 */
	public List<UserVo> pageSelectUser(int currentPage,int pageSize,int countSize,UserVo entity,Object ...obj);
	
	/**
	 *查询数据条数
	 * @param userNo 用户名
	 * @return
	 */
	public int getCount(String userNo);
	
	/**
	 * 修改用户信息
	 * @param userNo  用户号
	 * @return
	 */
	public abstract int  updateUser(UserVo user);
	
}