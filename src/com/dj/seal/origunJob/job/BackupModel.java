package com.dj.seal.origunJob.job;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.util.spring.MyApplicationContextUtil;

public class BackupModel implements Job {
	Logger logger = LogManager.getLogger(BackupModel.class.getName());

	private static Object getBean(String name) {
		Object obj = MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		/*logger.info("------开始执行备份模板------");
		ModelFileServiceImpl modelFS = (ModelFileServiceImpl) getBean("modelFileService");
		long startT = System.currentTimeMillis();
		if (modelFS.backupModel() > 0) {
			logger.info("------开始执行删除已备份模板------");
			int a = modelFS.delModel();
			long endT = System.currentTimeMillis();
			if (a > 0) {
				logger.info("备份模板正常结束,总耗时:" + (endT - startT));
			} else {
				logger.info("备份模板未正常结束,请确定当前用户对数据有删除权限...");
			}
		} else {
			logger.info("------备份模板失败,没有可备份数据------");
		}*/

	}

}
