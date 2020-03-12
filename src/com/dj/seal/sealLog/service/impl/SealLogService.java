package com.dj.seal.sealLog.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.sealLog.dao.api.ISealLogDao;
import com.dj.seal.sealLog.service.api.ISealLogService;
import com.dj.seal.structure.dao.po.CZSealLog;
import com.dj.seal.structure.dao.po.ServerSealLog;

public class SealLogService implements ISealLogService {
	static Logger logger  = LogManager.getLogger(SealLogService.class.getName());
	private ISealLogDao sealLog_dao;
	public void setSealLog_dao(ISealLogDao sealLog_dao) {
		this.sealLog_dao = sealLog_dao;
	}

	/**
	 * 新增盖章、打印日志记录
	 * @param log 日志对象
	 */
	@Override
	public void addSealLog(ServerSealLog log) {
		//System.out.println("流水号："+log.getCaseseqid());
		sealLog_dao.addSealLog(log);
	}

	@Override
	public void addSealCZLog(CZSealLog log) {		
		sealLog_dao.addSealCZLog(log);
	}
	
	@Override
	public int backupSealLogs() {
		return sealLog_dao.backupSealLogs();
	}

	@Override
	public int delSealLogs() {
		return sealLog_dao.delSealLogs();
	}

}
