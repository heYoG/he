package com.dj.seal.sealLog.service.api;

import com.dj.seal.structure.dao.po.CZSealLog;
import com.dj.seal.structure.dao.po.ServerSealLog;

public interface ISealLogService {
	/**
	 * 新增盖章、打印日志记录
	 * @param log 日志对象
	 */
	//public void addSealLog(SealLog log);
	public void addSealLog(ServerSealLog log);
	/**
	 * 财政国库支付项目的盖章日志
	 * @param log 日志对象
	 */
	public void addSealCZLog(CZSealLog log);
	
	/**
	 * 服务端盖章日志备份
	 */
	public int backupSealLogs();
	
	/**
	 * 删除原表已备份日志
	 */
	public int delSealLogs();

}
