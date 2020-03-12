package com.dj.seal.util.table;

/**
 * 
 * @description 系统日志表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class LogSealWriteUtil {

	public static String TABLE_NAME = "T_SW"; // 表名
	public static String LOG_ID = "C_SAA"; // 日志ID
	public static String USER_ID = "C_SAB"; // 用户名
	public static String USER_NAME = "C_SAC"; // 用户姓名
	public static String OPR_TIME = "C_SAD"; // 操作时间
	public static String OPR_IP = "C_SAE"; // 操作IP
	public static String OPR_NUM = "C_SAH"; // 写入次数
	public static String OPR_SN = "C_SAJ"; // keySN序列号
	public static String SEAL_ID = "C_SAK"; // 印章id
	public static String KEY_DESC="C_SAL";  //印章写入可以描述
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(LOG_ID).append(" int,");
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(USER_NAME).append(" varchar(50),");
		sb.append(OPR_TIME)
		.append(
				" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(OPR_IP).append(" varchar(50),");
		sb.append(OPR_NUM).append(" varchar(50),");
		sb.append(OPR_SN).append(" varchar(100),");
		sb.append(SEAL_ID).append(" varchar(100),");
		sb.append(KEY_DESC).append(" varchar(100)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建系统日志表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + LOG_ID
				+ " int(11) auto_increment primary key," + USER_ID
				+ " varchar(50) not null," + USER_NAME
				+ " varchar(50) not null," 
				+ OPR_TIME + " datetime," + OPR_IP + " varchar(50)," + OPR_NUM
				+ " varchar(50)," + OPR_SN + " varchar(255)," + SEAL_ID + " varchar(255)," + KEY_DESC + " varchar(255)" +")";
		System.out.println(create_sql+";");
		return create_sql;
	}
	/**
	 * @description 返回建系统日志表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(LOG_ID).append(
				" int GENERATED ALWAYS AS IDENTITY primary key,");
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(USER_NAME).append(" varchar(50),");
		sb.append(OPR_TIME).append(" timestamp,");
		sb.append(OPR_IP).append(" varchar(50),");
		sb.append(OPR_NUM).append(" varchar(50),");
		sb.append(OPR_SN).append(" varchar(255),");
		sb.append(SEAL_ID).append(" varchar(255),");
		sb.append(KEY_DESC).append(" varchar(255)");
		sb.append(")");
		return sb.toString();
	}
	/**
	 * @description 返回删除系统日志表的SQL语句
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

	public static String getLOG_ID() {
		return LOG_ID;
	}

	public static void setLOG_ID(String log_id) {
		LOG_ID = log_id;
	}

	public static String getUSER_ID() {
		return USER_ID;
	}

	public static void setUSER_ID(String user_id) {
		USER_ID = user_id;
	}

	public static String getOPR_TIME() {
		return OPR_TIME;
	}

	public static void setOPR_TIME(String opr_time) {
		OPR_TIME = opr_time;
	}

	public static String getOPR_IP() {
		return OPR_IP;
	}

	public static void setOPR_IP(String opr_ip) {
		OPR_IP = opr_ip;
	}

	public static String getOPR_NUM() {
		return OPR_NUM;
	}

	public static void setOPR_NUM(String opr_num) {
		OPR_NUM = opr_num;
	}

	public static String getOPR_SN() {
		return OPR_SN;
	}

	public static void setOPR_SN(String opr_sn) {
		OPR_SN = opr_sn;
	}

	public static String getUSER_NAME() {
		return USER_NAME;
	}

	public static void setUSER_NAME(String user_name) {
		USER_NAME = user_name;
	}

	public static String getSEAL_ID() {
		return SEAL_ID;
	}

	public static void setSEAL_ID(String seal_id) {
		SEAL_ID = seal_id;
	}

	public static String getKEY_DESC() {
		return KEY_DESC;
	}

	public static void setKEY_DESC(String key_desc) {
		KEY_DESC = key_desc;
	}

}
