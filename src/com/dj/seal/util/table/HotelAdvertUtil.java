package com.dj.seal.util.table;

public class HotelAdvertUtil {

	public static String TABLE_NAME = "T_HOTELADVERT"; // 表名
	public static String AD_ID = "AD_ID";// 广告ID
	public static String AD_TITLE = "AD_TITLE";// 广告标题
	public static String AD_CTIME = "AD_CTIME";// 广告创建时间
	public static String AD_FILENAME = "AD_FILENAME";// 文件名称
	public static String AD_STATE = "AD_STATE";// 广告状态（'0审核，,1未批准,2审核通过激活状态,3注销'）；
	public static String AD_DEPT = "AD_DEPT";
	public static String AD_VERSION = "AD_VERSION";// 广告版本号
	public static String APPROVE_USER = "APPROVE_USER";// 审批人approve_user
	public static String AD_OPINION = "AD_OPINION";// 审批意见
	public static String AD_APPROVETIME = "AD_APPROVETIME";// 审批时间
	public static String AD_UPDATETIME = "AD_UPDATETIME";// 广告修改时间
	public static String AD_STARTTIME = "AD_STARTTIME";
	public static String AD_ENDTIME = "AD_ENDTIME";
	public static String AD_SCORLLTIME = "AD_SCORLLTIME";// 广告轮训播放时间

	public static String getAD_SCORLLTIME() {
		return AD_SCORLLTIME;
	}

	public static void setAD_SCORLLTIME(String aD_SCORLLTIME) {
		AD_SCORLLTIME = aD_SCORLLTIME;
	}

	public static String getAD_STARTTIME() {
		return AD_STARTTIME;
	}

	public static void setAD_STARTTIME(String aD_STARTTIME) {
		AD_STARTTIME = aD_STARTTIME;
	}

	public static String getAD_ENDTIME() {
		return AD_ENDTIME;
	}

	public static void setAD_ENDTIME(String aD_ENDTIME) {
		AD_ENDTIME = aD_ENDTIME;
	}

	public static String getAD_VERSION() {
		return AD_VERSION;
	}

	public static void setAD_VERSION(String aDVERSION) {
		AD_VERSION = aDVERSION;
	}

	public static String getAPPROVE_USER() {
		return APPROVE_USER;
	}

	public static void setAPPROVE_USER(String aPPROVEUSER) {
		APPROVE_USER = aPPROVEUSER;
	}

	public static String getAD_OPINION() {
		return AD_OPINION;
	}

	public static void setAD_OPINION(String aDOPINION) {
		AD_OPINION = aDOPINION;
	}

	public static String getAD_APPROVETIME() {
		return AD_APPROVETIME;
	}

	public static void setAD_APPROVETIME(String aDAPPROVETIME) {
		AD_APPROVETIME = aDAPPROVETIME;
	}

	public static String getAD_UPDATETIME() {
		return AD_UPDATETIME;
	}

	public static void setAD_UPDATETIME(String aDUPDATETIME) {
		AD_UPDATETIME = aDUPDATETIME;
	}

	public static String getAD_TITLE() {
		return AD_TITLE;
	}

	public static void setAD_TITLE(String aDTITLE) {
		AD_TITLE = aDTITLE;
	}

	public static String getAD_DEPT() {
		return AD_DEPT;
	}

	public static void setAD_DEPT(String aDDEPT) {
		AD_DEPT = aDDEPT;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String tABLENAME) {
		TABLE_NAME = tABLENAME;
	}

	public static String getAD_ID() {
		return AD_ID;
	}

	public static void setAD_ID(String aDID) {
		AD_ID = aDID;
	}

	public static String getAD_CTIME() {
		return AD_CTIME;
	}

	public static void setAD_CTIME(String aDCTIME) {
		AD_CTIME = aDCTIME;
	}

	public static String getAD_FILENAME() {
		return AD_FILENAME;
	}

	public static void setAD_FILENAME(String fILENAME) {
		AD_FILENAME = fILENAME;
	}

	public static String getAD_STATE() {
		return AD_STATE;
	}

	public static void setAD_STATE(String aDSTATE) {
		AD_STATE = aDSTATE;
	}

	/**
	 * 返回文档表oracle建表语句
	 * 
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(AD_TITLE).append(" varchar(255),");
		sb.append(AD_ID).append(" varchar(255),");
		sb.append(AD_CTIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(AD_FILENAME).append(" varchar(255),");
		sb.append(AD_DEPT).append("  varchar(200),");
		sb.append(AD_STATE).append(" varchar(255),");
		sb.append(APPROVE_USER).append(" varchar(200),");
		sb.append(AD_OPINION).append(" varchar(200),");
		sb.append(AD_APPROVETIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(AD_UPDATETIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(AD_STARTTIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(AD_ENDTIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(AD_VERSION).append(" varchar(200)");
		sb.append(");");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * MySql创建语句
	 * 
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(AD_TITLE).append(" varchar(255),");
		sb.append(AD_ID).append(" varchar(200),");
		sb.append(AD_CTIME).append("  datetime default '1970-01-01 08:00:00',");
		sb.append(AD_FILENAME).append("  varchar(200),");
		sb.append(AD_DEPT).append("  varchar(200),");
		sb.append(AD_STATE).append(" varchar(200), ");
		sb.append(APPROVE_USER).append("  varchar(200),");
		sb.append(AD_APPROVETIME).append(
				"  datetime default '1970-01-01 08:00:00',");
		sb.append(AD_OPINION).append(" varchar(200), ");
		sb.append(AD_UPDATETIME).append(
				"  datetime default '1970-01-01 08:00:00', ");
		sb.append(AD_STARTTIME).append(
				"  datetime default '1970-01-01 08:00:00', ");
		sb.append(AD_ENDTIME).append(
				"  datetime default '1970-01-01 08:00:00', ");
		sb.append(AD_VERSION).append(" varchar(200) ");
		sb.append(")");
		return sb.toString();
	}

	/**
	 * @description 返回删除印模表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

}
