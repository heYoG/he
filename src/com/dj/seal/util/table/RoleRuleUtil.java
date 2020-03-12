package com.dj.seal.util.table;

public class RoleRuleUtil {
	public static String TABLE_NAME = "T_JD"; // 表名
	public static String ROLE_ID = "C_JDA";// 角色ID
	public static String RULE_NO = "C_JDB";// 规则号
	public static String BUSI_NO = "C_JDC";// 规则所属业务号

	/**
	 * 返回oracle建表的SQL语句
	 * 
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(ROLE_ID).append(" int,");
		sb.append(RULE_NO).append(" varchar(255),");
		sb.append(BUSI_NO).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建市民表的SQL语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(ROLE_ID).append(" int(11),");
		sb.append(RULE_NO).append(" varchar(255),");
		sb.append(BUSI_NO).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * @description 返回建市民表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(ROLE_ID).append(" int,");
		sb.append(RULE_NO).append(" varchar(255),");
		sb.append(BUSI_NO).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString());
		return sb.toString();
	}
	/**
	 * @description 返回删除市民表的SQL语句
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

	public static String getROLE_ID() {
		return ROLE_ID;
	}

	public static void setROLE_ID(String role_id) {
		ROLE_ID = role_id;
	}

	public static String getRULE_NO() {
		return RULE_NO;
	}

	public static void setRULE_NO(String rule_no) {
		RULE_NO = rule_no;
	}

	public static String getBUSI_NO() {
		return BUSI_NO;
	}

	public static void setBUSI_NO(String busi_no) {
		BUSI_NO = busi_no;
	}
}
