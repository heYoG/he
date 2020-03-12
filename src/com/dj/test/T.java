package com.dj.test;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class T implements Job{
	static Logger logger = LogManager.getLogger(T.class.getName());
	static Scanner sc=new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		// String systemName = System.getProperty("os.name");
		// String systemUser = System.getProperty("user.name");
		// System.out.println("系统名称:"+systemName+"\n系统用户:"+systemUser);
		
//		SchedulerFactory sf=new StdSchedulerFactory();
//		Scheduler scheduler = sf.getScheduler();
//		JobDetail jobDetail = new JobDetail("t1","t2",T.class);
//		CronTrigger trigger = new CronTrigger("t3","t4");
//		trigger.setCronExpression("0 0 0 * * ?");//每晚11:30执行
//		scheduler.scheduleJob(jobDetail, trigger);
//		scheduler.start();
		t(1,"2");
		t("2",1);
	
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
			System.out.println("开始执行！");			
	}
	
	public static void t(int a,String b){
		System.out.println(a+" "+b);
	}
	
	public static void t(String b,int a){
		System.out.println(a+" "+b);
	}

}
