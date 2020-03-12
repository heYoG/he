package com.dj.seal.huizhilog.dao.api;

import com.dj.seal.structure.dao.po.HuiZhiLog;

public interface IHuizhiLogDao {
	/**
	 * 新增盖章、打印日志记录
	 * @param log 日志对象
	 */
	public void addHuizhiLog(HuiZhiLog log);
}