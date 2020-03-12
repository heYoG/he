package com.dj.seal.util.table;

public class RoleSealUtil {
  

	public static String TABLE_NAME = "T_RBD"; // 表名   角色印章表
	public static String SEAL_ID = "C_RDB"; // 印章id
	public static String ROLE_ID = "C_RDC"; // 角色id
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(SEAL_ID).append(" varchar(30),");
		sb.append(ROLE_ID).append(" varchar(30)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * @description 返回建角色印章表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table "
				+ TABLE_NAME
				+ " ("
				+ SEAL_ID
				+ " varchar(30),"
				+ ROLE_ID
				+ " varchar(30)"// //foreign key references
				+ ")";
		System.out.println(create_sql+";");
		return create_sql;
	}
	/**
	 * @description 返回建角色印章表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		return createSql();
	}
	/**
	 * @description 返回删除角色印章表的SQL语句
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
	public static String getROLE_ID() {
		return ROLE_ID;
	}
}
