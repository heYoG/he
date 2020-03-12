/**    
 * @{#} RecordMergeDao.java Create on 2015-7-19 下午10:56:55    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neusoft.po.RecordMergePO;

/**    
 * 单据合并DAO
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public interface RecordMergeDao {
	static Logger logger = LogManager.getLogger(RecordMergeDao.class.getName());
	public void add(RecordMergePO recordMergePO);
	
	public int showCount(String sql);
}

