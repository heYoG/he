package com.dj.seal.util.table;

/**
 * 
 * @description 用户角色表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class SysUserRoleUtil {

	public static String TABLE_NAME = "T_AF"; // 表名
	public static String USER_ID = "C_AFA"; // 用户名
	public static String ROLE_ID = "C_AFB"; // 角色ID
	public static String USER_ROLE_STATUS = "C_AFC"; // 状态

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(USER_ID).append(" varchar(20),");
		sb.append(ROLE_ID).append(" int,");
		sb.append(USER_ROLE_STATUS).append(" char(1)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建用户角色表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + USER_ID
				+ " varchar(20)," + ROLE_ID + " int(11)," + USER_ROLE_STATUS
				+ " char(1)" + ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建用户角色表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(USER_ID).append(" varchar(20),");
		sb.append(ROLE_ID).append(" int,");
		sb.append(USER_ROLE_STATUS).append(" varchar(1)");
		sb.append(")");
		return sb.toString();
	}
	public static String getUSER_ROLE_STATUS() {
		return USER_ROLE_STATUS;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getUSER_ID() {
		return USER_ID;
	}

	public static String getROLE_ID() {
		return ROLE_ID;
	}

	/**
	 * @description 为表设置双主键的SQL语句
	 * @return
	 */
	public static String alterSql() {
		return "alter table " + TABLE_NAME + " add constraint pk_" + TABLE_NAME
				+ " primary key(" + USER_ID + "," + ROLE_ID + ")";
	}

	/**
	 * @description 返回删除用户角色表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

}
