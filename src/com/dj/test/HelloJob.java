package com.dj.test;


import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class HelloJob implements Job {
	static Scheduler sched = null;
private static int i=0;
	@Override
	public void execute(JobExecutionContext cntxt) throws JobExecutionException {
		System.out
				.println("输出："
						+ cntxt.getJobDetail().getJobDataMap().get("name"));
		i++;
		if(i==9){
			try {
				cntxt.getScheduler().shutdown();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			SchedulerFactory schedFact = new StdSchedulerFactory();
			sched = schedFact.getScheduler();
			sched.start();
			JobDetail jobDetail = new JobDetail("a", "b", HelloJob.class);
			jobDetail.getJobDataMap().put("name", "lucy");

			CronTrigger trigger = new CronTrigger("c", "d");
			String first = "0";
			String second = "12";
			String three = "8";
			getTimeConfig(first, second, three);
			trigger.setCronExpression("0/1 * * * * ? "); // 启动之后立即执行 每一秒继续重复。
			sched.scheduleJob(jobDetail, trigger);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getTimeConfig(String cfrequency, String cdate, String ctime) {
		String cronExpression = "";
		if (cfrequency.equals("0")) {// 每天
			cronExpression = "0 0 " + ctime + " * * ?";
		} else if (cfrequency.equals("1")) {// 每周第几天
			cronExpression = "0 0 " + ctime + " ? * " + cdate;
		} else if (cfrequency.equals("2")) {// 每月第几天
			cronExpression = "0 0 " + ctime + " " + cdate + " * ?";
		}
		System.out.println("定时任务时间表达式:" + cronExpression);
		return cronExpression;
	}
}
