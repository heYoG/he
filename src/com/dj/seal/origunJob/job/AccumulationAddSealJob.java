package com.dj.seal.origunJob.job;

import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.accumulationFund.service.impl.AccumulationDealImpl;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class AccumulationAddSealJob implements Job {
	static Logger logger=LogManager.getLogger(AccumulationAddSealJob.class.getName());
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		AccumulationDealImpl accImpl = (AccumulationDealImpl)getBean("accumulationImpl");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowTime = sf.format(System.currentTimeMillis());
		if(accImpl.addSealToAccumulation()==0)
			logger.info(nowTime+" 公积金回单盖章完成!");
		else if(accImpl.addSealToAccumulation()==-1)
			logger.info(nowTime+" 清单文件HDFileInfo.txt不存在");
		else if(accImpl.addSealToAccumulation()==-2)
			logger.info(nowTime+"复制清单文件出错");
		else if(accImpl.addSealToAccumulation()==-3)
			logger.info(nowTime+"txt回单转pdf格式出错");
		else
			logger.info(nowTime+"公积金回单盖章有错,请查看addSealFailedFile.txt了解明细");
	}
	
	public static Object getBean(String beanName){
		if(MyApplicationContextUtil.getContext()==null){
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(beanName);
	}

}
