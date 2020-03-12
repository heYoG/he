package com.dj.seal.util.table;

public class DocPrintRoleUserUtil {
	
	public static String TABLE_NAME = "T_DocPrintRoleUser"; // 表名 用户印章表
	public static String DOC_NO = "doc_no"; // 文档编号
	public static String TYPE = "type"; // 分配类型 角色还是用户
	public static String USER_ID = "user_id"; // 用户id
	public static String PRINTNUM = "printnum"; // 总打印份数
	public static String CURRNUM = "currnum"; // 当前已打印份数
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(DOC_NO).append(" varchar(200),");
		sb.append(TYPE).append(" int,");
		sb.append(USER_ID).append(" varchar(30),");
		sb.append(PRINTNUM).append(" int,");
		sb.append(CURRNUM).append(" int");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	/**
	 * @description 返回建用户印章表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table "
				+ TABLE_NAME
				+ " ("
				+ DOC_NO
				+ " varchar(200),"
				+TYPE
				+" int,"
				+ USER_ID
				+ " varchar(30),"// //foreign key references
				+PRINTNUM
				+" int,"
				+CURRNUM
				+" int"
				+ ")";
		System.out.println(create_sql+";");
		return create_sql;
	}
	/**
	 * @description 返回删除用户印章表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	
	public static String getDOC_NO() {
		return DOC_NO;
	}
	public static void setDOC_NO(String doc_no) {
		DOC_NO = doc_no;
	}
	public static void setUSER_ID(String user_id) {
		USER_ID = user_id;
	}
	public static String getUSER_ID() {
		return USER_ID;
	}
	public static String getTYPE() {
		return TYPE;
	}
	public static void setTYPE(String type) {
		TYPE = type;
	}
	public static String getPRINTNUM() {
		return PRINTNUM;
	}
	public static void setPRINTNUM(String printnum) {
		PRINTNUM = printnum;
	}
	public static String getCURRNUM() {
		return CURRNUM;
	}
	public static void setCURRNUM(String currnum) {
		CURRNUM = currnum;
	}
	
}
