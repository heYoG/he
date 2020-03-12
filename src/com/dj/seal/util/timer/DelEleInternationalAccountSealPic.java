package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.DelEleInternationalAccountSealPicJob;
import com.dj.seal.util.properyUtil.DJPropertyUtil;


public class DelEleInternationalAccountSealPic {
	static Logger logger=LogManager.getLogger(DelEleInternationalAccountSealPic.class.getName());
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {//服务器部署即执行清理
		String setIp=DJPropertyUtil.getPropertyValue("localIP").trim();//add on 20200308
		String cronExpre=DJPropertyUtil.getPropertyValue("eleInternationalSealPic_cron").trim();//定时执行时间
		InetAddress ia=null;
		String localIp=null;//当前服务器ip
		try {
			ia=ia.getLocalHost();
			localIp=ia.getHostAddress();
			if(setIp.equals(localIp)){
			logger.info("本机ip:" + localIp + ",删除对账系统和国结系统印章图片");
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();
			JobDetail jobDetail = new JobDetail("delSealPic1", "delSealPic2",DelEleInternationalAccountSealPicJob.class);// 定时任务
			CronTrigger cronTrigger = new CronTrigger("delSealPic3","delSealPic4");// 触发器
			cronTrigger.setCronExpression(cronExpre);// 触发时间
			scheduler.scheduleJob(jobDetail, cronTrigger);// 定时触发任务
			}else{
				logger.info("本机ip:"+localIp+",不执行删除对账系统和国结系统印章图片定时任务");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
