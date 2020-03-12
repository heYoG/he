package com.dj.seal.origunJob.job;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.accumulationFund.service.impl.AccumulationDealImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class ClearAccumulationJob implements Job {
	static Logger logger=LogManager.getLogger(ClearAccumulationJob.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		AccumulationDealImpl accImpl = (AccumulationDealImpl)getBean("accumulationImpl");
		boolean b1 = accImpl.clearNoSealAccumulation();
		boolean b2 = accImpl.clearSealAccumulation();
		if(b1&b2)
			logger.info("clear accumulation successfully");
		else
			logger.info("clear accumulation with some errors occuring");
	}
	
	public static Object getBean(String name){
		if(MyApplicationContextUtil.getContext()==null){
			return null;
		}else{
			return MyApplicationContextUtil.getContext().getBean(name);
		}
	}

}
