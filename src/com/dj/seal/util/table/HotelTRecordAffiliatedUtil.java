package com.dj.seal.util.table;
/**
 * 单据附属表
 */
public class HotelTRecordAffiliatedUtil {

	public static String TABLE_NAME = "T_HOTELTRECORDAFFILIATED";
	public static String CNAME="CNAME";
	public static String CVALUE = "CVALUE";
	public static String RECORDID = "RECORDID";
	
	public static String createSql4Oracle(){
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(CNAME).append(" varchar(255),");
		sb.append(CVALUE).append(" varchar(4000),");
		sb.append(RECORDID).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	public static String createSql4MySql(){
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(CNAME).append(" varchar(255),");
		sb.append(CVALUE).append(" text,");
		sb.append(RECORDID).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	public static String dropSql(){
		StringBuffer sb = new StringBuffer();
		sb.append(" drop table ").append(TABLE_NAME);
		return sb.toString();
	}
	
	
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}
	public static String getCNAME() {
		return CNAME;
	}
	public static void setCNAME(String cname) {
		CNAME = cname;
	}
	public static String getCVALUE() {
		return CVALUE;
	}
	public static void setCVALUE(String cvalue) {
		CVALUE = cvalue;
	}
	public static String getRECORDID() {
		return RECORDID;
	}
	public static void setRECORDID(String recordid) {
		RECORDID = recordid;
	}
	
}
