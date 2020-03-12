package com.dj.seal.huizhilog.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.huizhilog.dao.api.IHuizhiLogDao;
import com.dj.seal.huizhilog.service.api.IHuizhiLogService;
import com.dj.seal.structure.dao.po.HuiZhiLog;

public class HuizhiLogService implements IHuizhiLogService {
	static Logger logger  = LogManager.getLogger(HuizhiLogService.class.getName());
	private IHuizhiLogDao huizhiLog_dao;
	public void setHuizhiLog_dao(IHuizhiLogDao huizhiLog_dao) {
		this.huizhiLog_dao = huizhiLog_dao;
	}

	/**
	 * 新增盖章、打印日志记录
	 * @param log 日志对象
	 */
	@Override
	public void addHuizhiLog(HuiZhiLog log) {
		//System.out.println("流水号："+log.getCaseseqid());
		huizhiLog_dao.addHuizhiLog(log);
	}

}
