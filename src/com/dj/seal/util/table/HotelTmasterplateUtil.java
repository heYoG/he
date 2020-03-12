package com.dj.seal.util.table;

public class HotelTmasterplateUtil {
	
	public static String TABLE_NAME="T_HotelTmasterplate";//表名
	public static String C_ID="C_ID";// UUID
	public static String AIP_NAME="AIP_NAME";// 模板名称
	public static String AIP_DATA="AIP_DATA";// Aip模版base64
	public static String FIEL_DATA="FIEL_DATA";// Aip模版域base64
	public static String MULTIPART="MULTIPART";// 是否套打
	public static String CREATE_TIME="CREATE_TIME";// 创建时间
	public static String PRINTOREDIT="PRINTOREDIT";//模版用户打印或者编辑，也可都用于（1 全包含，2打印，3编辑）
	
	/**
	 * 返回文档表oracle建表语句
	 * @return
	 */
	public static String createSql4oracle() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(C_ID).append(" varchar(100),");
		sb.append(AIP_NAME).append(" varchar(255),");
		sb.append(AIP_DATA).append(" varchar(255),");
		sb.append(FIEL_DATA).append(" varchar(255),");
		sb.append(MULTIPART).append(" varchar(255),");
		sb.append(CREATE_TIME).append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(PRINTOREDIT).append(" varchar(255)");  
		sb.append(")");
		
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	
	/**
	 * MySql创建表语句
	 * @return
	 */
	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(C_ID).append(" varchar(100),");
		sb.append(AIP_NAME).append(" varchar(200),");
		sb.append(AIP_DATA).append("  varchar(200),");
		sb.append(FIEL_DATA).append("  varchar(200),");
		sb.append(MULTIPART).append("  varchar(200),");
		sb.append(CREATE_TIME).append("  datetime default '1970-01-01 08:00:00',");
		sb.append(PRINTOREDIT).append(" varchar(200) ");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	
	
	/**
	 * @description 返回删除印模表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}
	
	
	public static String getC_ID() {
		return C_ID;
	}
	public static String getAIP_NAME() {
		return AIP_NAME;
	}
	public static String getAIP_DATA() {
		return AIP_DATA;
	}
	public static String getFIEL_DATA() {
		return FIEL_DATA;
	}
	public static String getMULTIPART() {
		return MULTIPART;
	}
	public static String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public static String getPRINTOREDIT() {
		return PRINTOREDIT;
	}
	public static void setC_ID(String c_ID) {
		C_ID = c_ID;
	}
	public static void setAIP_NAME(String aIP_NAME) {
		AIP_NAME = aIP_NAME;
	}
	public static void setAIP_DATA(String aIP_DATA) {
		AIP_DATA = aIP_DATA;
	}
	public static void setFIEL_DATA(String fIEL_DATA) {
		FIEL_DATA = fIEL_DATA;
	}
	public static void setMULTIPART(String mULTIPART) {
		MULTIPART = mULTIPART;
	}
	public static void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public static void setPRINTOREDIT(String pRINTOREDIT) {
		PRINTOREDIT = pRINTOREDIT;
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}


	

}
