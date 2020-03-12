package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.CheckJob;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

/**
 * c3p0使用此类,DBCP不需要用到(ApplicationContext-config.xml执行定时任务)
 * @author WB000520
 *
 */
public class CheckJobExport{
	static Logger logger = LogManager.getLogger(CheckJobExport.class.getName());
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String localIP = DJPropertyUtil.getPropertyValue("localIP").trim();// 设置的ip
		InetAddress ia = null;
		try {
			ia = ia.getLocalHost();
			String localip = ia.getHostAddress();
			if (localip.equals(localIP)) {//指定服务器执行
				logger.info("本机ip是:" + localip + ",导出后督数据！");
				SchedulerFactory schedFact = new StdSchedulerFactory();
				Scheduler sched = schedFact.getScheduler();
				String logJobCron = DJPropertyUtil.getPropertyValue("check_cron");// 定时执行
				JobDetail jobDetail = new JobDetail("ckct1", "dckct2",CheckJob.class);
				CronTrigger trigger = new CronTrigger("ckct3", "ckct4");
				trigger.setCronExpression(logJobCron);
				sched.scheduleJob(jobDetail, trigger);
				//sched.start();//启动定时器开始计时(c3p0使用)
			} else {
				logger.info("本机ip是:" + localip + ",不导出后督数据！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
