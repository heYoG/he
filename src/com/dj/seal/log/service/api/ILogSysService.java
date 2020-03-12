package com.dj.seal.log.service.api;

import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.web.SearchForm;

/**
 * @title ILogService
 * @description 日志管理service接口
 * @author yc
 * @since 2009-11-5
 * @version 1.0
 * 
 */
public interface ILogSysService {

	/**
	 * 增加系统操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	public void logAdd(String userid, String username, String ip,
			String viewMenu, String logdetail) throws GeneralException;

	/**
	 * 增加印章写入key操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	public void logAddSealWrite(String username, String ip, String opr_num,
			String key_desc, String keysn, String seal_id)
			throws GeneralException;

	/**
	 * 查询系统操作日志
	 * 
	 * @param logSys
	 * @throws DAOException
	 */
	public PageSplit logSel(int pageIndex, int pageSize, SearchForm f)
			throws GeneralException;

	/**
	 * 查询印章写入key操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	public PageSplit logSealWriteSel(int pageIndex, int pageSize, SearchForm f)
			throws GeneralException;

	/**
	 * 查询客户端盖章等操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	public PageSplit logSealOperLog(int pageIndex, int pageSize, SearchForm f);

}
