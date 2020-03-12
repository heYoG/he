/**    
 * @{#} RecordMergeService.java Create on 2015-7-20 上午09:29:04    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.web.SearchForm;
import com.neusoft.po.RecordMergePO;

/**    
 * 单据合并Service
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public interface RecordMergeService  {
	static Logger logger = LogManager.getLogger(RecordMergeService.class.getName());
	/**
	 * 添加单据合并数据
	 * @param recordMergePO
	 * @return
	 * @author 陈钜珩
	 * Create on 2015-7-20 上午09:38:58
	 */
	public String addRecordMerge(RecordMergePO recordMergePO);
	
	/**
	 * 获取单据合并
	 * @param pageIndex
	 * @param pageSize
	 * @param searchForm
	 * @return
	 * @author 陈钜珩
	 * Create on 2015-7-20 下午04:04:08
	 */
	public PageSplit getRecordMergeList(int pageIndex, int pageSize, SearchForm searchForm) throws Exception;
}

