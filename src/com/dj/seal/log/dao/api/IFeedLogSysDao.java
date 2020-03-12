package com.dj.seal.log.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.FeedLogSys;
import com.dj.seal.util.exception.DAOException;

/**
 * @title IFeedLogDao
 * @description 评价日志管理DAO接口
 * @author zw
 * @since 2016-03-07
 * @version 1.0
 *
 */
public interface IFeedLogSysDao {
	/**
	 * 增加评价操作日志
	 * @param logSeal
	 * @throws DAOException
	 */
	public void addFeedLogSys(FeedLogSys FeedLogSys)throws DAOException;
	
	/**
	 * 查询系统操作日志
	 * @param logSeal
	 * @throws DAOException
	 */
	public List<FeedLogSys> logSel(String sql)throws DAOException;
}