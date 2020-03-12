package com.dj.seal.util.table;

public class GaiZhangRuleUtil {
	public static String TABLE_NAME = "T_JC"; // 表名
	public static String RULE_NO = "C_JCA";// 规则编号
	public static String RULE_NAME = "C_JCB";// 规则名称
	public static String SEAL_ID = "C_JCC";// 印章编号
	public static String USE_CERT = "C_JCD";// 是否使用证书
	public static String CERT_NO = "C_JCE";// 证书编号
	public static String RULE_TYPE = "C_JCF";// 规则类型（七大类）
	public static String ARG_DESC = "C_JCG";// 参数描述
    public static String RULE_STATE="C_STATE";//盖章规则状态 1 正常 2 注销
	public static String VERSION_NO="C_VERSION";//版本号
    /**
	 * 返回oracle建表的SQL语句
	 * 
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(RULE_NO).append(" varchar(255),");
		sb.append(RULE_NAME).append(" varchar(255),");
		sb.append(SEAL_ID).append(" varchar(255),");
		sb.append(USE_CERT).append(" int,");
		sb.append(CERT_NO).append(" varchar(255),");
		sb.append(RULE_TYPE).append(" int,");
		sb.append(ARG_DESC).append(" varchar(255),");
		sb.append(RULE_STATE).append(" varchar(255),");
		sb.append(VERSION_NO).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建市民表的SQL语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(RULE_NO).append(" varchar(255),");
		sb.append(RULE_NAME).append(" varchar(255),");
		sb.append(SEAL_ID).append(" varchar(255),");
		sb.append(USE_CERT).append(" int(11),");
		sb.append(CERT_NO).append(" varchar(255),");
		sb.append(RULE_TYPE).append(" int(11),");
		sb.append(ARG_DESC).append(" varchar(255),");
		sb.append(RULE_STATE).append(" varchar(255),");
		sb.append(VERSION_NO).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回删除市民表的SQL语句
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

	public static String getRULE_NO() {
		return RULE_NO;
	}

	public static void setRULE_NO(String rule_no) {
		RULE_NO = rule_no;
	}

	public static String getSEAL_ID() {
		return SEAL_ID;
	}

	public static void setSEAL_ID(String seal_id) {
		SEAL_ID = seal_id;
	}

	public static String getCERT_NO() {
		return CERT_NO;
	}

	public static void setCERT_NO(String cert_no) {
		CERT_NO = cert_no;
	}

	public static String getUSE_CERT() {
		return USE_CERT;
	}

	public static void setUSE_CERT(String use_cert) {
		USE_CERT = use_cert;
	}

	public static String getRULE_TYPE() {
		return RULE_TYPE;
	}

	public static void setRULE_TYPE(String rule_type) {
		RULE_TYPE = rule_type;
	}

	public static String getARG_DESC() {
		return ARG_DESC;
	}

	public static void setARG_DESC(String arg_desc) {
		ARG_DESC = arg_desc;
	}

	public static String getRULE_NAME() {
		return RULE_NAME;
	}

	public static void setRULE_NAME(String rule_name) {
		RULE_NAME = rule_name;
	}

	public static String getRULE_STATE() {
		return RULE_STATE;
	}

	public static void setRULE_STATE(String rule_state) {
		RULE_STATE = rule_state;
	}

	public static String getVERSION_NO() {
		return VERSION_NO;
	}

	public static void setVERSION_NO(String version_no) {
		VERSION_NO = version_no;
	}
	
}
