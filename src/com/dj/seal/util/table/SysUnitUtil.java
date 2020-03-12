package com.dj.seal.util.table;

/**
 * 
 * @description 单位表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class SysUnitUtil {

	public static String TABLE_NAME = "T_AA"; // 表名
	public static String UNIT_NAME = "C_AAA"; // 单位名称
	public static String TEL_NO = "C_AAB"; // 电话号码
	public static String FAX_NO = "C_AAC"; // 传真号码
	public static String POST_NO = "C_AAD"; // 邮政编码
	public static String UNIT_ADDRESS = "C_AAE"; // 单位地址
	public static String UNIT_URL = "C_AAF"; // 单位网址
	public static String UNIT_EMAIL = "C_AAG"; // 单位邮箱
	public static String BANK_NAME = "C_AAH"; // 开户银行
	public static String BANK_NO = "C_AAI"; // 银行帐号
	public static String DEPT_NO = "C_AAJ"; // 部门编号

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(UNIT_NAME).append(" varchar(100),");
		sb.append(TEL_NO).append(" varchar(100),");
		sb.append(FAX_NO).append(" varchar(100),");
		sb.append(POST_NO).append(" varchar(50),");
		sb.append(UNIT_ADDRESS).append(" varchar(200),"); //之前没有加这个字段 
		sb.append(UNIT_URL).append(" varchar(200),");
		sb.append(UNIT_EMAIL).append(" varchar(200),");
		sb.append(BANK_NAME).append(" varchar(200),");
		sb.append(BANK_NO).append(" varchar(200),");
		sb.append(DEPT_NO).append(" varchar(200)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建单位表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String sql= "create table " + TABLE_NAME + " (" + UNIT_NAME
				+ " varchar(100) not null," + TEL_NO + " varchar(100)," + FAX_NO
				+ " varchar(100)," + POST_NO + " varchar(50)," + UNIT_ADDRESS
				+ " varchar(200)," + UNIT_URL + " varchar(200)," + UNIT_EMAIL
				+ " varchar(200)," + BANK_NAME + " varchar(200)," + BANK_NO
				+ " varchar(200)," + DEPT_NO
				+ " varchar(200) default 'AA' not null" + ")";
		System.out.println(sql+";");
		return sql;
	}
	public static String createDB2Sql(){
		return createSql();
	}
	/**
	 * @description 返回删除单位表的SQL语句
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

	public static String getUNIT_NAME() {
		return UNIT_NAME;
	}

	public static void setUNIT_NAME(String unit_name) {
		UNIT_NAME = unit_name;
	}

	public static String getTEL_NO() {
		return TEL_NO;
	}

	public static void setTEL_NO(String tel_no) {
		TEL_NO = tel_no;
	}

	public static String getFAX_NO() {
		return FAX_NO;
	}

	public static void setFAX_NO(String fax_no) {
		FAX_NO = fax_no;
	}

	public static String getPOST_NO() {
		return POST_NO;
	}

	public static void setPOST_NO(String post_no) {
		POST_NO = post_no;
	}

	public static String getUNIT_ADDRESS() {
		return UNIT_ADDRESS;
	}

	public static void setUNIT_ADDRESS(String unit_address) {
		UNIT_ADDRESS = unit_address;
	}

	public static String getUNIT_URL() {
		return UNIT_URL;
	}

	public static void setUNIT_URL(String unit_url) {
		UNIT_URL = unit_url;
	}

	public static String getUNIT_EMAIL() {
		return UNIT_EMAIL;
	}

	public static void setUNIT_EMAIL(String unit_email) {
		UNIT_EMAIL = unit_email;
	}

	public static String getBANK_NAME() {
		return BANK_NAME;
	}

	public static void setBANK_NAME(String bank_name) {
		BANK_NAME = bank_name;
	}

	public static String getBANK_NO() {
		return BANK_NO;
	}

	public static void setBANK_NO(String bank_no) {
		BANK_NO = bank_no;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}

}
