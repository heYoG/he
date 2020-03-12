package com.dj.seal.util.table;

/**
 * 
 * @description 应用系统表
 * @author zbl
 * @since 2013-5-9
 * 
 */
public class AppSystemUtil {
	public static String TABLE_NAME = "APP_SYSTEM";// 表名
	public static String APP_ID = "APP_ID";
	public static String APP_NO = "APP_NO";// 应用系统编号
	public static String APP_NAME = "APP_NAME";// 应用系统名称
	public static String APP_IP = "APP_IP";// 应用系统IP
	public static String APP_PWD = "APP_PWD";// 应用系统密码
	public static String CREATE_DATE = "CREATE_DATE";// 应用系统创建时间
	public static String CREATE_USERNAME = "CREATE_USERNAME";// 应用系统创建人

	/**
	 * @description 返回oracle建表语句
	 */
	public static String createSql4Oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(APP_ID).append(" int ,");
		sb.append(APP_NO).append(" varchar(50) ,");
		sb.append(APP_NAME).append(" varchar(255) ,");
		sb.append(APP_IP).append(" varchar(255) ,");
		sb.append(APP_PWD).append(" varchar(200) ,");
		sb.append(CREATE_DATE).append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(CREATE_USERNAME).append(" varchar(50)");
		sb.append(" )");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回mysql建表语句
	 */
	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(APP_ID).append(" int ,");
		sb.append(APP_NO).append(" varchar(50) ,");
		sb.append(APP_NAME).append(" varchar(255) ,");
		sb.append(APP_IP).append(" varchar(255) ,");
		sb.append(APP_PWD).append(" varchar(200) ,");
		sb.append(CREATE_DATE).append(" datetime default '1970-01-01 08:00:00' not null ,");
		sb.append(CREATE_USERNAME).append(" varchar(50)");
		sb.append(" )");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * @description 返回删除表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}
	/**
	 * @description 返回DB2建表语句
	 */
	public static String createSql4DB2() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(APP_ID).append(" int ,");
		sb.append(APP_NO).append(" varchar(50) ,");
		sb.append(APP_NAME).append(" varchar(255) ,");
		sb.append(APP_IP).append(" varchar(50) ,");
		sb.append(APP_PWD).append(" varchar(200) ,");
		sb.append(CREATE_DATE).append(" timestamp ,");
		sb.append(CREATE_USERNAME).append(" varchar(50)");
		sb.append(" )");
		return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(createSql4Oracle());
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getAPP_ID() {
		return APP_ID;
	}

	public static void setAPP_ID(String app_id) {
		APP_ID = app_id;
	}

	public static String getAPP_NO() {
		return APP_NO;
	}

	public static void setAPP_NO(String app_no) {
		APP_NO = app_no;
	}

	public static String getAPP_NAME() {
		return APP_NAME;
	}

	public static void setAPP_NAME(String app_name) {
		APP_NAME = app_name;
	}

	public static String getAPP_IP() {
		return APP_IP;
	}

	public static void setAPP_IP(String app_ip) {
		APP_IP = app_ip;
	}

	public static String getAPP_PWD() {
		return APP_PWD;
	}

	public static void setAPP_PWD(String app_pwd) {
		APP_PWD = app_pwd;
	}

	public static String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public static void setCREATE_DATE(String create_date) {
		CREATE_DATE = create_date;
	}

	public static String getCREATE_USERNAME() {
		return CREATE_USERNAME;
	}

	public static void setCREATE_USERNAME(String create_username) {
		CREATE_USERNAME = create_username;
	}

}
