/**    
 * @{#} RecordMergeDaoImpl.java Create on 2015-7-19 下午10:58:08    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.dao.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.neusoft.dao.RecordMergeDao;
import com.neusoft.po.RecordMergePO;
import com.neusoft.util.table.HotelRecordMergeUtil;

/**    
 * 单据合并DAO实现类
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class RecordMergeDaoImpl extends BaseDAOJDBC implements RecordMergeDao {
	static Logger logger = LogManager.getLogger(RecordMergeDaoImpl.class.getName());
	private HotelRecordMergeUtil table = new HotelRecordMergeUtil();
	
	public HotelRecordMergeUtil getTable() {
		return table;
	}

	public void setTable(HotelRecordMergeUtil table) {
		this.table = table;
	}

	@Override
	public void add(RecordMergePO recordMergePO) {
		// TODO Auto-generated method stub
		String sql = ConstructSql.composeInsertSql(recordMergePO, table);
		add(sql);
	}

	@Override
	public int showCount(String sql) {
		// TODO Auto-generated method stub
		return count(sql);
	}

}

