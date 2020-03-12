package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.DeleteLogFile;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class LogFileUtil {
	static Logger logger = LogManager.getLogger(LogFileUtil.class.getName());

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		InetAddress ia=null;
		String localIP=DJPropertyUtil.getPropertyValue("localIP").trim();
		try {
			ia=ia.getLocalHost();
			String localip=ia.getHostAddress();
			if(localip.equals(localIP)){
				logger.info("本机ip是："+localip+",执行删除日志文件定时任务！");
					SchedulerFactory schedFact = new StdSchedulerFactory();
					Scheduler sched = schedFact.getScheduler();
					String pdfJobCron = DJPropertyUtil
							.getPropertyValue("logFile_refresh_cron");
					JobDetail jobDetail = new JobDetail("te1", "te2",
							DeleteLogFile.class);
					CronTrigger trigger = new CronTrigger("te3", "te4");
					trigger.setCronExpression(pdfJobCron);
					sched.scheduleJob(jobDetail, trigger);
					//sched.start();//启动定时器开始计时(c3p0使用)
			}else{
				logger.info("本机ip是："+localip+",不执行删除日志文件定时任务！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
