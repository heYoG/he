package com.dj.seal.origunJob.job;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.log.service.impl.FeedLogSysServiceImpl;
import com.dj.seal.log.service.impl.LogSysServiceImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class DeleteLog implements Job{
	Logger logger = LogManager.getLogger(DeleteLog.class.getName());
	
	private static Object getBean(String name){
		Object obj=MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("-----开始删除日志数据-----");
		FeedLogSysServiceImpl flog_srv = (FeedLogSysServiceImpl) getBean("feedLogSysService");  
		flog_srv.deleteFeedLog();  //删除评价日志
		LogSysServiceImpl log_srv = (LogSysServiceImpl) getBean("logSysService");
		log_srv.deleteSealSys();  //删除签章日志
	    log_srv.deleteLogSys();  //删除系统操作日志
	}

}
