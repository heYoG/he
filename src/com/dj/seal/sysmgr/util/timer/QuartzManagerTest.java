package com.dj.seal.sysmgr.util.timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;

/**
 * @author lzl
 * 
 * 2013-4-16 
 */
public class QuartzManagerTest {
	
	static Logger logger = LogManager.getLogger(QuartzManagerTest.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CustomJob job = new CustomJob();
		job.setJobId("job1");
		job.setJobGroup("job1_group");
		job.setJobName("第一个测试定时器");
		job.setMemos("我是第一个测试定时器的描述");
		job.setCronExpression("0/5 * * * * ?");// 每五秒执行一次
		job.setStateFulljobExecuteClass(QuartzJobOne.class);

		JobDataMap paramsMap = new JobDataMap();
		QuartzManager.enableCronSchedule(job, paramsMap, true);

//		System.out.println(QuartzManager.disableSchedule("job1", "job1_group"));
	}

}
