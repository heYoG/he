package com.dj.seal.util.table;

/**
 * 
 * @description 服务端盖章操作日志表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class ServerSealLogUtil {

	public static String TABLE_NAME = "T_SerLog"; // 表名
	public static String USER_ID = "C_SAB"; // 用户名
	public static String CASESEQID = "C_SAH"; // 流水号
	public static String SERVER_ID = "C_SAC";// 应用系统id
	public static String OPR_TIME = "C_SAD"; // 操作时间
	public static String OPR_IP = "C_SAE"; // 操作IP
	public static String SEAL_STATUS = "C_SAF";// 盖章状态,0成功；1失败
	public static String FILE_NAME = "C_SAG";// 文档名称
	public static String OPR_MSG = "C_MSG";// 盖章描述信息
	public static String SEAL_NAME = "SEAL_NAME";// 印章名称
	public static String SEAL_TYPE = "SEAL_TYPE";// 印章类型
	public static String DEPT_NO = "DEPT_NO";// 部门编号
	public static String DEPT_NAME = "DEPT_NAME";// 部门名称

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(CASESEQID).append(" varchar(200),");
		sb.append(SERVER_ID).append(" varchar(50),");
		sb.append(OPR_TIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(OPR_IP).append(" varchar(50),");
		sb.append(SEAL_STATUS).append(" varchar(50),");
		sb.append(FILE_NAME).append(" varchar(150),");
		sb.append(OPR_MSG).append(" varchar(200)");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(SEAL_TYPE).append(" varchar(50),");
		sb.append(DEPT_NO).append(" varchar(50),");
		sb.append(DEPT_NAME).append(" varchar(50)");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	/**
	 * @description 返回建系统日志表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + USER_ID
				+ " varchar(50) not null," + CASESEQID + " varchar(200),"
				+ SERVER_ID + " varchar(50) not null," + OPR_TIME
				+ " datetime," + OPR_IP + " varchar(50)," + SEAL_STATUS
				+ " varchar(50)," + FILE_NAME + " varchar(150)," + SEAL_NAME
				+ " varchar(200)," + SEAL_TYPE + " varchar(50)," + DEPT_NO
				+ " varchar(50)," + DEPT_NAME + " varchar(50)," + OPR_MSG
				+ " varchar(200)" + ")";
		System.out.println(create_sql + ";");
		return create_sql;
	}

	/**
	 * @description 返回建系统日志表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(CASESEQID).append(" varchar(200),");
		sb.append(SERVER_ID).append(" varchar(50),");
		sb.append(OPR_TIME).append(" timestamp,");
		sb.append(OPR_IP).append(" varchar(50),");
		sb.append(SEAL_STATUS).append(" varchar(50),");
		sb.append(FILE_NAME).append(" varchar(150),");
		sb.append(OPR_MSG).append(" varchar(200)");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(SEAL_TYPE).append(" varchar(50),");
		sb.append(DEPT_NO).append(" varchar(50),");
		sb.append(DEPT_NAME).append(" varchar(50)");
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

	public static String getCASESEQID() {
		return CASESEQID;
	}

	public static void setCASESEQID(String caseSeqID) {
		CASESEQID = caseSeqID;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getSERVER_ID() {
		return SERVER_ID;
	}

	public static void setSERVER_ID(String server_id) {
		SERVER_ID = server_id;
	}

	public static String getSEAL_STATUS() {
		return SEAL_STATUS;
	}

	public static void setSEAL_STATUS(String seal_status) {
		SEAL_STATUS = seal_status;
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

	public static String getFILE_NAME() {
		return FILE_NAME;
	}

	public static void setFILE_NAME(String file_name) {
		FILE_NAME = file_name;
	}

	public static String getOPR_MSG() {
		return OPR_MSG;
	}

	public static void setOPR_MSG(String opr_msg) {
		OPR_MSG = opr_msg;
	}

	public static String getSEAL_NAME() {
		return SEAL_NAME;
	}

	public static void setSEAL_NAME(String sEAL_NAME) {
		SEAL_NAME = sEAL_NAME;
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

	public static String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public static void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

}
