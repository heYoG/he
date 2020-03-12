/**    
 * @{#} RecordRoomMergeDao.java Create on 2015-7-19 下午10:56:55    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neusoft.po.RecordRoomMergePO;

/**    
 * 房间单据合并DAO
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public interface RecordRoomMergeDao {
	static Logger logger = LogManager.getLogger(RecordRoomMergeDao.class.getName());
	public void add(RecordRoomMergePO recordRoomMergePO);
	
	public void addNew(RecordRoomMergePO recordRoomMergePO);
	
	public int showCount(String sql);
}

