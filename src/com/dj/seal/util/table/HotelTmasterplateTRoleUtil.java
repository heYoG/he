package com.dj.seal.util.table;

/**
 * 模版角色中间表
 */
public class HotelTmasterplateTRoleUtil {

	public static String TABLE_NAME = "T_HOTELMASTERPLATEROLE";
	public static String ID = "ID";
	public static String MATERCID="MATERCID";//模板ID
	public static String ROLEID = "ROLEID";//角色ID
	
	public static String createSql4Oracle(){
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(ID).append(" varchar(100),");
		sb.append(MATERCID).append(" varchar(100),");
		sb.append(ROLEID).append(" varchar(100)");
		sb.append(" )");
		sb.append("");
		return sb.toString();
	}
	public static String createSql4MySql(){
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(ID).append(" varchar(100),");
		sb.append(MATERCID).append(" varchar(100),");
		sb.append(ROLEID).append(" varchar(100)");
		sb.append(" )");
		sb.append("");
		return sb.toString();
	}
	public static String dropSql(){
		StringBuffer sb = new StringBuffer();
		sb.append("drop table ").append(TABLE_NAME);
		return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(createSql4Oracle());
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}
	public static String getID() {
		return ID;
	}
	public static void setID(String id) {
		ID = id;
	}
	public static String getMATERCID() {
		return MATERCID;
	}
	public static void setMATERCID(String matercid) {
		MATERCID = matercid;
	}
	public static String getROLEID() {
		return ROLEID;
	}
	public static void setROLEID(String roleid) {
		ROLEID = roleid;
	}
	
	
}
