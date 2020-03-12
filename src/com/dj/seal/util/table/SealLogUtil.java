package com.dj.seal.util.table;

public class SealLogUtil {
	public static String TABLE_NAME = "T_SEALLOG"; // 表名
	public static String DOC_ID = "DOC_ID"; 
	public static String LOG_TYPE="LOG_TYPE";
	public static String SEAL_ID="SEAL_ID";
	public static String CLIENT_SYSTEM="CLIENT_SYSTEM";
	public static String KEY_SN="KEY_SN";
	public static String KEY_DN="KEY_DN";
	public static String MAC_ADD="MAC_ADD";
	public static String CARD_ID="CARD_ID";
	public static String LOG_VALUE="LOG_VALUE";
	public static String DOC_TITLE="DOC_TITLE";
	public static String IP="IP";
	public static String CREATE_TIME="CREATE_TIME";
	public static String SEAL_NAME="SEAL_NAME";
	public static String DEPT_NO="DEPT_NO";
	public static String DEPT_NAME="DEPT_NAME";
	public static String USER_ID="USER_ID";
	public static String USER_NAME="USER_NAME";
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DOC_ID).append(" varchar(100),");
		sb.append(LOG_TYPE).append(" varchar(5),");
		sb.append(SEAL_ID).append(" int,");
		sb.append(CLIENT_SYSTEM).append(" varchar(50),");
		sb.append(KEY_SN).append(" varchar(255),");
		sb.append(KEY_DN).append(" varchar(255),");
		sb.append(MAC_ADD).append(" varchar(255),");
		sb.append(CARD_ID).append(" varchar(255),");
		sb.append(LOG_VALUE).append(" varchar(255),");
		sb.append(DOC_TITLE).append(" varchar(255),");
		sb.append(IP).append(" varchar(50),");
		sb.append(CREATE_TIME).append("  date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");	
		sb.append(SEAL_NAME).append(" varchar(255),");
		sb.append(DEPT_NO).append(" varchar(255),");
		sb.append(DEPT_NAME).append(" varchar(255),");
		sb.append(USER_ID).append(" varchar(255),");
		sb.append(USER_NAME).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建印章表的SQL语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DOC_ID).append(" varchar(100),");
		sb.append(LOG_TYPE).append(" varchar(5),");
		sb.append(SEAL_ID).append(" int,");
		sb.append(CLIENT_SYSTEM).append(" varchar(50),");
		sb.append(KEY_SN).append(" varchar(255),");
		sb.append(KEY_DN).append(" varchar(255),");
		sb.append(MAC_ADD).append(" varchar(255),");
		sb.append(CARD_ID).append(" varchar(255),");
		sb.append(LOG_VALUE).append(" varchar(255),");
		sb.append(DOC_TITLE).append(" varchar(255),");
		sb.append(IP).append(" varchar(50),");
		sb.append(CREATE_TIME).append("  datetime default '1970-01-01 08:00:00',");		
		sb.append(SEAL_NAME).append(" varchar(255),");
		sb.append(DEPT_NO).append(" varchar(255),");
		sb.append(DEPT_NAME).append(" varchar(255),");
		sb.append(USER_ID).append(" varchar(255),");
		sb.append(USER_NAME).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * @description 返回建印章表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DOC_ID).append(" varchar(100),");
		sb.append(LOG_TYPE).append(" varchar(5),");
		sb.append(SEAL_ID).append(" int,");
		sb.append(CLIENT_SYSTEM).append(" varchar(50),");
		sb.append(KEY_SN).append(" varchar(255),");
		sb.append(KEY_DN).append(" varchar(255),");
		sb.append(MAC_ADD).append(" varchar(255),");
		sb.append(CARD_ID).append(" varchar(255),");
		sb.append(LOG_VALUE).append(" varchar(255),");
		sb.append(DOC_TITLE).append(" varchar(255),");
		sb.append(IP).append(" varchar(50),");
		sb.append(CREATE_TIME).append("  timestamp, ");	
		sb.append(SEAL_NAME).append(" varchar(255),");
		sb.append(DEPT_NO).append(" varchar(255),");
		sb.append(DEPT_NAME).append(" varchar(255),");
		sb.append(USER_ID).append(" varchar(255),");
		sb.append(USER_NAME).append(" varchar(255)");
		sb.append(")");
		return sb.toString();
	}
	/**
	 * @description 返回删除印章表的SQL语句
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

	public static String getDOC_ID() {
		return DOC_ID;
	}

	public static void setDOC_ID(String doc_id) {
		DOC_ID = doc_id;
	}

	public static String getLOG_TYPE() {
		return LOG_TYPE;
	}

	public static void setLOG_TYPE(String log_type) {
		LOG_TYPE = log_type;
	}

	public static String getSEAL_ID() {
		return SEAL_ID;
	}

	public static void setSEAL_ID(String seal_id) {
		SEAL_ID = seal_id;
	}

	public static String getCLIENT_SYSTEM() {
		return CLIENT_SYSTEM;
	}

	public static void setCLIENT_SYSTEM(String client_system) {
		CLIENT_SYSTEM = client_system;
	}

	public static String getKEY_SN() {
		return KEY_SN;
	}

	public static void setKEY_SN(String key_sn) {
		KEY_SN = key_sn;
	}

	public static String getKEY_DN() {
		return KEY_DN;
	}

	public static void setKEY_DN(String key_dn) {
		KEY_DN = key_dn;
	}

	public static String getMAC_ADD() {
		return MAC_ADD;
	}

	public static void setMAC_ADD(String mac_add) {
		MAC_ADD = mac_add;
	}

	public static String getCARD_ID() {
		return CARD_ID;
	}

	public static void setCARD_ID(String card_id) {
		CARD_ID = card_id;
	}

	public static String getLOG_VALUE() {
		return LOG_VALUE;
	}

	public static void setLOG_VALUE(String log_value) {
		LOG_VALUE = log_value;
	}

	public static String getDOC_TITLE() {
		return DOC_TITLE;
	}

	public static void setDOC_TITLE(String doc_title) {
		DOC_TITLE = doc_title;
	}

	public static String getIP() {
		return IP;
	}

	public static void setIP(String ip) {
		IP = ip;
	}

	public static String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public static void setCREATE_TIME(String create_time) {
		CREATE_TIME = create_time;
	}

	public static String getSEAL_NAME() {
		return SEAL_NAME;
	}

	public static void setSEAL_NAME(String seal_name) {
		SEAL_NAME = seal_name;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}

	public static String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public static void setDEPT_NAME(String dept_name) {
		DEPT_NAME = dept_name;
	}

	public static String getUSER_ID() {
		return USER_ID;
	}

	public static void setUSER_ID(String user_id) {
		USER_ID = user_id;
	}

	public static String getUSER_NAME() {
		return USER_NAME;
	}

	public static void setUSER_NAME(String user_name) {
		USER_NAME = user_name;
	}
	
	
	
}
