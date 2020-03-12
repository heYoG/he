package com.dj.seal.hotel.service.api;

import java.util.List;

import com.dj.seal.hotel.form.HotelTClientOperLogsForm;
import com.dj.seal.hotel.po.HotelTClientOperLogPO;
import com.dj.seal.util.exception.DAOException;

public interface IHotelClientOperLogService {
	
	/**
	 * 新增客户端操作日志
	 * @param clientOperLog
	 * @return 1成功 0失败
	 */
	public int addClientOperLog(HotelTClientOperLogPO clientOperLog) throws DAOException;
	
	public int addClientOperLogWithForm(HotelTClientOperLogsForm clientOperLogForm) throws Exception;
	
	public List<HotelTClientOperLogPO> showAllHotelTClientOperLog() throws Exception;
	
	public HotelTClientOperLogPO getClientOperLogByCid(String cid) throws DAOException;
	
	public List<HotelTClientOperLogPO> showHotelClientOperLogByCid(String cid) throws DAOException;
}
