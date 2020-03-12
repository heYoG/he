package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.ClearAccumulationJob;
import com.dj.seal.util.properyUtil.DJPropertyUtil;



public class ClearAccumulation {
	static Logger logger=LogManager.getLogger(ClearAccumulation.class.getName());
	public static void main(String[] args) {//服务器部署即执行清理
		String cronExp=DJPropertyUtil.getPropertyValue("accumulationClearCronExp").trim();
		try {
			String localIp=InetAddress.getLocalHost().getHostAddress();
//			if(localIp.equals(setIp)){
			logger.info("本机ip:" + localIp + ",清理盖章回单文件(含未盖章和盖章后文件)");
			StdSchedulerFactory sd = new StdSchedulerFactory();
			Scheduler sch = sd.getScheduler();
			JobDetail jobDetail = new JobDetail("clAcc1", "clAcc2",ClearAccumulationJob.class);
			CronTrigger trigger = new CronTrigger("clAcc3", "clAcc4");
			trigger.setCronExpression(cronExp);
			sch.scheduleJob(jobDetail, trigger);// 执行
//			}else{
//				logger.info("本机ip:"+localIp+",不清理盖章回单文件");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
