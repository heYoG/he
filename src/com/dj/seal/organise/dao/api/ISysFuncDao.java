package com.dj.seal.organise.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.SysFunc;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ISysFuncDao
 * @description 权限DAO接口
 * @author oyxy
 * @since 2009-11-5
 * 
 */
public interface ISysFuncDao {

	/**
	 * 新增权限
	 * 
	 * @param sysFunc
	 * @throws DAOException
	 */
	public void addSysFunc(SysFunc sysFunc) throws DAOException;

	/**
	 * 修改权限
	 * 
	 * @param sysFunc
	 * @throws DAOException
	 */
	public void updateSysFunc(SysFunc sysFunc) throws DAOException;

	/**
	 * 根据SQL语句修改角色表
	 * 
	 * @param sql
	 * @throws DAOException
	 */
	public void updateSysFuncBySql(String sql) throws DAOException;

	/**
	 * 删除权限
	 * 
	 * @param sysFunc
	 * @throws DAOException
	 */
	public void deleteSysFunc(SysFunc sysFunc) throws DAOException;

	/**
	 * 根据SQL语句删除权限
	 * 
	 * @param func_id
	 * @throws DAOException
	 */
	public void deleteSysFuncBySql(String sql) throws DAOException;

	/**
	 * 返回指定编号的权限信息
	 * 
	 * @param func_id
	 * @return
	 * @throws DAOException
	 */
	public SysFunc showSysFuncByFunc_id(Integer func_id) throws DAOException;
	
	/**
	 * 根据权限组和权限值获得权限对象
	 * @param func_value
	 * @param value_proup
	 * @return
	 * @throws DAOException
	 */
	public SysFunc showSysFuncByFunc_value(Integer func_value,String value_proup) throws DAOException;

	/**
	 * 根据SQL语句获得权限信息
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public SysFunc showSysFuncBySql(String sql) throws DAOException;

	/**
	 * 返回菜单的所有子菜单（即权限）集合
	 * 
	 * @param menu_no
	 * @return
	 * @throws DAOException
	 */
	public List<SysFunc> showSysFuncsByMenu_no(String menu_no)
			throws DAOException;

	/**
	 * 返回角色的所有权限集合
	 * 
	 * @param role_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysFunc> showSysFuncsByRole_id(Integer role_id)
			throws DAOException;

	/**
	 * 返回用户的所有权限集合
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysFunc> showSysFuncsByUser_id(String user_id)
			throws DAOException;

	/**
	 * 根据SQL语句获得权限集合
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SysFunc> showSysFuncsBySql(String sql) throws DAOException;

}
