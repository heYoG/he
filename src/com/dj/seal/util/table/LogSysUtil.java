package com.dj.seal.util.table;

/**
 * 
 * @description 系统日志表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class LogSysUtil {

	public static String TABLE_NAME = "T_DA"; // 表名
	public static String LOG_ID = "C_DAA"; // 日志ID
	public static String USER_ID = "C_DAB"; // 用户名
	public static String USER_NAME = "C_DAC"; // 用户姓名
	public static String OPR_TIME = "C_DAD"; // 操作时间
	public static String OPR_IP = "C_DAE"; // 操作IP
	public static String OPR_TYPE = "C_DAF"; // 操作菜单
	public static String LOG_REMARK = "C_DAG"; // 日志记录

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
		sb.append(OPR_TYPE).append(" varchar(50),");
		sb.append(LOG_REMARK).append(" varchar(255)");
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
				+ OPR_TIME + " datetime," + OPR_IP + " varchar(50)," + OPR_TYPE
				+ " varchar(50)," + LOG_REMARK + " varchar(255)" + ")";
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
		sb.append(OPR_TYPE).append(" varchar(50),");
		sb.append(LOG_REMARK).append(" varchar(255)");
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

	public static String getOPR_TYPE() {
		return OPR_TYPE;
	}

	public static void setOPR_TYPE(String opr_type) {
		OPR_TYPE = opr_type;
	}

	public static String getLOG_REMARK() {
		return LOG_REMARK;
	}

	public static void setLOG_REMARK(String log_remark) {
		LOG_REMARK = log_remark;
	}

	public static String getUSER_NAME() {
		return USER_NAME;
	}

	public static void setUSER_NAME(String user_name) {
		USER_NAME = user_name;
	}

}
