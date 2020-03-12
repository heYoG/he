package com.dj.seal.util.table;

public class UserCertUtil {

	public static String TABLE_NAME = "T_USERCERT"; // 表名
	public static String USER_ID="user_id";//用户名
	public static String CERT_NO="cert_no";//用户证书序列号
	public static String IS_ACTIVE="isactive";//用户证书序列号
	/**
	 * 返回oracle的建表语句
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(USER_ID).append(" varchar(20),");
		sb.append(CERT_NO).append(" varchar(255),");
		sb.append(IS_ACTIVE).append(" varchar(5)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * @description 返回mysql建表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + USER_ID
				+ " varchar(20)," + CERT_NO + " varchar(255)," + IS_ACTIVE
				+ " varchar(5)" + ")";
		System.out.println(create_sql+";");
		return create_sql;
	}
	/**
	 * @description 返回db2的建表语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(USER_ID).append(" varchar(20),");
		sb.append(CERT_NO).append(" varchar(255),");
		sb.append(IS_ACTIVE).append(" varchar(5)");
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * @description 返回删除表的SQL语句
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
	public static String getUSER_ID() {
		return USER_ID;
	}
	public static void setUSER_ID(String user_id) {
		USER_ID = user_id;
	}
	public static String getCERT_NO() {
		return CERT_NO;
	}
	public static void setCERT_NO(String cert_no) {
		CERT_NO = cert_no;
	}
	public static String getIS_ACTIVE() {
		return IS_ACTIVE;
	}
	public static void setIS_ACTIVE(String is_active) {
		IS_ACTIVE = is_active;
	}
	
}
