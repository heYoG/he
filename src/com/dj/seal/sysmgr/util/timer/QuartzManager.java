package com.dj.seal.sysmgr.util.timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lzl
 * 
 * 2013-4-16 
 */
public class QuartzManager {
	static Logger logger = LogManager.getLogger(QuartzManager.class.getName());
	private static Scheduler scheduler;

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"quartzDynamic.xml");
		scheduler = (StdScheduler) context.getBean("schedulerFactory");
	}

	// @Scheduled(cron="0/5 * * * * ? ")
	public void test() {
		System.out.println("5秒执行一次");

		try {
			System.out.println(scheduler.getGlobalTriggerListeners().size());
			System.out.println(scheduler.getCurrentlyExecutingJobs().size());
			System.out.println(scheduler.getSchedulerName());
			System.out.println(scheduler.getJobGroupNames().length);
			// System.out.println(scheduler.getJobGroupNames()[0]);
			System.out.println(scheduler.getSchedulerInstanceId());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 启动一个自定义的job
	 * 
	 * @param schedulingJob
	 *            自定义的job
	 * @param paramsMap
	 *            传递给job执行的数据
	 * @param isStateFull
	 *            是否是一个同步定时任务，true：同步，false：异步
	 * @return 成功则返回true，否则返回false
	 */
	public static boolean enableCronSchedule(CustomJob schedulingJob,
			JobDataMap paramsMap, boolean isStateFull) {
		if (schedulingJob == null) {
			return false;
		}
		try {
			CronTrigger trigger = (CronTrigger) scheduler
					.getTrigger(schedulingJob.getTriggerName(), schedulingJob
							.getJobGroup());
			if (null == trigger) {// 如果不存在该trigger则创建一个
				JobDetail jobDetail = null;
				if (isStateFull) {
					jobDetail = new JobDetail(schedulingJob.getJobId(),
							schedulingJob.getJobGroup(), schedulingJob
									.getStateFulljobExecuteClass());
				} else {
					jobDetail = new JobDetail(schedulingJob.getJobId(),
							schedulingJob.getJobGroup(), schedulingJob
									.getJobExecuteClass());
				}
				jobDetail.setJobDataMap(paramsMap);
				trigger = new CronTrigger(schedulingJob.getTriggerName(),
						schedulingJob.getJobGroup(), schedulingJob
								.getCronExpression());
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				trigger.setCronExpression(schedulingJob.getCronExpression());
				scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(),
						trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 禁用一个job
	 * 
	 * @param jobId
	 *            需要被禁用的job的ID
	 * @param jobGroupId
	 *            需要被警用的jobGroupId
	 * @return 成功则返回true，否则返回false
	 */
	public static boolean disableSchedule(String jobId, String jobGroupId) {
		if (jobId.equals("") || jobGroupId.equals("")) {
			return false;
		}
		try {
			Trigger trigger = getJobTrigger(jobId, jobGroupId);
			if (null != trigger) {
				scheduler.deleteJob(jobId, jobGroupId);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 得到job的详细信息
	 * 
	 * @param jobId
	 *            job的ID
	 * @param jobGroupId
	 *            job的组ID
	 * @return job的详细信息,如果job不存在则返回null
	 */
	public static JobDetail getJobDetail(String jobId, String jobGroupId) {
		if (jobId.equals("") || jobGroupId.equals("") || null == jobId
				|| jobGroupId == null) {
			return null;
		}
		try {
			return scheduler.getJobDetail(jobId, jobGroupId);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到job对应的Trigger
	 * 
	 * @param jobId
	 *            job的ID
	 * @param jobGroupId
	 *            job的组ID
	 * @return job的Trigger,如果Trigger不存在则返回null
	 */
	public static Trigger getJobTrigger(String jobId, String jobGroupId) {
		if (jobId.equals("") || jobGroupId.equals("") || null == jobId
				|| jobGroupId == null) {
			return null;
		}
		try {
			return scheduler.getTrigger(jobId + "Trigger", jobGroupId);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
	}

}
