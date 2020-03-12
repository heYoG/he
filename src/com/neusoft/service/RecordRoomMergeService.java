/**    
 * @{#} RecordRoomMergeService.java Create on 2015-7-20 上午09:29:04    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.web.SearchForm;

/**    
 * 房间单据合并Service
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public interface RecordRoomMergeService  {
	static Logger logger = LogManager.getLogger(RecordRoomMergeService.class.getName());
//	/**
//	 * 添加单据合并数据
//	 * @param recordMergePO
//	 * @return
//	 * @author 陈钜珩
//	 * Create on 2015-7-20 上午09:38:58
//	 */
//	public String addRecordRoomMerge(RecordRoomMergePO recordRoomMergePO);
	
	/**
	 * 处理离店单据，并和入店单据进行合并
	 * @param checkOutRecordid 离店单据id
	 * @author 陈钜珩
	 * Create on 2015-7-21 上午10:05:12
	 */
	public void handleRoomCheckOut(String checkOutRecordid) throws Exception;
	
	/**
	 * 获取单据合并
	 * @param pageIndex
	 * @param pageSize
	 * @param searchForm
	 * @return
	 * @author 陈钜珩
	 * Create on 2015-7-20 下午04:04:08
	 */
	public PageSplit getRecordRoomMergeList(int pageIndex, int pageSize, SearchForm searchForm) throws Exception;
}

