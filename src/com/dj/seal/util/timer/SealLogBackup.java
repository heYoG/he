package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.BackupSealLog;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class SealLogBackup {
	static Logger logger = LogManager.getLogger(SealLogBackup.class.getName());

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		InetAddress ia = null;

		String localIP = DJPropertyUtil.getPropertyValue("localIP").trim();
		try {
			ia = ia.getLocalHost();
			String localip = ia.getHostAddress();
			if (localip.equals(localIP)) {
				logger.info("本机ip是：" + localip + ",执行备份超过3个月服务端盖章日志定时任务！");
				SchedulerFactory schedFact = new StdSchedulerFactory();
				Scheduler sched = schedFact.getScheduler();
				String logJobCron = DJPropertyUtil
						.getPropertyValue("_backup_cron");
				JobDetail jobDetail = new JobDetail("tl1", "tl2",
						BackupSealLog.class);
				CronTrigger trigger = new CronTrigger("tl3", "tl4");
				trigger.setCronExpression(logJobCron);
				sched.scheduleJob(jobDetail, trigger);
				//sched.start();//启动定时器开始计时(c3p0使用)
			} else {
				logger.info("本机ip是：" + localip + ",不执行备份超过3个月服务端盖章日志定时任务！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

}
