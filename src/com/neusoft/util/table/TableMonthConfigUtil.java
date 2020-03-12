/**    
 * @{#} TableMonthConfigUtil.java Create on 2015-7-19 下午09:55:55    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.util.table;

/**    
 * 月表名配置表
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class TableMonthConfigUtil {
	public static String TABLE_NAME = "t_tablemonthconfig";
	public static String BEGINYEAR = "beginyear"; // 开始年份
	public static String BEGINMONTH = "beginmonth"; // 开始月份
	public static String ENDYEAR = "endyear"; // 结束年份
	public static String ENDMONTH = "endmonth"; // 结束月份
	
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String tABLENAME) {
		TABLE_NAME = tABLENAME;
	}

	public static String getBEGINYEAR() {
		return BEGINYEAR;
	}

	public static void setBEGINYEAR(String bEGINYEAR) {
		BEGINYEAR = bEGINYEAR;
	}

	public static String getBEGINMONTH() {
		return BEGINMONTH;
	}

	public static void setBEGINMONTH(String bEGINMONTH) {
		BEGINMONTH = bEGINMONTH;
	}

	public static String getENDYEAR() {
		return ENDYEAR;
	}

	public static void setENDYEAR(String eNDYEAR) {
		ENDYEAR = eNDYEAR;
	}

	public static String getENDMONTH() {
		return ENDMONTH;
	}

	public static void setENDMONTH(String eNDMONTH) {
		ENDMONTH = eNDMONTH;
	}

	/**
	 * @description MySql
	 * @return
	 */
	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(BEGINYEAR).append(" varchar(5),");
		sb.append(BEGINMONTH).append(" varchar(5),");
		sb.append(ENDYEAR).append(" varchar(5),");
		sb.append(ENDMONTH).append(" varchar(5)");
		sb.append(")");
		return sb.toString();
	}
	
	public static String dropSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("drop table ").append(TABLE_NAME);
		return sb.toString();
	}
}

