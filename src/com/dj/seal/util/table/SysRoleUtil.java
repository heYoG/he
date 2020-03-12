package com.dj.seal.util.table;

/**
 * 
 * @description 角色表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class SysRoleUtil {

	public static String TABLE_NAME = "T_AD"; // 表名
	public static String ROLE_ID = "C_ADA"; // 角色ID
	public static String DEPT_NO = "C_ADB"; // 部门编号
	public static String ROLE_STATUS = "C_ADC"; // 角色状态
	public static String ROLE_NAME = "C_ADD"; // 角色名称
	public static String ROLE_FUN1 = "C_ADE"; // 权限组一的权限值
	public static String ROLE_FUN2 = "C_ADF"; // 权限组二的权限值
	public static String ROLE_FUN3 = "C_ADG"; // 权限组三的权限值
	public static String ROLE_FUN4 = "C_ADH"; // 权限组四的权限值
	public static String ROLE_FUN5 = "C_ADI"; // 权限组五的权限值
	public static String ROLE_TAB = "C_ADJ"; // 角色排序号

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(ROLE_ID).append(" int,");
		sb.append(DEPT_NO).append(" varchar(100),");
		sb.append(ROLE_STATUS).append(" char(1),");
		sb.append(ROLE_NAME).append(" varchar(100),");
		sb.append(ROLE_FUN1).append(" int,");
		sb.append(ROLE_FUN2).append(" int,");
		sb.append(ROLE_FUN3).append(" int,");
		sb.append(ROLE_FUN4).append(" int,");
		sb.append(ROLE_FUN5).append(" int,");
		sb.append(ROLE_TAB).append(" varchar(20)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建角色表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + ROLE_ID
				+ " int(11) auto_increment primary key," + DEPT_NO
				+ " varchar(100) not null," + ROLE_STATUS
				+ " char(1) not null," + ROLE_NAME
				+ " varchar(100) not null unique," + ROLE_FUN1
				+ " int(11) not null," + ROLE_FUN2 + " int(11) not null,"
				+ ROLE_FUN3 + " int(11) not null," + ROLE_FUN4
				+ " int(11) not null," + ROLE_FUN5 + " int(11) not null,"
				+ ROLE_TAB + " varchar(20) not null" + ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建角色表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(ROLE_ID).append(
				" int GENERATED ALWAYS AS IDENTITY primary key,");
		sb.append(DEPT_NO).append(" varchar(100),");
		sb.append(ROLE_STATUS).append(" varchar(1),");
		sb.append(ROLE_NAME).append(" varchar(100),");
		sb.append(ROLE_FUN1).append(" int,");
		sb.append(ROLE_FUN2).append(" int,");
		sb.append(ROLE_FUN3).append(" int,");
		sb.append(ROLE_FUN4).append(" int,");
		sb.append(ROLE_FUN5).append(" int,");
		sb.append(ROLE_TAB).append(" varchar(20)");
		sb.append(")");
		return sb.toString();
	}

	/**
	 * @description 返回删除角色表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getROLE_ID() {
		return ROLE_ID;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static String getROLE_STATUS() {
		return ROLE_STATUS;
	}

	public static String getROLE_NAME() {
		return ROLE_NAME;
	}

	public static String getROLE_FUN1() {
		return ROLE_FUN1;
	}

	public static String getROLE_FUN2() {
		return ROLE_FUN2;
	}

	public static String getROLE_FUN3() {
		return ROLE_FUN3;
	}

	public static String getROLE_FUN4() {
		return ROLE_FUN4;
	}

	public static String getROLE_FUN5() {
		return ROLE_FUN5;
	}

	public static String getROLE_TAB() {
		return ROLE_TAB;
	}

}
