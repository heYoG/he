/**    
 * @{#} RecordMergeDaoImpl.java Create on 2015-7-19 下午10:58:08    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.neusoft.dao.RecordRoomMergeDao;
import com.neusoft.po.RecordRoomMergePO;
import com.neusoft.util.table.HotelRecordRoomMergeUtil;

/**    
 * 单据合并DAO实现类
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class RecordRoomMergeDaoImpl extends BaseDAOJDBC implements RecordRoomMergeDao {
	static Logger logger = LogManager.getLogger(RecordRoomMergeDaoImpl.class.getName());
	private HotelRecordRoomMergeUtil table = new HotelRecordRoomMergeUtil();
	
	public HotelRecordRoomMergeUtil getTable() {
		return table;
	}

	public void setTable(HotelRecordRoomMergeUtil table) {
		this.table = table;
	}
	
	@Override
	public void add(RecordRoomMergePO recordRoomMergePO) {
		// TODO Auto-generated method stub
		String sql = ConstructSql.composeInsertSql(recordRoomMergePO, table);
		add(sql);
	}
	
	@Override
	public void addNew(RecordRoomMergePO recordRoomMergePO) {
		// TODO Auto-generated method stub
		String sql = ConstructSql.composeInsertSqlNew(recordRoomMergePO, table, getTableName(HotelRecordRoomMergeUtil.TABLE_NAME));
		add(sql);
	}

	@Override
	public int showCount(String sql) {
		// TODO Auto-generated method stub
		return count(sql);
	}
	
	private String getTableName(String tbName) {
		Date nowdate = new Date();
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		return tbName+yuestr;
	}
}

