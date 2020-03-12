package com.dj.seal.util.table;

public class CZSealLogUtil {
	public static String TABLE_NAME = "T_CZSEALLOG"; // 表名
	public static String ID = "ID"; // 日志ID
	public static String SEAL_CZID = "SEAL_CZID"; // 印章的财政id
	public static String SEAL_NAME = "SEAL_NAME"; // 印章名称
	public static String OPER_TYPE = "OPER_TYPE"; // 操作类型
	public static String OPER_TIME = "OPER_TIME"; // 操作时间
	public static String CERT_INFO = "CERT_INFO"; // 证书信息

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(ID).append(" int,");
		sb.append(SEAL_CZID).append(" varchar(200),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(OPER_TYPE).append(" varchar(5),");
		sb.append(OPER_TIME)
		.append(
				" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(CERT_INFO).append(" varchar(250)");
		sb.append(")");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * @description 返回建系统日志表的SQL语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(ID).append(" int ,");
		sb.append(SEAL_CZID).append(" varchar(200),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(OPER_TYPE).append(" varchar(5),");
		sb.append(OPER_TIME)
		.append(
				" datetime default '1970-01-01 08:00:00',");
		sb.append(CERT_INFO).append(" varchar(250)");
		sb.append(")");
		System.out.println(sb.toString());
		return sb.toString();
	}
	/**
	 * @description 返回建系统日志表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(ID).append(
				" int GENERATED ALWAYS AS IDENTITY primary key,");
		sb.append(SEAL_CZID).append(" varchar(200),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(OPER_TYPE).append(" varchar(5),");
		sb.append(OPER_TIME).append(" timestamp,");
		sb.append(CERT_INFO).append(" varchar(255)");
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

	public static String getID() {
		return ID;
	}

	public static void setID(String id) {
		ID = id;
	}

	public static String getSEAL_CZID() {
		return SEAL_CZID;
	}

	public static void setSEAL_CZID(String seal_czid) {
		SEAL_CZID = seal_czid;
	}

	public static String getSEAL_NAME() {
		return SEAL_NAME;
	}

	public static void setSEAL_NAME(String seal_name) {
		SEAL_NAME = seal_name;
	}
	public static String getOPER_TYPE() {
		return OPER_TYPE;
	}

	public static void setOPER_TYPE(String oper_type) {
		OPER_TYPE = oper_type;
	}

	public static String getOPER_TIME() {
		return OPER_TIME;
	}

	public static void setOPER_TIME(String oper_time) {
		OPER_TIME = oper_time;
	}

	public static String getCERT_INFO() {
		return CERT_INFO;
	}

	public static void setCERT_INFO(String cert_info) {
		CERT_INFO = cert_info;
	}

}
