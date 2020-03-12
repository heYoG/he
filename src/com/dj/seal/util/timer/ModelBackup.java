package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.BackupModel;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class ModelBackup {
	static Logger logger = LogManager.getLogger(ModelBackup.class.getName());

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		InetAddress ia = null;
		String localIP = DJPropertyUtil.getPropertyValue("localIP").trim();
		try {
			ia = ia.getLocalHost();
			String localip = ia.getHostAddress();
			if (localIP.equals(localip)) {
				logger.info("本机ip是:" + localip + "执行备份超过3个月模板定时任务");
				SchedulerFactory schedFact = new StdSchedulerFactory();
				Scheduler sched = schedFact.getScheduler();
				String logJobCron = DJPropertyUtil
						.getPropertyValue("_backup_cron");// 定时执行
				JobDetail jobDetail = new JobDetail("tm1", "tm2",
						BackupModel.class);
				CronTrigger trigger = new CronTrigger("tm3", "tm4");
				trigger.setCronExpression(logJobCron);
				sched.scheduleJob(jobDetail, trigger);
			} else {
				logger.info("本机ip是:" + localip + "不执行备份超过3个月模板定时任务");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
