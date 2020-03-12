package com.dj.seal.log.service.api;

import com.dj.seal.structure.dao.po.FeedLogSys;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.web.SearchForm;

/**
 * @title IFeedLogService
 * @description 评价日志管理service接口
 * @author zw
 * @since 2016-03-07
 * @version 1.0
 * 
 */
public interface IFeedLogSysService {

	/**
	 * 增加系统操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	public void logAdd(FeedLogSys flogsys) throws GeneralException;

	/**
	 * 查询系统操作日志
	 * 
	 * @param logSys
	 * @throws DAOException
	 */
	public PageSplit logSel(int pageIndex, int pageSize, SearchForm f) throws GeneralException;
}
