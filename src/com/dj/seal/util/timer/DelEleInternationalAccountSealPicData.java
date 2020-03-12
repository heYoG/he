package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.DelEleInternationalAccountSealPicDataJob;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class DelEleInternationalAccountSealPicData {
	static Logger logger=LogManager.getLogger(DelEleInternationalAccountSealPicData.class.getName());
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String setIp=DJPropertyUtil.getPropertyValue("localIP").trim();//设置ip
		String cronExpre=DJPropertyUtil.getPropertyValue("eleInternationalSealPic_cron2").trim();//定时表达式
		 InetAddress ia=null;
		 String localIp=null;
		try {
			ia=ia.getLocalHost();
			localIp=ia.getHostAddress();
			if(setIp.equals(localIp)){
				logger.info("本机ip:"+localIp+",执行删除对账系统历史表数据定时任务!");
				SchedulerFactory sf=new StdSchedulerFactory();
				Scheduler scheduler = sf.getScheduler();
				JobDetail jobDetail = new JobDetail("delSealPicData1","delSealPicData1",DelEleInternationalAccountSealPicDataJob.class);
				CronTrigger cronTrigger = new CronTrigger("delSealPicData3","delSealPicData4");
				cronTrigger.setCronExpression(cronExpre);
				scheduler.scheduleJob(jobDetail, cronTrigger);
			}else{
				logger.info("本机ip:"+localIp+",不执行删除对账系统历史表数据定时任务!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行删除对账系统历史表数据定时任务异常!");
		}
		
		
	}
	
}
