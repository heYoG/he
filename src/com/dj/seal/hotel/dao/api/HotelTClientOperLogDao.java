package com.dj.seal.hotel.dao.api;

import java.util.List;

import com.dj.seal.hotel.po.HotelTClientOperLogPO;
import com.dj.seal.util.exception.DAOException;

public interface HotelTClientOperLogDao {
	
	/**
	 * 新增客户端操作日志
	 * @param clientOperLog
	 */
	public void addTClientOperLog(HotelTClientOperLogPO clientOperLog) throws DAOException;
	
	/**
	 * 通过指定ID获得客户端日志
	 * @param cid
	 * @return
	 * @throws DAOException
	 */
	public HotelTClientOperLogPO getClientOperLogById(String cid) throws DAOException;
	
	/**
	 * 获得所有的客户端日志
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<HotelTClientOperLogPO> showAllClientOperLogsBySql(String sql) throws DAOException;
	/**
	 * 得到SQL语句返回的记录数
	 * 
	 * @param sql
	 * @return
	 */
	public int showCount(String sql);
	
	/**
	 * 通过sql获得制定的操作日志
	 * @param sql
	 * @return
	 */
	public HotelTClientOperLogPO getHotelTClientOperLogPOBySql(String sql);

}
