package com.dj.seal.util.timer;

import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.dj.seal.origunJob.job.DelRecodrOfHT;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class DelRecordOfHistory {
	static Logger logger = LogManager.getLogger(DelRecordOfHistory.class
			.getName());

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		/*获取设置的ip和定时时间 */
		String localIP = DJPropertyUtil.getPropertyValue("localIP").trim();
		String logJobCron = DJPropertyUtil.getPropertyValue("_backup_cron");
		InetAddress ia = null;
		try {
			ia = ia.getLocalHost();
			String localip = ia.getHostAddress();//获取当前服务器ip ②
			if (localip.equals(localIP)) {
				logger.info("本机ip是:" + localip + ",删除>1年的单据历史表数据！");
				SchedulerFactory schedFact = new StdSchedulerFactory();
				Scheduler sched = schedFact.getScheduler();
				JobDetail jobDetail = new JobDetail("rt1", "rt2",DelRecodrOfHT.class);//定时任务
				CronTrigger trigger = new CronTrigger("rt3", "rt4");//定时触发器对象
				trigger.setCronExpression(logJobCron);//定时触发
				sched.scheduleJob(jobDetail, trigger);// 触发器中的任务表 ③
				//sched.start();//启动定时器开始计时(c3p0使用)
			} else {
				logger.info("本机ip是:" + localip + ",不删除>1年的单据历史表数据！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
