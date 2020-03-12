package com.dj.seal.origunJob.job;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.hotel.service.impl.NSHRecordServiceImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class BackupNSHRecord implements Job {

	Logger logger = LogManager.getLogger(BackupNSHRecord.class.getName());

	private static Object getBean(String name) {
		Object obj = MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("-----开始执行备份单据-----");
		NSHRecordServiceImpl nshRs = (NSHRecordServiceImpl) getBean("nshRecordService");
		long startTime = System.currentTimeMillis();
		if (nshRs.backupRecords() > 0) {
			logger.info("------开始执行删除已备份单据------");
			int a = nshRs.delRecords();
			long endTime = System.currentTimeMillis();
			if (a > 0)
				logger.info("备份单据正常结束,总耗时:" + (endTime - startTime));
			else {
				logger.info("备份单据未正常结束,请确定当前用户有对数据删除的权限");
			}
		} else {
			logger.info("------备份单据结束,没有可备份数据------");
		}

	}

}
