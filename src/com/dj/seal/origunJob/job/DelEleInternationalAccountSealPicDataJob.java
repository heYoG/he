package com.dj.seal.origunJob.job;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.accountCheckSys.dao.impl.EleInternationalAccountDaoImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class DelEleInternationalAccountSealPicDataJob implements Job {
	static Logger logger =LogManager.getLogger(DelEleInternationalAccountSealPicDataJob.class.getName());
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EleInternationalAccountDaoImpl eac=(EleInternationalAccountDaoImpl)getBean("EleAccountCheckDao");
		if(eac.delEleInternationalAccountOldTabData()>0)
			logger.info("删除对账系统历史表数据成功!");
		else
			logger.info("没有可删除的对账系统历史表数据!");
	}

	private static Object getBean(String beanName){
		if(MyApplicationContextUtil.getContext()==null){
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(beanName);
	}
	
}
