package com.dj.seal.util.table;

/**
 * 
 * @description 菜单表
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class ViewMenuUtil {
	public static String TABLE_NAME = "T_FA"; // 表名
	public static String MENU_ID="C_FID";
	public static String MENU_NO = "C_FAA"; // 菜单编号
	public static String MENU_NAME = "C_FAB"; // 菜单名称
	public static String MENU_IMAGE = "C_FAC"; // 菜单图片
	public static String FUNC_GROUP = "C_FAD"; // 权限组名
    public static String FUNC_TYPE="C_FAE";//菜单分类（1：系统管理，2:日志管理）
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(MENU_ID).append(" varchar(20),");
		sb.append(MENU_NO).append(" varchar(20),");
		sb.append(MENU_NAME).append(" varchar(100),");
		sb.append(MENU_IMAGE).append(" varchar(255),");
		sb.append(FUNC_GROUP).append(" varchar(10),");
		sb.append(FUNC_TYPE).append(" varchar(10)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建菜单表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + MENU_NO
				+ " varchar(20) primary key," + MENU_ID
				+ " varchar(100) not null,"+ MENU_NAME
				+ " varchar(100) not null," + MENU_IMAGE
				+ " varchar(255) not null," + FUNC_GROUP
				+ " varchar(10) not null," +  FUNC_TYPE
				+ " varchar(10) not null" + ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建菜单表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(MENU_NO).append(" varchar(20),");
		sb.append(MENU_NAME).append(" varchar(100),");
		sb.append(MENU_IMAGE).append(" varchar(255),");
		sb.append(FUNC_GROUP).append(" varchar(10)");
		sb.append(")");
		return sb.toString();
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getMENU_NO() {
		return MENU_NO;
	}

	public static String getMENU_NAME() {
		return MENU_NAME;
	}

	public static String getMENU_IMAGE() {
		return MENU_IMAGE;
	}

	public static String getFUNC_GROUP() {
		return FUNC_GROUP;
	}	
	public static String getFUNC_TYPE() {
		return FUNC_TYPE;
	}
	/**
	 * @description 返回删除菜单表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getMENU_ID() {
		return MENU_ID;
	}

}
