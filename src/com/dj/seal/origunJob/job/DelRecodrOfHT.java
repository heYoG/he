package com.dj.seal.origunJob.job;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.hotel.service.impl.NSHRecordServiceImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class DelRecodrOfHT implements Job {
	Logger logger = LogManager.getLogger(BackupNSHRecord.class.getName());

	private static Object getBean(String name) {
		Object obj = MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		NSHRecordServiceImpl nshRs = (NSHRecordServiceImpl) getBean("nshRecordService");
		logger.info("------开始执行删除>1年的单据历史表数据------");
		long startTime = System.currentTimeMillis();
		if (nshRs.delRecordOfHistory() > 0) {
			long endTime = System.currentTimeMillis();
			logger.info("删除单据历史表数据成功！！！");
			logger.info("耗时:" + (endTime - startTime));
		} else {
			logger.info("删除单据历史表数据结束,没有可删除数据");
		}

	}

}
