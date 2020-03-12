package com.dj.seal.organise.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ISysUserDao
 * @description 用户DAO接口
 * @author oyxy
 * @since 2009-11-4
 * @version 1.0
 * 
 */
public interface ISysUserDao {

	/**
	 * 根据用户ID获得用户信息
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public SysUser showSysUserByUser_id(String user_id) throws DAOException;

	/**
	 * 根据用户ID获得用户信息
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public SysUser showSysUserByUser_id_JH(String user_id) throws DAOException;

	/**
	 * 根据用户ID获得用户信息
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysUser> showSysUserByUserList(String user_id)
			throws DAOException;

	/**
	 * 根据证书dn获得用户信息
	 * 
	 * @param key_dn
	 *            证书DN值
	 * @return
	 * @throws DAOException
	 */
	public SysUser getUserBy_keyDN(String key_dn) throws DAOException;

	/**
	 * 根据证书sn获得用户信息
	 * 
	 * @param key_sn
	 *            证书sN值
	 * @return
	 * @throws DAOException
	 */
	public SysUser getUserBy_keySN(String key_sn) throws DAOException;

	/**
	 * 更新用户的部门编号
	 * 
	 * @param dept_no
	 * @param new_no
	 * @throws DAOException
	 */
	public void updateSysUser(String dept_no, String new_no)
			throws DAOException;

	/**
	 * 更新用户
	 * 
	 * @param sysUser
	 * @throws DAOException
	 */
	public void updateSysUser(SysUser sysUser) throws DAOException;

	/**
	 * 更新用户
	 * 
	 * @param sysUser
	 * @throws DAOException
	 */
	public void updateSysUser(String user_id_old, String user_name_old,
			SysUser sysUser) throws DAOException;

	/**
	 * 根据用户ID删除用户记录
	 * 
	 * @param user_id
	 * @throws DAOException
	 */
	public void deleteSysUser(String user_id) throws DAOException;

	/**
	 * 根据部门编号集合获得该部门下所有用户的集合
	 * 
	 * @param dept_no
	 * @return
	 * @throws DAOException
	 */
	public List<SysUser> showSysUsersByDept_no(String isJouner, String dept_no)
			throws DAOException;

	/**
	 * 返回角色的所有用户列表集合
	 * 
	 * @param role_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysUser> showSysUserByRole_id(Integer role_id)
			throws DAOException;

	/**
	 * 根据SQL语句获得用户集合
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SysUser> showSysUsersBySql(String sql) throws DAOException;

	/**
	 * 根据分页信息得到用户集合
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SysUser> showSysUserByPageSplit(int pageIndex, int pageSize,
			String sql) throws DAOException;

	/**
	 * 根据用户id查询角色号
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public int getUserRoleNum(String user_id) throws DAOException;

	/**
	 * 根据部门编号得到部门名称
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public String getDeptName(String dept_no) throws DAOException;

	/**
	 * 根据SQL语句得到数据
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public int showCount(String sql) throws DAOException;

	/**
	 * 增加用户
	 * 
	 * @param UserForm
	 * @return
	 * @throws GeneralException
	 */
	public void AddUser(SysUser user) throws DAOException;

	/**
	 * 判断用户名是否存在
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistUser(String user_id) throws DAOException;

	/**
	 * 判断应用系统是否存在
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistServer(String sys_id) throws DAOException;

	/**
	 * 判断用户名密码是否正确
	 * 
	 * @param user_id
	 * @param pwd
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistorPwd(String user_id, String pwd) throws DAOException;

	/**
	 * 注销记录
	 * 
	 * @param user_id
	 * @throws DAOException
	 */
	public boolean zhuxiaoList(String sql) throws DAOException;

	/**
	 * 根据用户ID取消注销记录
	 * 
	 * @param user_id
	 * @throws DAOException
	 */
	public void quzhuxiaoList(String user_id) throws DAOException;

	/**
	 * 根据用户id查询用户记录进行编辑
	 * 
	 * @param user_id
	 * @throws GeneralException
	 */
	public SysUser editSysUser(String user_id) throws DAOException;

	/**
	 * 更新用户记录
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void updateSysUser(SysUser sysuser, String usertype2)
			throws DAOException;

	/**
	 * 删除用户拥有的部门记录
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void deleteUserDetp(String sql) throws DAOException;

	/**
	 * 增加用户拥有的部门记录
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void AddSysUserDEPT(String sql) throws DAOException;

	/**
	 * 重置密码
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void updPWDByID(String sql) throws DAOException;
	
	/**
	 * 审批用户
	 * @param id    用户唯一ID
	 * @param flag  标志,同意or拒绝
	 * @return	          返回结果>0成功，否则失败
	 */
	public int approveUserByUniId(String id,String flag,String state,String current_user);
	
	/**
	 * 查询密码有效时长辅助接口
	 * @param sql 查询语句
	 * @return
	 */
	public String getPasswordLastTime(String sql); 

}