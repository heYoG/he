package com.dj.seal.util.table;

public class UserSealUtil {
	
	public static String TABLE_NAME = "T_UBD"; // 表名 用户印章表
	public static String SEAL_ID = "C_UDB"; // 印章id
	public static String USER_ID = "C_UDC"; // 用户id
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(SEAL_ID).append(" varchar(30),");
		sb.append(USER_ID).append(" varchar(30)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * @description 返回建用户印章表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table "
				+ TABLE_NAME
				+ " ("
				+ SEAL_ID
				+ " varchar(30),"
				+ USER_ID
				+ " varchar(30)"// //foreign key references
				+ ")";
		System.out.println(create_sql+";");
		return create_sql;
	}
	/**
	 * @description 返回删除用户印章表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static String getSEAL_ID() {
		return SEAL_ID;
	}
	public static String getUSER_ID() {
		return USER_ID;
	}
	
}
