package com.dj.seal.util.table;

public class HuiZhiLogUtil {
	public static String TABLE_NAME = "HUIZHILOG"; // 表名
	public static String USER_ID = "USER_ID"; // 用户id
	public static String CASESEQID = "CASESEQID"; // 流水号
	public static String FILE_NAME = "FILE_NAME"; // 文档名称
	public static String OPR_TIME = "OPR_TIME"; // 操作时间
	public static String STATUS = "STATUS"; // 盖章结果
	public static String SEAL_TYPE = "SEAL_TYPE"; // 印章类型
	public static String DEPT_NO = "DEPT_NO"; // 部门编号
	public static String DEPT_NAME = "DEPT_NAME"; // 部门名称
	public static String SEAL_NAME = "SEAL_NAME"; // 印章名称

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(CASESEQID).append(" varchar(200),");
		sb.append(FILE_NAME).append(" varchar(150),");
		sb.append(OPR_TIME)
				.append("  date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(STATUS).append(" varchar(50),");
		sb.append(SEAL_TYPE).append(" varchar(50),");
		sb.append(DEPT_NO).append(" varchar(50),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(DEPT_NAME).append(" varchar(50),");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	/**
	 * @description 返回建印章表的SQL语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(CASESEQID).append(" varchar(200),");
		sb.append(FILE_NAME).append(" varchar(150),");
		sb.append(OPR_TIME).append("  datetime default '1970-01-01 08:00:00',");
		sb.append(STATUS).append(" varchar(50),");
		sb.append(SEAL_TYPE).append(" varchar(50),");
		sb.append(DEPT_NO).append(" varchar(50),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(DEPT_NAME).append(" varchar(50),");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	/**
	 * @description 返回建印章表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(CASESEQID).append(" varchar(200),");
		sb.append(FILE_NAME).append(" varchar(150),");
		sb.append(OPR_TIME).append("  timestamp, ");
		sb.append(STATUS).append(" varchar(50),");
		sb.append(SEAL_TYPE).append(" varchar(50),");
		sb.append(DEPT_NO).append(" varchar(50),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(DEPT_NAME).append(" varchar(50),");
		sb.append(")");
		return sb.toString();
	}

	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}

	public static String getUSER_ID() {
		return USER_ID;
	}

	public static void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public static String getCASESEQID() {
		return CASESEQID;
	}

	public static void setCASESEQID(String cASESEQID) {
		CASESEQID = cASESEQID;
	}

	public static String getFILE_NAME() {
		return FILE_NAME;
	}

	public static void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public static String getOPR_TIME() {
		return OPR_TIME;
	}

	public static void setOPR_TIME(String oPR_TIME) {
		OPR_TIME = oPR_TIME;
	}

	public static String getSTATUS() {
		return STATUS;
	}

	public static void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public static String getSEAL_TYPE() {
		return SEAL_TYPE;
	}

	public static void setSEAL_TYPE(String sEAL_TYPE) {
		SEAL_TYPE = sEAL_TYPE;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static void setDEPT_NO(String dEPT_NO) {
		DEPT_NO = dEPT_NO;
	}

	public static String getSEAL_NAME() {
		return SEAL_NAME;
	}

	public static void setSEAL_NAME(String sEAL_NAME) {
		SEAL_NAME = sEAL_NAME;
	}

	public static String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public static void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

}
