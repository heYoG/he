package com.dj.seal.log.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.LogSealWrite;
import com.dj.seal.structure.dao.po.LogSys;
import com.dj.seal.util.exception.DAOException;

/**
 * @title ILogDao
 * @description 日志管理DAO接口
 * @author yc
 * @since 2009-11-5
 * @version 1.0
 *
 */
public interface ILogSysDao {
	/**
	 * 增加系统操作日志
	 * @param logSeal
	 * @throws DAOException
	 */
	public void addLogSys(LogSys logSys)throws DAOException;
	/**
	 * 增加印章写入key操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	public void logAddSealWrite(LogSealWrite logsealWrite) throws DAOException;
	
	/**
	 * 查询系统操作日志
	 * @param logSeal
	 * @throws DAOException
	 */
	public List<LogSys> logSel(String sql)throws DAOException;
	
}