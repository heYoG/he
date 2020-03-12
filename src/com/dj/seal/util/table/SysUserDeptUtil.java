package com.dj.seal.util.table;

/**
 * 
 * @description 用户部门表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class SysUserDeptUtil {

	public static String TABLE_NAME = "T_AH"; // 表名
	public static String USER_ID = "C_AHA"; // 用户名
	public static String DEPT_NO = "C_AHB"; // 部门编号
	
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(USER_ID).append(" varchar(20),");
		sb.append(DEPT_NO).append(" varchar(200)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建用户部门表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + USER_ID
				+ " varchar(20) not null," + DEPT_NO + " varchar(200) not null"+")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建用户部门表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(USER_ID).append(" varchar(20),");
		sb.append(DEPT_NO).append(" varchar(200)");
		sb.append(")");
		return sb.toString();
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getUSER_ID() {
		return USER_ID;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}
   
	

	/**
	 * @description 为表设置双主键的SQL语句
	 * @return
	 */
	public static String alterSql() {
		return "alter table " + TABLE_NAME + " add constraint pk_" + TABLE_NAME
				+ " primary key(" + USER_ID + "," + DEPT_NO + ")";
	}

	/**
	 * @description 返回删除用户部门表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}
}
