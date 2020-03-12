/**    
 * @{#} TableMonthConfigDaoImpl.java Create on 2015-7-19 下午10:58:08    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.DaoUtil;
import com.neusoft.dao.TableMonthConfigDao;
import com.neusoft.po.TableMonthConfigPO;
import com.neusoft.util.table.TableMonthConfigUtil;

/**    
 * 月表名配置DAO实现类
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class TableMonthConfigDaoImpl extends BaseDAOJDBC implements TableMonthConfigDao {
	static Logger logger = LogManager.getLogger(TableMonthConfigDaoImpl.class.getName());
	private TableMonthConfigUtil table = new TableMonthConfigUtil();
	
	public TableMonthConfigUtil getTable() {
		return table;
	}

	public void setTable(TableMonthConfigUtil table) {
		this.table = table;
	}

	@Override
	public TableMonthConfigPO getDefault() {
		// TODO Auto-generated method stub
		String sql = "select * from " + TableMonthConfigUtil.TABLE_NAME + " limit 1";
		List list = select(sql);
		TableMonthConfigPO obj = new TableMonthConfigPO();
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (TableMonthConfigPO) DaoUtil.setPo(obj, map, table);
			return obj;
		} else {
			return null;
		}
	}

	@Override
	public void updateTableMonthConfig(TableMonthConfigPO tableMonthConfigPO) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("update ").append(TableMonthConfigUtil.TABLE_NAME);
		sb.append(" set ").append(TableMonthConfigUtil.BEGINYEAR).append(" = '").append(tableMonthConfigPO.getBeginyear()).append("', ");
		sb.append(TableMonthConfigUtil.BEGINMONTH).append(" = '").append(tableMonthConfigPO.getBeginmonth()).append("', ");
		sb.append(TableMonthConfigUtil.ENDYEAR).append(" = '").append(tableMonthConfigPO.getEndyear()).append("', ");
		sb.append(TableMonthConfigUtil.ENDMONTH).append(" = '").append(tableMonthConfigPO.getEndmonth()).append("'");
		update(sb.toString());
	}
}

