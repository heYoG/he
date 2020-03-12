package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.AccumulationAddSealJob;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class AccumulationAddSeal {
	static Logger logger=LogManager.getLogger(AccumulationAddSeal.class.getName());
	public static void main(String[] args) {
		String setIp=DJPropertyUtil.getPropertyValue("localIP").trim();
		String cronExp=DJPropertyUtil.getPropertyValue("accumulationAddSealCronExp").trim();
		String localIp=null;
		try {
			localIp=InetAddress.getLocalHost().getHostAddress();
			if(localIp.equals(setIp)){
				logger.info("本机ip:"+localIp+",执行定时盖章公积金回单任务!");
				/*获取时刻表*/
				StdSchedulerFactory sd=new StdSchedulerFactory();
				Scheduler scheduler = sd.getScheduler();
				/*定时要执行的任务*/
				JobDetail jd=new JobDetail("clearAcc1", "clearAcc2", AccumulationAddSealJob.class);
				/*触发器*/
				CronTrigger ct=new CronTrigger("clearAcc3", "clearAcc4");
				/*触发时间*/
				ct.setCronExpression(cronExp);
				/*调用定时执行方法*/
				scheduler.scheduleJob(jd, ct);
			}else{
				logger.info("本机ip:"+localIp+",不执行定时盖章公积金回单任务!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
