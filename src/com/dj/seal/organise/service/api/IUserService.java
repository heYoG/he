package com.dj.seal.organise.service.api;

import java.util.List;

import com.dj.seal.organise.web.form.LoginForm;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.personInfo.web.form.PersonForm;
import com.dj.seal.personInfo.web.form.PsdForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;

public interface IUserService {

	public String piLiangImportUser(String gonghao,String xingming,String yingyeting ,String juese,String create_userid) throws Exception;
	
	/**
	 * 增加用户
	 * 
	 * @param UserForm
	 * @return
	 * @throws GeneralException
	 */
	public String AddUser(UserForm userForm,String userid) throws GeneralException;
	/**
	 * 用户登录
	 * 
	 * @param loginForm
	 * @return
	 * @throws GeneralException
	 */
	public SysUser userLogin(LoginForm loginForm, String ip)throws GeneralException;
	/**
	 * 查询用户数量
	 * 
	 * @param loginForm
	 * @return
	 * @throws GeneralException
	 */
	public String selUserNum()throws Exception;
	/**
	 * 判断用户是否注销
	 * 
	 * @param loginForm
	 * @return
	 * @throws GeneralException
	 */
	public int isActive(LoginForm loginForm)throws GeneralException;
	/**
	 * 更新用户的权限值
	 * 
	 * @param user_id
	 * @throws DAOException
	 */
	public void updateFuncsValue(String user_id) throws GeneralException;
	/**
	 * 判断用户是否系统内置用户
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public boolean isSuperManager(String user_id) throws GeneralException;
	/**
	 * 根据分页信息和用户得到用户列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showTempsByDeptManageUser(int pageIndex, int pageSize,
			String user_id) throws GeneralException;
	
	/**
	 * 根据用户id获取用户列表（只包含此用户）
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showUserByUserId(int pageIndex, int pageSize,
			String user_id) throws GeneralException;

	/**
	 * 根据分页信息和用户类型得到用户列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showTempsByDeptManage(int pageIndex, int pageSize,UserForm userForm,int status) throws GeneralException;
	/**
	 * 根据分页信息和部门id得到用户列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showTempsByDeptTree(int pageIndex, int pageSize,String dept_no,int status) throws GeneralException;
	


	/**
	 * 根据角色id得到用户信息
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public List<SysUser> showUsersByRole(Integer role_id) throws Exception;
	/**
	 * 根据证书号获得用户名
	 * 
	 * @param key_sn
	 * @return
	 * @throws GeneralException
	 */
	public String getUserIdBy_key(String key_sn) throws GeneralException;
	/**
	 * 根据证书DN判断是否注册过用户
	 * @param key_dn 证书dn值
	 * @return
	 * @throws GeneralException
	 */
	public String getUserIdBy_keyDN(String key_dn) throws GeneralException ;
	/**
	 * 根据证书DN获得用户
	 * @param key_dn 证书dn值
	 * @return SysUser对象
	 * @throws GeneralException
	 */
	public SysUser getUserBy_keyDN(String key_dn);
	/**
	 * 根据证书sN获得用户
	 * @param key_sn 证书sn值
	 * @return SysUser对象
	 * @throws GeneralException
	 */
	public SysUser getUserBy_keySN(String key_sn);
	/**
	 * 根据用户id删除用户记录
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public void deleteSysUser(String user_id) throws GeneralException;
	/**
	 * 根据用户id注销记录
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public String zhuxiaoList(String user_id) throws GeneralException;
	/**
	 * 根据用户id取消注销记录
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public void quzhuxiaoList(String user_id) throws GeneralException;
	
	/**
	 * 根据用户id查询用户角色记录
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public String serSysUserRole(String user_id) throws GeneralException;
	/**
	 * 根据用户ID查询用户记录进行编辑
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public SysUser editSysUser(String user_id) throws GeneralException;
	/**
	 * 根据用户id查询用户记录进行编辑
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public SysUser editSysUserJH(String user_id) throws GeneralException;
	/**
	 * 查询属于用户的单位名称
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	 public String serSysUnitUtil(String deptno) throws GeneralException;
	 /**
	 * 更新用户记录
	 * 
	 * @param form
	 * @throws GeneralException
	 
	public void updateSysUser(UserForm form,String usertype2,String is_key) throws GeneralException;  */
	/**
	 * 更新用户记录
	 * 
	 * @param form
	 * @param dept_no
	 * @param user_remark2
	 * @throws GeneralException
	 */
	public void updateSysUser(UserForm form,String dept_no,String user_remark2) throws GeneralException;
	/**
	 * 更新用户记录
	 * 
	 * @param form
	 * @param dept_no
	 * @param user_remark2
	 * @throws GeneralException
	 */
	public void updateSysUser(UserForm form) throws GeneralException;
	/**
	 * 激活用户记录
	 * 
	 * @param form
	 * @param dept_no
	 * @param user_remark2
	 * @throws GeneralException
	 */
	public void jihuoSysUser(UserForm form,String user_id_old,String user_name_old,String dept_no,String user_remark2)throws GeneralException; 
	/**
	 * 更新用户用户的部门记录
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void updateSysUserDEPT(String dept_no,String dept_name) throws GeneralException;

	
	/**
	 * 更新用户密码
	 * 
	 * @param psdForm
	 * @throws GeneralException
	 */
	public void updateSysUserPsd(PsdForm psdForm) throws GeneralException;
	
	/**
	 * 更新用户资料
	 * 
	 * @param personForm
	 * @throws GeneralException
	 */
	public void updateSysPerson(PersonForm personForm) throws GeneralException;   

	/**
	 * 根据用户ID获得用户信息
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public SysUser showSysUserByUser_id(String user_id);
	/**
	 * 根据用户名获得所有部门编号
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */

	public String getAllDept(String user_id) throws GeneralException;
	/** 根据SQL语句获得用户集合
	 * 
	 * @param sql
	 * @return
	 * @throws GeneralException
	 */
	public List<SysUser> showSysUsersBySql(String sql) throws GeneralException;
	/** 将用户输入的密码明文与数据库中的密码密文对比
	 * 
	 * @param sql
	 * @return
	 * @throws GeneralException
	 */
	public String checkPsd(String user_id,String oldPsd) throws GeneralException;
	
	/**
	 * 审批用户
	 * @param id    用户唯一ID
	 * @param flag  标志,同意or拒绝
	 * @return	          返回结果>0成功，否则失败
	 */
	public int approveUserByUniId(String id,String flag,String state,String current_user);
	
	/**
	 *判断是否第一次登录(未使用)
	 *@param 用户id
	 * @return
	 */
	public String loginFirstTime(String userId);
	
	/**
	 * 密码是否已过期(定时30天更新,未使用)
	 * @param userId 用户ID
	 * @return
	 */
	public String isEffectivePassword(String userId);

	/**
	 * 查询密码有效时长
	 * @return
	 */
	public String getPasswordLastTime(); 

}
