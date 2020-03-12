package com.dj.seal.system.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ITableModuleDao
 * @description 桌面模块DAO接口
 * @author oyxy
 * @since 2010-1-8
 * 
 */
public interface ITableModuleDao {

	/**
	 * 根据模块编号得到桌面模块记录
	 * 
	 * @param module_no
	 * @return
	 * @throws DAOException
	 */
	public ViewTableModule getTableModuleByNo(String module_no)
			throws DAOException;

	/**
	 * 根据SQL语句得到桌面模块记录
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public ViewTableModule getTableModuleBySql(String sql) throws DAOException;

	/**
	 * 根据SQL语句得到桌面模块记录集合
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<ViewTableModule> showModuleListBySql(String sql)
			throws DAOException;

	/**
	 * 更新桌面模块记录
	 * 
	 * @param module
	 * @throws DAOException
	 */
	public void updateModule(ViewTableModule module) throws DAOException;

}
