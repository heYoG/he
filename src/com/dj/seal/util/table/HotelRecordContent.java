package com.dj.seal.util.table;
/**
 * 单据附属表
 */
public class HotelRecordContent {

	public static String TABLE_NAME = "T_HOTELRECORDCONTENT";
	public static String RID="RID";
	public static String CONTENT = "CONTENT";
	
	public static String createSql4Oracle(){
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(RID).append(" varchar(255),");
		sb.append(CONTENT).append(" text");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	public static String createSql4MySql(){
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(RID).append(" varchar(255),");
		sb.append(CONTENT).append(" text");
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
	public static String getRID() {
		return RID;
	}
	public static void setRID(String rID) {
		RID = rID;
	}
	public static String getCONTENT() {
		return CONTENT;
	}
	public static void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	
	
}
