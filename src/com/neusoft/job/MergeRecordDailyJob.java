/**    
 * @{#} MergeRecordDailyJob.java Create on 2015-7-17 下午03:17:39    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.neusoft.po.RecordMergePO;
import com.neusoft.service.RecordMergeService;

/**    
 * 合并昨天的单据文件
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class MergeRecordDailyJob implements Job {
	static Logger logger = LogManager.getLogger(MergeRecordDailyJob.class.getName());
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("=================MergeRecordDailyJob");
		
		RecordMergeService recordMergeService = (RecordMergeService)getBean("recordMergeService");
		RecordMergePO recordMergePO = new RecordMergePO();
		recordMergeService.addRecordMerge(recordMergePO);
	}
	
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
}

