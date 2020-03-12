package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.OrigunJob;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class TimerUtil {
	static Logger logger = LogManager.getLogger(TimerUtil.class.getName());
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		InetAddress ia=null;
		String localIP=DJPropertyUtil.getPropertyValue("localIP").trim();
		try {
			ia=ia.getLocalHost();
			String localip=ia.getHostAddress();
			if(localip.equals(localIP)){
				logger.info("本机ip是："+localip+",执行组织机构同步数据定时任务！");
					SchedulerFactory schedFact = new StdSchedulerFactory();
					Scheduler sched = schedFact.getScheduler();
					String origunJobCron = DJPropertyUtil.getPropertyValue("origun_refresh_cron");//定时表达式
					JobDetail jobDetail = new JobDetail("td1", "td2", OrigunJob.class);//要执行任务
					CronTrigger trigger = new CronTrigger("td3","td4");
					trigger.setCronExpression(origunJobCron);//定时表达式触发器
					sched.scheduleJob(jobDetail, trigger);//放入工作时间表
					//sched.start();//启动定时器开始计时(c3p0使用)
			}else{
				logger.info("本机ip是："+localip+",不执行组织机构同步数据定时任务！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
