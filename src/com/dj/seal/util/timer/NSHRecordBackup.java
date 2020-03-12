package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.BackupNSHRecord;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class NSHRecordBackup {
	static Logger logger = LogManager
			.getLogger(NSHRecordBackup.class.getName());

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		InetAddress ia = null;
		String localIP = DJPropertyUtil.getPropertyValue("localIP").trim();// 设置的ip
		try {
			ia = ia.getLocalHost();
			String localip = ia.getHostAddress();// 当前服务器ip
			if (localip.equals(localIP)) {
				logger.info("本机ip是：" + localip + ",执行备份超过2天单据定时任务！");
				SchedulerFactory schedFact = new StdSchedulerFactory();
				Scheduler sched = schedFact.getScheduler();
				String logJobCron = DJPropertyUtil
						.getPropertyValue("_backup_cron");// 定时执行
				JobDetail jobDetail = new JobDetail("tn1", "tn2",
						BackupNSHRecord.class);//要执行任务
				CronTrigger trigger = new CronTrigger("tn3", "tn4");//定时对象
				trigger.setCronExpression(logJobCron);//设置定时时间
				sched.scheduleJob(jobDetail, trigger);//放入时刻表
				//sched.start();//启动定时器开始计时(c3p0使用)
			} else {
				logger.info("本机ip是：" + localip + ",不执行备份超过2天单据定时任务！");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
