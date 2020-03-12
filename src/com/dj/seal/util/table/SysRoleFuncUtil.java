package com.dj.seal.util.table;

/**
 * 
 * @description 角色权限表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class SysRoleFuncUtil {

	public static String TABLE_NAME = "T_AG"; // 表名
	public static String ROLE_ID = "C_AGA"; // 角色ID
	public static String FUNC_ID = "C_AGB"; // 权限ID

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(ROLE_ID).append(" int,");
		sb.append(FUNC_ID).append(" int");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建角色权限表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + ROLE_ID
				+ " int(11) not null," + FUNC_ID + " int(11) not null" + ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建角色权限表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(ROLE_ID).append(" int,");
		sb.append(FUNC_ID).append(" int");
		sb.append(")");
		return sb.toString();
	}
	/**
	 * @description 为表设置双主键的SQL语句
	 * @return
	 */
	public static String alterSql() {
		return "alter table " + TABLE_NAME + " add constraint pk_" + TABLE_NAME
				+ " primary key(" + ROLE_ID + "," + FUNC_ID + ")";
	}

	/**
	 * @description 返回删除角色权限表的SQL语句
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

	public static String getFUNC_ID() {
		return FUNC_ID;
	}

}
