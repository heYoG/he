package com.dj.seal.organise.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.SysUserRole;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ISysUserRoleDao
 * @description 用户角色DAO接口
 * @author oyxy
 * @since 2009-11-16
 * 
 */
public interface ISysUserRoleDao {

	/**
	 * 根据角色ID删除用户角色表记录
	 * 
	 * @param role_id
	 * @throws DAOException
	 */
	public void deleteUserRoleByRole_id(Integer role_id) throws DAOException;


	/**
	 * 根据角色ID获得所有用户角色集合
	 * 
	 * @param role_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysUserRole> showSysUserRolesByRole_id(Integer role_id)
			throws DAOException;
	/**
	 * 根据角色ID获得所有用户角色集合
	 * 
	 * @param role_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysUserRole> showSysUserRolesByRole_id2(String sql)
			throws DAOException;
	/**
	 * 根据用户名获得所有用户角色集合
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysUserRole> showSysUserRolesByUser_id(String user_id)
			throws DAOException;

	/**
	 * 为用户角色表添加记录
	 * 
	 * @param user_id
	 * @param role_id
	 * @param user_role_status
	 * @throws DAOException
	 */
	public void addUserRole(String user_id, Integer role_id,
			String user_role_status) throws DAOException;

	/**
	 * 为用户角色表更新记录
	 * 
	 * @param user_id
	 * @param role_id
	 * @param user_role_status
	 * @throws DAOException
	 */
	public void updateUserRole(String user_id, Integer role_id,
			String user_role_status) throws DAOException;

	/**
	 * 根据用户名称获得用户角色信息
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public SysUserRole showSysUserRoleByUser_id(String user_id)
			throws DAOException;

	/**
	 * 根据用户ID删除用户角色表记录(yc)
	 * 
	 * @param role_id
	 * @throws DAOException
	 */
	public void deleteUserRoleByUser_id(String user_id) throws DAOException;

}
