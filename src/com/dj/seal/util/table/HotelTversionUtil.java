package com.dj.seal.util.table;

/**
 * 模版版本号表
 * 
 */
public class HotelTversionUtil {

	public static String TABLE_NAME = "T_HOTELVERSION";
	public static String CID = "CID";
	public static String CVERSIONNO = "CVERSIONNO";
	public static String CCREATETIME = "CCREATETIME";
	public static String EDITHTMLCODE = "EDITHTMLCODE";
	public static String PRINTHTMLCODE = "PRINTHTMLCODE";
	public static String MASTERPLATEID = "MASTERPLATEID";

	public static String createSql4Oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(CID).append(" varchar(100),");
		sb.append(CVERSIONNO).append(" varchar(100),");
		sb
				.append(CCREATETIME)
				.append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(EDITHTMLCODE).append(" varchar(255),");
		sb.append(PRINTHTMLCODE).append(" varchar(255),");
		sb.append(MASTERPLATEID).append(" varchar(255)");
		sb.append(")");
		return sb.toString();

	}

	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(CID).append(" varchar(100),");
		sb.append(CVERSIONNO).append(" varchar(100),");
		sb.append(CCREATETIME).append(" datetime,");
		sb.append(EDITHTMLCODE).append(" varchar(255),");
		sb.append(PRINTHTMLCODE).append(" varchar(255),");
		sb.append(MASTERPLATEID).append(" varchar(255)");
		sb.append(")");
		return sb.toString();
	}

	public static String dropSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("drop table ").append(TABLE_NAME);
		return sb.toString();
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getCID() {
		return CID;
	}

	public static void setCID(String cid) {
		CID = cid;
	}

	public static String getCVERSIONNO() {
		return CVERSIONNO;
	}

	public static void setCVERSIONNO(String cversionno) {
		CVERSIONNO = cversionno;
	}

	public static String getCCREATETIME() {
		return CCREATETIME;
	}

	public static void setCCREATETIME(String ccreatetime) {
		CCREATETIME = ccreatetime;
	}

	public static String getEDITHTMLCODE() {
		return EDITHTMLCODE;
	}

	public static void setEDITHTMLCODE(String edithtmlcode) {
		EDITHTMLCODE = edithtmlcode;
	}

	public static String getPRINTHTMLCODE() {
		return PRINTHTMLCODE;
	}

	public static void setPRINTHTMLCODE(String printhtmlcode) {
		PRINTHTMLCODE = printhtmlcode;
	}

	public static String getMASTERPLATEID() {
		return MASTERPLATEID;
	}

	public static void setMASTERPLATEID(String masterplateid) {
		MASTERPLATEID = masterplateid;
	}

}
