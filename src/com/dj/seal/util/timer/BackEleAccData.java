package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.BackEleAccDataJob;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class BackEleAccData {
	static Logger logger=LogManager.getLogger(BackEleAccData.class.getName());
	public static void main(String[] args) {
		String setIP = DJPropertyUtil.getPropertyValue("localIP").trim();
		String cronExpre=DJPropertyUtil.getPropertyValue("eleInternationalSealPic_cron").trim();
		try {
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			if(hostAddress.equals(setIP)){
				logger.info("本机ip:" + hostAddress + ",执行备份对账系统表数据定时任务!");
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler scheduler = sf.getScheduler();
				JobDetail jobDetail = new JobDetail("bakupSealPic1","bakupSealPic1", BackEleAccDataJob.class);
				CronTrigger cronTrigger = new CronTrigger("bakupSealPic3","bakupSealPic4");
				cronTrigger.setCronExpression(cronExpre);
				scheduler.scheduleJob(jobDetail, cronTrigger);
			}else{
				logger.info("本机ip:"+hostAddress+",不执行备份对账系统表数据定时任务!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行备份对账系统表数据定时任务异常!");
		}
	}
}
