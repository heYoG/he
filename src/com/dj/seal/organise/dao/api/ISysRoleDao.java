package com.dj.seal.organise.dao.api;

import java.util.List;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ISysRoleDao
 * @description 角色DAO接口
 * @author oyxy
 * @since 2009-11-5
 * 
 */
public interface ISysRoleDao {

	
	/**
	 * 新增角色
	 * 
	 * @param sysRole
	 * @throws DAOException
	 */
	public void addSysRole(SysRole sysRole) throws DAOException;
	
	/**
	 * 修改角色
	 * 
	 * @param sysRole
	 * @throws DAOException
	 */
	public void updateSysRole(SysRole sysRole) throws DAOException;

	/**
	 * 根据SQL语句修改角色表
	 * 
	 * @param sql
	 * @throws DAOException
	 */
	public void updateSysRoleBySql(String sql) throws DAOException;

	/**
	 * 删除角色
	 * 
	 * @param sysRole
	 * @throws DAOException
	 */
	public void deleteRole(SysRole sysRole) throws DAOException;

	/**
	 * 判断角色是否存在
	 * @param role_name
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistRole(String role_name) throws DAOException;
	
	/**
	 * 根据角色ID获得角色信息
	 * 
	 * @param role_id
	 * @return SysRole
	 * @throws DAOException
	 */
	public SysRole showSysRoleByRole_id(Integer role_id) throws DAOException;

	/**
	 * 根据SQL语句获得角色信息
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public SysRole showSysRoleBySql(String sql) throws DAOException;

	/**
	 * 根据部门编号获得部门所有角色列表
	 * 
	 * @param dept_no
	 * @return
	 * @throws DAOException
	 */
	public List<SysRole> showSysRolesByDept_no(String dept_no)
			throws DAOException;

	/**
	 * 根据用户名获得用户担任的角色列表
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysRole> showSysRolesByUser_id(String user_id)
			throws DAOException;

	/**
	 * 返回拥有权限ID为func_id的权限的角色列表
	 * 
	 * @param func_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysRole> showSysRolesByFunc_id(Integer func_id)
			throws DAOException;

	/**
	 * 根据SQL语句获得角色列表
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SysRole> showSysRolesBySql(String sql) throws DAOException;
	
	/**
	 * 根据分页信息得到角色集合
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SysRole> showSysRolesByPageSplit(int pageIndex,int pageSize,String sql) throws DAOException;
	
	/**
	 * 根据SQL语句得到数据
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public int showCount(String sql) throws DAOException;
	
	/**
	 * 
	 * @param userId 用户姓名
	 * @return       用户角色
	 */

	public String getRoleName(String userId);
}
