package com.dj.seal.origunJob.job;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.accountCheckSys.dao.impl.EleInternationalAccountDaoImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class BackEleAccDataJob implements Job {
	static Logger logger=LogManager.getLogger(BackEleAccDataJob.class.getName());
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EleInternationalAccountDaoImpl eac=(EleInternationalAccountDaoImpl)getBean("EleAccountCheckDao");
		int sealPicData = eac.backEleInternationalAccountSealPicData();
		int picData=0;
		logger.info("备份对账系统表数据完成,returnVal:"+sealPicData);
		if(sealPicData>0){//没备份则没删除
			logger.info("删除对账系统表已备份数据...");
			picData = eac.delEleInternationalAccountSealPicData();
		}
		if(picData>0)
			logger.info("删除对账系统已备份数据完成.");
		else
			logger.info("没有可删除的对账系统旧数据.");
	}
	
	public static Object getBean(String name){
		if(MyApplicationContextUtil.getContext()==null)
			return null;
		return MyApplicationContextUtil.getContext().getBean(name);
	}

}
