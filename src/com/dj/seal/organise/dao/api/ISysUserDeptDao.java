package com.dj.seal.organise.dao.api;

import com.dj.seal.structure.dao.po.SysUserDept;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ISysUserDeptDao
 * @description 用户部门DAO接口
 * @author oyxy
 * @since 2009-11-11
 * @version 1.0
 * 
 */
public interface ISysUserDeptDao {

	/**
	 * 增加用户部门记录
	 * 
	 * @param userDept
	 * @throws DAOException
	 */
	public void addSysUserDept(SysUserDept userDept) throws DAOException;

	/**
	 * 根据用户名和部门编号添加用户部门记录
	 * 
	 * @param user_id
	 * @param dept_no
	 * @throws DAOException
	 */
	public void addSysUserDept(String user_id, String dept_no)
			throws DAOException;

	/**
	 * 根据部门编号删除用户部门关系表
	 * 
	 * @param dept_no
	 * @throws DAOException
	 */
	public void deleteSysUserDept(String dept_no) throws DAOException;
	
	/**
	 * 根据用户名称删除用户部门关系表
	 * @param user_id
	 * @throws DAOException
	 */
	public void deleteSysUserDeptByUser_id(String user_id) throws DAOException;

	/**
	 * 根据SQL语句更新用户部门记录
	 * 
	 * @param sql
	 * @throws DAOException
	 */
	public void updateSysUserDept(String sql) throws DAOException;

	/**
	 * 更新用户部门表的部门编号
	 * 
	 * @param dept_no
	 * @param new_no
	 * @throws DAOException
	 */
	public void updateSysUserDept(String dept_no, String new_no)
			throws DAOException;

}
