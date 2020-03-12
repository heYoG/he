package com.dj.seal.util.table;

/**
 * 单据表
 */
public class HotelScannerRecordUtil {
	private static String TABLE_NAME ="t_scannerfile";//部门名称
	private static String ID="id";//唯一编号
	private static String FILENAME="filename";//文档名称
	private static String CREATEUSERID =  "createuserid";//创建ID
	private static String DEPTNO = "deptno";//创建部门
	private static String CREATETIME ="createtime";//创建时间
	private static String FILEPATH="filepath";
	public static String getFILEPATH() {
		return FILEPATH;
	}

	public static void setFILEPATH(String fILEPATH) {
		FILEPATH = fILEPATH;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}

	public static String getID() {
		return ID;
	}

	public static void setID(String iD) {
		ID = iD;
	}

	public static String getFILENAME() {
		return FILENAME;
	}

	public static void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}

	public static String getCREATEUSERID() {
		return CREATEUSERID;
	}

	public static void setCREATEUSERID(String cREATEUSERID) {
		CREATEUSERID = cREATEUSERID;
	}

	public static String getDEPTNO() {
		return DEPTNO;
	}

	public static void setDEPTNO(String dEPTNO) {
		DEPTNO = dEPTNO;
	}

	public static String getCREATETIME() {
		return CREATETIME;
	}

	public static void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}

	public static String getIP() {
		return IP;
	}

	public static void setIP(String iP) {
		IP = iP;
	}

	public static String getCONTEXT() {
		return CONTEXT;
	}

	public static void setCONTEXT(String cONTEXT) {
		CONTEXT = cONTEXT;
	}

	private static String IP="ip";//创建IP
	private static String CONTEXT="context";//OCR识别的文字内容
	/**
	 * @description ORACLE
	 * @return
	 */
//	public static String createSql4Oracle() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("create table ").append(TABLE_NAME).append("(");
//		sb.append(CID).append(" varchar(255),");
//		sb.append(CIP).append(" varchar(100),");
//		sb.append(CCREATETIME).append(
//						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
//		sb.append(CUPLOADTIME).append(
//						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
//		sb.append(MTPLID).append(" varchar(100),");
//		sb.append(CREATEUSERID).append(" varchar(100),");
//		sb.append(UPLOADUSERID).append(" varchar(100),");
//		sb.append(CFILEFILENAME).append(" varchar(255),");
//		sb.append(CDATA).append(" varchar(255),");
//		sb.append(CSTATUS).append(" varchar(100),");
//		sb.append(HASDONE).append(" varchar(100),");
//		sb.append(DEPTNO).append(" varchar(255),");
//		sb.append(AGREEMENTID).append(" varchar(255),");
//		sb.append(HAVEIDCARD).append(" varchar(100),");
//		sb.append(HAVEATTACH).append(" varchar(100),");
//		sb.append(CHECKSTATUS).append(" varchar(100),");
//		sb.append(JIHEUSER).append(" varchar(100),");
//		sb.append(JIHETIME).append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
//		sb.append(JIHEREASON).append(" varchar(255),");
//		sb.append(REMARKS).append(" varchar(255)");
//		sb.append(")");
//		System.out.println(sb.toString());
//		return sb.toString();
//	}

	/**
	 * @description MySql
	 * @return
	 */
	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(ID).append(" varchar(30),");
		sb.append(FILENAME).append(" varchar(30),");
		sb.append(CREATETIME).append(" datetime,");
		sb.append(CREATEUSERID).append(" varchar(100),");
		sb.append(DEPTNO).append(" varchar(255),");
		sb.append(IP).append(" varchar(30),");
		sb.append(CONTEXT).append(" mediumtext,");
		sb.append(FILEPATH).append(" varchar(100)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}


	public static String dropSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("drop table ").append(TABLE_NAME);
		return sb.toString();
	}

	public static void main(String[] args) {
		//createSql4MySql();
	}

}
