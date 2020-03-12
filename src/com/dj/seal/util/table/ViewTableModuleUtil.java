package com.dj.seal.util.table;

/**
 * 
 * @description 桌面模块表
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class ViewTableModuleUtil {
	public static String TABLE_NAME = "T_FC"; // 表名
	public static String MODULE_NO = "C_FCA"; // 模块编号
	public static String MODULE_NAME = "C_FCB"; // 模块名称
	public static String MODULE_FILE = "C_FCC"; // 模块文件名
	public static String MODULE_URL = "C_FCD"; // 模块URL
	public static String MODULE_SCROLL = "C_FCE"; // 是否可滚动
	public static String MODULE_LINES = "C_FCF"; // 显示行数

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(MODULE_NO).append(" varchar(20),");
		sb.append(MODULE_NAME).append(" varchar(200),");
		sb.append(MODULE_FILE).append(" varchar(200),");
		sb.append(MODULE_URL).append(" varchar(200),");
		sb.append(MODULE_SCROLL).append(" char(1) default 0,");
		sb.append(MODULE_LINES).append(" int default 5");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建桌面模块表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + MODULE_NO
				+ " varchar(20) primary key," + MODULE_NAME + " varchar(200) ,"
				+ MODULE_FILE + " varchar(200) ," + MODULE_URL
				+ " varchar(200) ," + MODULE_SCROLL
				+ " char(1) default 0 not null," + MODULE_LINES
				+ " int(4) default 5 not null" + ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建桌面模块表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(MODULE_NO).append(" varchar(20),");
		sb.append(MODULE_NAME).append(" varchar(200) ,");
		sb.append(MODULE_FILE).append(" varchar(200) ,");
		sb.append(MODULE_URL).append(" varchar(200) ,");
		sb.append(MODULE_SCROLL).append(" varchar(1),");
		sb.append(MODULE_LINES).append(" int");
		sb.append(")");
		return sb.toString();
	}
	/**
	 * @description 返回删除桌面模块表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getMODULE_NO() {
		return MODULE_NO;
	}

	public static void setMODULE_NO(String module_no) {
		MODULE_NO = module_no;
	}

	public static String getMODULE_NAME() {
		return MODULE_NAME;
	}

	public static void setMODULE_NAME(String module_name) {
		MODULE_NAME = module_name;
	}

	public static String getMODULE_FILE() {
		return MODULE_FILE;
	}

	public static void setMODULE_FILE(String module_file) {
		MODULE_FILE = module_file;
	}

	public static String getMODULE_URL() {
		return MODULE_URL;
	}

	public static void setMODULE_URL(String module_url) {
		MODULE_URL = module_url;
	}

	public static String getMODULE_SCROLL() {
		return MODULE_SCROLL;
	}

	public static void setMODULE_SCROLL(String module_scroll) {
		MODULE_SCROLL = module_scroll;
	}

	public static String getMODULE_LINES() {
		return MODULE_LINES;
	}

	public static void setMODULE_LINES(String module_lines) {
		MODULE_LINES = module_lines;
	}

}
