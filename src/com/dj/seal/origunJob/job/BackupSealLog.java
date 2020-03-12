package com.dj.seal.origunJob.job;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.sealLog.service.impl.SealLogService;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class BackupSealLog implements Job {
	Logger logger = LogManager.getLogger(BackupSealLog.class.getName());

	private static Object getBean(String name) {
		Object obj = MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("-----开始执行备份服务端盖章日志-----");
		SealLogService slog_srv = (SealLogService) getBean("SealLogService");
		long startTime = System.currentTimeMillis();
		if (slog_srv.backupSealLogs() > 0) {
			logger.info("-----开始执行删除已备份服务端盖章日志-----");
			if (slog_srv.delSealLogs() > 0) {
				long endTime = System.currentTimeMillis();
				logger.info("-备份服务端盖章日志正常结束,总耗时:" + (endTime - startTime));
			} else {
				logger.info("备份服务端盖章日志未正常结束,请确定当前用户有对数据删除的权限");
			}
		} else {
			logger.info("-----备份服务端盖章日志结束,没有可备份数据-----");
		}
	}

}
