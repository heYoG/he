package com.dj.seal.origunJob.job;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.hotel.service.impl.NSHRecordServiceImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class DelServerSealLog_Job implements Job {
	Logger logger = LogManager.getLogger(DelServerSealLog_Job.class.getName());

	private static Object getBean(String name) {
		Object obj = MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		NSHRecordServiceImpl nshRs = (NSHRecordServiceImpl) getBean("nshRecordService");
		logger.info("------开始执行删除服务端盖章日志历史表数据------");
		if (nshRs.delServerSealLog() > 1) {
			logger.info("删除服务端盖章日志历史表数据成功！");
		} else {
			logger.info("没有可删除的服务端盖章日志历史表数据！");
		}
	}

}
