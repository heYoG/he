package com.dj.seal.util.table;

/**
 * 
 * @description 部门表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class SysDepartmentUtil {

	public static String TABLE_NAME = "T_AB"; // 表名
	public static String DEPT_NO = "C_ABA"; // 部门编号
	public static String PRIV_NO = "C_ABB"; // 自身编号
	public static String DEPT_NAME = "C_ABC"; // 部门名称
	public static String TEL_NO = "C_ABD"; // 电话号码
	public static String FAX_NO = "C_ABE"; // 传真号码
	public static String DEPT_TAB = "C_ABF"; // 部门排序号
	public static String DEPT_PARENT = "C_ABG"; // 父部门编号
	public static String DEPT_LEVER = "C_ABH"; // 部门所属级别
	public static String DEPT_FUNC = "C_ABI"; // 部门职能
	public static String BANK_DEPT = "C_HXDEPTID";//对应的银行部门ID
	//public static String IS_DEPTFLOW = "C_ABP"; // 是否有下级部门
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DEPT_NO).append(" varchar(500),");
		sb.append(PRIV_NO).append(" varchar(100),");
		sb.append(DEPT_NAME).append(" varchar(255),");
		sb.append(TEL_NO).append(" varchar(50),");
		sb.append(FAX_NO).append(" varchar(50),");
		sb.append(DEPT_TAB).append(" varchar(300),");
		sb.append(DEPT_PARENT).append(" varchar(500) default '0',");
		sb.append(DEPT_LEVER).append(" int default 1,");
		sb.append(DEPT_FUNC).append(" varchar(255)");
	//	sb.append(IS_DEPTFLOW).append(" varchar(50)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建部门表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + DEPT_NO
				+ " varchar(255) primary key," + PRIV_NO
				+ " varchar(100) not null unique," + DEPT_NAME
				+ " varchar(255) not null," + TEL_NO + " varchar(50)," + FAX_NO
				+ " varchar(50)," + DEPT_TAB + " varchar(200)," + DEPT_PARENT
				+ " varchar(255) default '0' not null," + DEPT_LEVER
				+ " int(2) default '1' not null," + DEPT_FUNC + " varchar(255)"
				+ ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	public static String createDB2Sql(){
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(DEPT_NO).append(" varchar(255),");
		sb.append(PRIV_NO).append(" varchar(100),");
		sb.append(DEPT_NAME).append(" varchar(255),");
		sb.append(TEL_NO).append(" varchar(50),");
		sb.append(FAX_NO).append(" varchar(50),");
		sb.append(DEPT_TAB).append(" varchar(50),");
		sb.append(DEPT_PARENT).append(" varchar(255),");
		sb.append(DEPT_LEVER).append(" int,");
		sb.append(DEPT_FUNC).append(" varchar(255)");
		sb.append(")");
		return sb.toString();
	}
	/**
	 * @description 返回删除部门表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static String getPRIV_NO() {
		return PRIV_NO;
	}

	public static String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public static String getTEL_NO() {
		return TEL_NO;
	}

	public static String getFAX_NO() {
		return FAX_NO;
	}

	public static String getDEPT_TAB() {
		return DEPT_TAB;
	}

	public static String getDEPT_PARENT() {
		return DEPT_PARENT;
	}

	public static String getDEPT_LEVER() {
		return DEPT_LEVER;
	}

	public static String getDEPT_FUNC() {
		return DEPT_FUNC;
	}
	
	public static String getBANK_DEPT() {
		return BANK_DEPT;
	}

}
