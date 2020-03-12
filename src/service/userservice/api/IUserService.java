package service.userservice.api;

import java.util.List;

import util.CommenClass;
import vo.userVo.UserVo;

/**
 * 用户数据库连接层(接口)
 * @author Administrator
 *
 */
public interface IUserService<T> {
	
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
		 * @param flag   删除标识,flag=0:彻底删除,flag=1:虚拟删除,仅更改用户状态
		 * @return       删除用户返回值
		 */
		public int delUser(T t,int userID,int flag);
		
		/**
		 * 	条件查询用户信息
		 * @param t				实例类
		 * @param isLogin		是否为登录查询,isLogin=1是,其它值否
		 * @return			用户列表
		 */
		public List<UserVo> selectUser(UserVo entity,int ...isLogin);
		
		
		
		/**
		 * 分页查询用户信息
		 * @param entity      用户实体类
		 * @param cc          分页工具类实体
		 * @return			  用户信息列表
		 */
		public List<UserVo> pageSelectUser(UserVo entity,CommenClass cc);
		
		/**
		 *查询数据条数
		 * @param type      查询类型 0：查询虚拟删除用户;1:查询正常用户
		 * @return
		 */
		public long getCount(String type);
		
		/**
		 * 修改用户信息
		 * @param user	 实例对象
		 * @return
		 */
		public abstract int  updateUser(UserVo user);
		
		/**
		 * 修改用户状态
		 * @param user		实例对象
		 * @param userID	用户id	
		 * @return			修改结果,判断修改是否成功
		 */
		public int updateStatus(UserVo user,int userID);
		
		/**
		 * 待审批用户信息
		 * @param vo		实例对象
		 * @param cc	分页工具类实体
		 * @return		待审批用户
		 */
		public List<UserVo> pageApproveUser(UserVo vo,CommenClass cc);
		
		/**
		 * 待审批用户总记录
		 * @return
		 */
		public long getApproveCount();
		
		/**
		 * 修改用户状态
		 * @param user	实例对象
		 * @return
		 */
		public int updateApproveUserStatus(UserVo user);
		
}
