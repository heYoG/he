package com.dj.seal.util.table;

/**
 * 
 * @description 权限表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class SysFuncUtil {

	public static String TABLE_NAME = "T_AE"; // 表名
	public static String FUNC_ID = "C_AEA"; // 权限ID
	public static String MENU_NO = "C_AEB"; // 菜单编号
	public static String FUNC_VALUE = "C_AEC"; // 权限值
	public static String FUNC_NAME = "C_AED"; // 权限名
	public static String FUNC_GROUP = "C_AEE"; // 权限组名
	public static String FUNC_IMAGE = "C_AEF"; // 菜单图片
	public static String FUNC_URL = "C_AEG"; // 链接地址

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(FUNC_ID).append(" int,");
		sb.append(MENU_NO).append(" varchar(10),");
		sb.append(FUNC_VALUE).append(" int,");
		sb.append(FUNC_NAME).append(" varchar(100),");
		sb.append(FUNC_GROUP).append(" varchar(10),");
		sb.append(FUNC_IMAGE).append(" varchar(50),");
		sb.append(FUNC_URL).append(" varchar(100)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建权限表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + FUNC_ID
				+ " int(11) unique primary key check(" + FUNC_ID
				+ " between 1 and 160)," + MENU_NO + " varchar(10) not null,"
				+ FUNC_VALUE + " int(11) not null," + FUNC_NAME
				+ " varchar(100) not null unique," + FUNC_GROUP
				+ " varchar(10) not null," + FUNC_IMAGE
				+ " varchar(50) not null," + FUNC_URL
				+ " varchar(100) not null" + ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建权限表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(FUNC_ID).append(" int,");
		sb.append(MENU_NO).append(" varchar(10),");
		sb.append(FUNC_VALUE).append(" int,");
		sb.append(FUNC_NAME).append(" varchar(100),");
		sb.append(FUNC_GROUP).append(" varchar(10),");
		sb.append(FUNC_IMAGE).append(" varchar(50),");
		sb.append(FUNC_URL).append(" varchar(100)");
		sb.append(")");
		return sb.toString();
	}
	/**
	 * @description 返回删除权限表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getFUNC_ID() {
		return FUNC_ID;
	}

	public static String getMENU_NO() {
		return MENU_NO;
	}

	public static String getFUNC_VALUE() {
		return FUNC_VALUE;
	}

	public static String getFUNC_NAME() {
		return FUNC_NAME;
	}

	public static String getFUNC_GROUP() {
		return FUNC_GROUP;
	}

	public static String getFUNC_IMAGE() {
		return FUNC_IMAGE;
	}

	public static String getFUNC_URL() {
		return FUNC_URL;
	}

}
