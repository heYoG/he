/**    
 * @{#} TableMonthConfigServiceImpl.java Create on 2015-7-20 上午09:30:10    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neusoft.dao.impl.TableMonthConfigDaoImpl;
import com.neusoft.po.TableMonthConfigPO;
import com.neusoft.service.TableMonthConfigService;

/**    
 * 单据合并Service实现类
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class TableMonthConfigServiceImpl implements TableMonthConfigService {
	static Logger logger = LogManager.getLogger(TableMonthConfigServiceImpl.class.getName());
	private TableMonthConfigDaoImpl tableMonthConfigDao;

	public TableMonthConfigDaoImpl getTableMonthConfigDao() {
		return tableMonthConfigDao;
	}

	public void setTableMonthConfigDao(TableMonthConfigDaoImpl tableMonthConfigDao) {
		this.tableMonthConfigDao = tableMonthConfigDao;
	}

	/**
	 * 刷新
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		Date curDate = new Date();
		String yearMonth = new SimpleDateFormat("yyyyMM").format(curDate);
		TableMonthConfigPO tableMonthConfigPO = tableMonthConfigDao.getDefault();
		if (tableMonthConfigPO != null) {
			tableMonthConfigPO.setEndyear(yearMonth.substring(0, 4));
			tableMonthConfigPO.setEndmonth(yearMonth.substring(4, yearMonth.length()));
			tableMonthConfigDao.updateTableMonthConfig(tableMonthConfigPO);
		}
	}

	/**
	 * 根据表名获取多个月表名列表
	 * @param tableName
	 * @return
	 */
	@Override
	public List<String> getTableMonthNameList(String tableName) {
		// TODO Auto-generated method stub
		List<String> tableNameList = new ArrayList<String>();
		TableMonthConfigPO tableMonthConfigPO = tableMonthConfigDao.getDefault();
		if (tableMonthConfigPO != null) {
			int beginYear = Integer.valueOf(tableMonthConfigPO.getBeginyear());
			int beginMonth = Integer.valueOf(tableMonthConfigPO.getBeginmonth());
			int endYear = Integer.valueOf(tableMonthConfigPO.getEndyear());
			int endMonth = Integer.valueOf(tableMonthConfigPO.getEndmonth());
			String tempMonth = "";
			for (int i = beginYear; i <= endYear; i++) {
				int start = 0; // 迭代开始月
				int end = 0;   // 迭代结束月
				if (beginYear == endYear) {
					start = beginMonth;
					end = endMonth;
				} else if (beginYear < endYear) {
					if (i == beginYear) {
						start = beginMonth;
						end = 12;
					} else if (i > beginYear && i < endYear) {
						start = 1;
						end = 12;
					} else {
						start = 1;
						end = endMonth;
					}
				}
				
				for (int j = start; j <= end; j++) {
					if (j < 10) {
						tempMonth = "0" + j;
					} else {
						tempMonth = String.valueOf(j);
					}
					tableNameList.add(tableName + i + tempMonth);
				}
			}
			
		} else {
			tableNameList.add(tableName);
		}
		
		return tableNameList;
	}
}

