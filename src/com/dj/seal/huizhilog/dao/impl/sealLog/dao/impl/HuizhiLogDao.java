package com.dj.seal.huizhilog.dao.impl.sealLog.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.huizhilog.dao.impl.sealLog.dao.api.IHuizhiLogDao;
import com.dj.seal.structure.dao.po.HuiZhiLog;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.table.HuiZhiLogUtil;

public class HuizhiLogDao extends BaseDAOJDBC implements IHuizhiLogDao{
	static Logger logger = LogManager.getLogger(HuizhiLogDao.class.getName());
	/**
	 * 新增盖章、打印日志记录
	 * @param log 日志对象
	 */
	@Override
	public void addHuizhiLog(HuiZhiLog log){
		//System.out.println("流水号："+log.getCaseseqid());
		String sql=ConstructSql.composeInsertSql(log, new HuiZhiLogUtil());
		add(sql);
	}
}
