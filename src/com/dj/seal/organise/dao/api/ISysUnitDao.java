package com.dj.seal.organise.dao.api;

import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ISysUnitDao
 * @description 单位DAO接口
 * @author oyxy
 * @since 2009-11-3
 * @version 1.0
 * 
 */
public interface ISysUnitDao {
	/**
	 * 增加单位
	 * 
	 * @param sysUnit
	 * @throws DAOException
	 */
	public void addSysUnit(SysUnit sysUnit) throws DAOException;

	/**
	 * 修改单位的方法
	 * 
	 * @param sysUnit
	 * @param old_name
	 * @throws DAOException
	 */
	public void upDateSysUnit(SysUnit sysUnit,String old_name) throws DAOException;
	/**
	 * 授权单位信息
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String LicenseUnit() throws Exception;
	/**
	 * 查询单位信息
	 * 
	 * @return
	 * @throws DAOException
	 */
	public SysUnit showSysUnit() throws DAOException;

	/**
	 * 删除单位
	 * 
	 * @throws DAOException
	 */
	public void deleteSysUnit() throws DAOException;

}
