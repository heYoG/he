package com.dj.seal.util.table;

/**
 * 
 * @description 系统日志表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class FeedLogSysUtil {

	public static String TABLE_NAME = "T_FEEDLOG"; // 表名
	public static String LOG_ID = "FID"; // 日志ID
	public static String USER_ID = "U_ID"; // 操作员ID
	public static String OPR_TIME = "C_DAD"; // 操作时间
	public static String FEED_ID = "PIAOID"; // 票号ID
	public static String FEED_RET = "JIEGUO"; // 评价结果
	public static String REASON="FKYYIN";	//评价原因
	public static String LOG_REMARK = "MESSAGE"; // 日志记录

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(LOG_ID).append(" int,");
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(OPR_TIME)
		.append(
				" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(FEED_ID).append(" varchar(50),");
		sb.append(FEED_RET).append(" varchar(50),");
		sb.append(REASON).append(" varchar(50),");
		sb.append(LOG_REMARK).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
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


	public static String getLOG_REMARK() {
		return LOG_REMARK;
	}

	public static void setLOG_REMARK(String log_remark) {
		LOG_REMARK = log_remark;
	}

	public static String getFEED_ID() {
		return FEED_ID;
	}

	public static void setFEED_ID(String fEED_ID) {
		FEED_ID = fEED_ID;
	}

	public static String getFEED_RET() {
		return FEED_RET;
	}

	public static void setFEED_RET(String fEED_RET) {
		FEED_RET = fEED_RET;
	}

	public static String getREASON() {
		return REASON;
	}

	public static void setREASON(String rEASON) {
		REASON = rEASON;
	}


}
