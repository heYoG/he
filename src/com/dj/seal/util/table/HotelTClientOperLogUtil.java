package com.dj.seal.util.table;

public class HotelTClientOperLogUtil {
	
	public static String TABLE_NAME="T_HotelTClientOperLog";//表名
	public static String OPERUSERID="OPERUSERID";// 用户ID
	public static String COPERTIME ="COPERTIME";// 操作时间
	public static String CIP ="CIP";// IP地址
	public static String CMAC="CMAC";// MAC地址
	public static String OPERTYPE="OPERTYPE";// 操作类型 (1登录，2上传文件)
	public static String OBJID="OBJID";// 对象ID
	public static String OBJNAME="OBJNAME";// 对象名称
	public static String RESULT="RESULT";// 结果 (0成功，1失败)
	public static String CID="CID";// 通过UUID生成
	public static String DEPTNO="DEPTNO";// 部门编号
	
	
	/**
	 * 返回文档表oracle建表语句
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(OPERUSERID).append(" varchar(255),");
		sb.append(COPERTIME).append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(CIP).append(" varchar(255),");
		sb.append(CMAC).append(" varchar(255),");
		sb.append(OPERTYPE).append(" varchar(255),");
		sb.append(OBJID).append(" varchar(255),");
		sb.append(OBJNAME).append(" varchar(255),");
		sb.append(RESULT).append(" varchar(255),");
		sb.append(CID).append(" varchar(255),");
		sb.append(DEPTNO).append(" varchar(255)");		  
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	
	/**
	 * MySql创建语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(OPERUSERID).append(" varchar(200),");
		sb.append(COPERTIME).append("  datetime default '1970-01-01 08:00:00',");
		sb.append(CIP).append("  varchar(200),");
		sb.append(CMAC).append("  varchar(200),");
		sb.append(OPERTYPE).append(" varchar(200), ");
		sb.append(OBJID).append(" varchar(200), ");
		sb.append(OBJNAME).append(" varchar(200), ");
		sb.append(RESULT).append(" varchar(200), ");
		sb.append(CID).append(" varchar(200), ");
		sb.append(DEPTNO).append(" varchar(255)");		  
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



	public static String getOPERUSERID() {
		return OPERUSERID;
	}



	public static String getCOPERTIME() {
		return COPERTIME;
	}



	public static String getCIP() {
		return CIP;
	}



	public static String getCMAC() {
		return CMAC;
	}



	public static String getOPERTYPE() {
		return OPERTYPE;
	}



	public static String getOBJID() {
		return OBJID;
	}



	public static String getOBJNAME() {
		return OBJNAME;
	}



	public static String getRESULT() {
		return RESULT;
	}



	public static String getCID() {
		return CID;
	}



	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}



	public static void setOPERUSERID(String oPERUSERID) {
		OPERUSERID = oPERUSERID;
	}



	public static void setCOPERTIME(String cOPERTIME) {
		COPERTIME = cOPERTIME;
	}



	public static void setCIP(String cIP) {
		CIP = cIP;
	}



	public static void setCMAC(String cMAC) {
		CMAC = cMAC;
	}



	public static void setOPERTYPE(String oPERTYPE) {
		OPERTYPE = oPERTYPE;
	}



	public static void setOBJID(String oBJID) {
		OBJID = oBJID;
	}



	public static void setOBJNAME(String oBJNAME) {
		OBJNAME = oBJNAME;
	}



	public static void setRESULT(String rESULT) {
		RESULT = rESULT;
	}



	public static void setCID(String cID) {
		CID = cID;
	}

	public static String getDEPTNO() {
		return DEPTNO;
	}

	public static void setDEPTNO(String deptno) {
		DEPTNO = deptno;
	}
	
	
	
	
	

}
