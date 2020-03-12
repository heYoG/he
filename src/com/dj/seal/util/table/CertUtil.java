package com.dj.seal.util.table;

public class CertUtil {
	public static String TABLE_NAME = "T_KA"; // 表名
	public static String CERT_NO = "C_KAA";// 证书号
	public static String CERT_NAME = "C_KAB";// 证书别名
	public static String CERT_DN = "C_KAC";// 证书DN项
	public static String CERT_USER="cert_user";//证书使用者（证书详细信息）
	public static String CERT_ISSUE="cert_issue";//证书颁发机构（证书详细信息）
	public static String CERT_SRC = "C_KAD";// 证书来源:客户端、服务器文件证书、签名服务器
	public static String CERT_DETAIL = "C_KAE";// 证书详细：路径或别名
	public static String CERT_PSD = "C_KAF";// 证书密码
	public static String CERT_TYPE = "C_KAG";// 证书类型：个人证书、公证书
	public static String CERT_STATUS = "C_KAH";// 证书状态
	public static String BEGIN_TIME = "C_KAI";// 证书有效期起始
	public static String END_TIME = "C_KAJ";// 证书有效期结束
	public static String DEPT_NO = "C_KAK";// 证书所属部门
	public static String REG_USER = "C_KAL";// 登记人
	public static String REG_TIME = "C_KAM";// 登记时间
    public static String FILE_CONTENT="C_DATA";//证书数据
    public static String PFX_CONTENT="PFX_CONTENT";
    
	

	public static String getFILE_CONTENT() {
		return FILE_CONTENT;
	}

	public static void setFILE_CONTENT(String file_content) {
		FILE_CONTENT = file_content;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}

	public static String getREG_USER() {
		return REG_USER;
	}

	public static void setREG_USER(String reg_user) {
		REG_USER = reg_user;
	}

	public static String getREG_TIME() {
		return REG_TIME;
	}

	public static void setREG_TIME(String reg_time) {
		REG_TIME = reg_time;
	}

	/**
	 * 返回oracle建表的SQL语句
	 * 
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(CERT_NO).append(" varchar(255),");
		sb.append(CERT_NAME).append(" varchar(255),");
		sb.append(CERT_DN).append(" varchar(255),");
		sb.append(CERT_USER).append(" varchar(255),");
		sb.append(CERT_ISSUE).append(" varchar(255),");
		sb.append(CERT_SRC).append(" varchar(255),");
		sb.append(CERT_DETAIL).append(" varchar(255),");
		sb.append(CERT_PSD).append(" varchar(255),");
		sb.append(CERT_TYPE).append(" varchar(255),");
		sb.append(CERT_STATUS).append(" varchar(255),");
		sb
				.append(BEGIN_TIME)
				.append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb
				.append(END_TIME)
				.append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(DEPT_NO).append(" varchar(255),");
		sb.append(REG_USER).append(" varchar(255),");
		sb.append(FILE_CONTENT).append(" clob,");
		sb.append(PFX_CONTENT).append(" clob,");
		sb
				.append(REG_TIME)
				.append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss')");
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
		sb.append(CERT_NO).append(" varchar(255),");
		sb.append(CERT_NAME).append(" varchar(255),");
		sb.append(CERT_DN).append(" varchar(255),");
		sb.append(CERT_USER).append(" varchar(255),");
		sb.append(CERT_ISSUE).append(" varchar(255),");
		sb.append(CERT_SRC).append(" varchar(255),");
		sb.append(CERT_DETAIL).append(" varchar(255),");
		sb.append(CERT_PSD).append(" varchar(255),");
		sb.append(CERT_TYPE).append(" varchar(255),");
		sb.append(CERT_STATUS).append(" varchar(255),");
		sb.append(BEGIN_TIME)
				.append(" datetime default '1970-01-01 08:00:00',");
		sb.append(END_TIME).append(" datetime default '1970-01-01 08:00:00',");
		sb.append(DEPT_NO).append(" varchar(255),");
		sb.append(REG_USER).append(" varchar(255),");
		sb.append(FILE_CONTENT).append(" mediumText,");
		sb.append(PFX_CONTENT).append(" mediumText,");
		sb.append(REG_TIME).append(" datetime default '1970-01-01 08:00:00'");	
		sb.append(")");
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

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getCERT_NO() {
		return CERT_NO;
	}

	public static void setCERT_NO(String cert_no) {
		CERT_NO = cert_no;
	}

	public static String getCERT_NAME() {
		return CERT_NAME;
	}

	public static void setCERT_NAME(String cert_name) {
		CERT_NAME = cert_name;
	}

	public static String getCERT_DN() {
		return CERT_DN;
	}

	public static void setCERT_DN(String cert_dn) {
		CERT_DN = cert_dn;
	}

	public static String getCERT_SRC() {
		return CERT_SRC;
	}

	public static void setCERT_SRC(String cert_src) {
		CERT_SRC = cert_src;
	}

	public static String getCERT_DETAIL() {
		return CERT_DETAIL;
	}

	public static void setCERT_DETAIL(String cert_detail) {
		CERT_DETAIL = cert_detail;
	}

	public static String getCERT_PSD() {
		return CERT_PSD;
	}

	public static void setCERT_PSD(String cert_psd) {
		CERT_PSD = cert_psd;
	}

	public static String getCERT_TYPE() {
		return CERT_TYPE;
	}

	public static void setCERT_TYPE(String cert_type) {
		CERT_TYPE = cert_type;
	}

	public static String getCERT_STATUS() {
		return CERT_STATUS;
	}

	public static void setCERT_STATUS(String cert_status) {
		CERT_STATUS = cert_status;
	}

	public static String getBEGIN_TIME() {
		return BEGIN_TIME;
	}

	public static void setBEGIN_TIME(String begin_time) {
		BEGIN_TIME = begin_time;
	}

	public static String getEND_TIME() {
		return END_TIME;
	}

	public static void setEND_TIME(String end_time) {
		END_TIME = end_time;
	}

	public static String getCERT_USER() {
		return CERT_USER;
	}

	public static void setCERT_USER(String cert_user) {
		CERT_USER = cert_user;
	}

	public static String getCERT_ISSUE() {
		return CERT_ISSUE;
	}

	public static void setCERT_ISSUE(String cert_issue) {
		CERT_ISSUE = cert_issue;
	}

	public static String getPFX_CONTENT() {
		return PFX_CONTENT;
	}

	public static void setPFX_CONTENT(String pfx_content) {
		PFX_CONTENT = pfx_content;
	}
	
}
