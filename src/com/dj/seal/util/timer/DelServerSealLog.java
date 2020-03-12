package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.DelServerSealLog_Job;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class DelServerSealLog {
	static Logger logger=LogManager.getLogger(DelServerSealLog.class.getName());
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String localIP = DJPropertyUtil.getPropertyValue("localIP").trim();// 设置的ip
		String cronValue = DJPropertyUtil.getPropertyValue("delLog_cron");//定时执行时间
		InetAddress ia=null;
		try {
			InetAddress localHost = ia.getLocalHost();
			String localip = localHost.getHostAddress();//主机ip
			if(localIP.equals(localip)){
				logger.info("本机ip是:"+localip+",执行删除盖章日志定时任务");
				StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
				Scheduler scheduler = schedulerFactory.getScheduler();
				JobDetail detail = new JobDetail("dlt1","dlt2",DelServerSealLog_Job.class);
				CronTrigger trigger = new CronTrigger("dlt3", "dlt4");
				trigger.setCronExpression(cronValue);
				scheduler.scheduleJob(detail, trigger);	
				//scheduler.start();//启动定时器开始计时(c3p0使用)
			}else{
				logger.info("本机ip是:"+localip+",不执行删除盖章日志定时任务");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
