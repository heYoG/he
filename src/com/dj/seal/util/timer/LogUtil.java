package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.DeleteLog;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class LogUtil {
	static Logger logger = LogManager.getLogger(LogUtil.class.getName());

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		InetAddress ia=null;
		String localIP=DJPropertyUtil.getPropertyValue("localIP").trim();
		try {
			ia=ia.getLocalHost();
			String localip=ia.getHostAddress();
			if(localip.equals(localIP)){
				logger.info("本机ip是："+localip+",执行删除日志数据定时任务！");
					SchedulerFactory schedFact = new StdSchedulerFactory();
					Scheduler sched = schedFact.getScheduler();
					String logJobCron = DJPropertyUtil.getPropertyValue("log_refresh_cron");
					JobDetail jobDetail = new JobDetail("ta1", "ta2",DeleteLog.class);
					CronTrigger trigger = new CronTrigger("ta3", "ta4");
					trigger.setCronExpression(logJobCron);
					sched.scheduleJob(jobDetail, trigger);
					//sched.start();//启动定时器开始计时(c3p0使用)
			}else{
				logger.info("本机ip是："+localip+",不执行删除日志数据定时任务！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
