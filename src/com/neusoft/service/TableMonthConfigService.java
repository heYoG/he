/**    
 * @{#} TableMonthConfigService.java Create on 2015-7-20 上午09:29:04    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.service;

import java.util.List;

/**    
 * 月表名配置Service
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public interface TableMonthConfigService  {
	/**
	 * 刷新
	 */
	public void refresh();
	
	/**
	 * 根据表名获取多个月表名列表
	 * @param tableName
	 * @return
	 */
	public List<String> getTableMonthNameList(String tableName);
}

