package com.dj.seal.util.table;

public class DocmentBodyUtil {

	public static  String TABLE_NAME = "T_DOC"; // 表名
	public static  String DOC_NO = "doc_no";//文档编号，由客户端控件产生
	public static  String DOC_NAME ="doc_name";//文档名称
	public static  String DOC_TYPE ="doc_type";//文档类型
	public static  String DOC_TITLE ="doc_title";//文档标题
	public static  String DOC_CREATOR="doc_creator";//文档创建者
	public static  String CREATE_TIME ="create_time";//文档录入系统时间
	public static  String CREATE_IP="create_ip";//文档录入系统的ip
	public static  String DOC_KEYS="doc_keys";//文档关键字
	public static  String DOC_CONTENT="doc_content";//文档内容
	public static  String DEPT_NO="dept_no";//部门编号
	public static  String DEPT_NAME="dept_name";//部门名
    
	/**
	 * 返回文档表oracle建表语句
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DOC_NO).append(" varchar(200),");
		sb.append(DOC_NAME).append(" varchar(200),");
		sb.append(DOC_TYPE).append(" varchar(30),");
		sb.append(DOC_TITLE).append(" varchar(200),");
		sb.append(DOC_CREATOR).append(" varchar(50),");
		sb.append(CREATE_IP).append(" varchar(20),");
		sb.append(DOC_KEYS).append(" varchar(500),");
		sb.append(DOC_CONTENT).append(" clob,");
		sb.append(DEPT_NO).append(" varchar(500),");
		sb.append(DEPT_NAME).append(" varchar(50),");
		sb.append(CREATE_TIME).append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss')");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	
	/**
	 * 返回文档表mysql建表语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DOC_NO).append(" varchar(200),");
		sb.append(DOC_NAME).append(" varchar(200),");
		sb.append(DOC_TYPE).append(" varchar(30),");
		sb.append(DOC_TITLE).append(" varchar(200),");
		sb.append(DOC_CREATOR).append(" varchar(50),");
		sb.append(CREATE_IP).append(" varchar(20),");
		sb.append(DOC_KEYS).append(" varchar(250),");
		sb.append(DOC_CONTENT).append(" mediumtext,");
		sb.append(DEPT_NO).append(" varchar(250),");
		sb.append(DEPT_NAME).append(" varchar(50),");
		sb.append(CREATE_TIME).append(" datetime default '1970-01-01 08:00:00'");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * 返回文档表db2建表语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DOC_NO).append(" varchar(200),");
		sb.append(DOC_NAME).append(" varchar(200),");
		sb.append(DOC_TYPE).append(" varchar(30),");
		sb.append(DOC_TITLE).append(" varchar(200),");
		sb.append(DOC_CREATOR).append(" varchar(50),");
		sb.append(CREATE_IP).append(" varchar(20),");
		sb.append(DOC_KEYS).append(" varchar(250),");
		sb.append(DOC_CONTENT).append(" clob,");
		sb.append(DEPT_NO).append(" varchar(250),");
		sb.append(DEPT_NAME).append(" varchar(50),");
		sb.append(CREATE_TIME).append(" timestamp");
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

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getDOC_NO() {
		return DOC_NO;
	}

	public static void setDOC_NO(String doc_no) {
		DOC_NO = doc_no;
	}

	public static String getDOC_NAME() {
		return DOC_NAME;
	}

	public static void setDOC_NAME(String doc_name) {
		DOC_NAME = doc_name;
	}

	public static String getDOC_TYPE() {
		return DOC_TYPE;
	}

	public static void setDOC_TYPE(String doc_type) {
		DOC_TYPE = doc_type;
	}

	public static String getDOC_TITLE() {
		return DOC_TITLE;
	}

	public static void setDOC_TITLE(String doc_title) {
		DOC_TITLE = doc_title;
	}

	public static String getDOC_CREATOR() {
		return DOC_CREATOR;
	}

	public static void setDOC_CREATOR(String doc_creator) {
		DOC_CREATOR = doc_creator;
	}

	public static String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public static void setCREATE_TIME(String create_time) {
		CREATE_TIME = create_time;
	}

	public static String getCREATE_IP() {
		return CREATE_IP;
	}

	public static void setCREATE_IP(String create_ip) {
		CREATE_IP = create_ip;
	}

	public static String getDOC_KEYS() {
		return DOC_KEYS;
	}

	public static void setDOC_KEYS(String doc_keys) {
		DOC_KEYS = doc_keys;
	}

	public static String getDOC_CONTENT() {
		return DOC_CONTENT;
	}

	public static void setDOC_CONTENT(String doc_content) {
		DOC_CONTENT = doc_content;
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
	
	
	
}
