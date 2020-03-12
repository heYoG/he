package com.dj.seal.util.table;

public class HotelTDicHotelUtil {
	
	
	public static String TABLE_NAME="T_HotelTDicHotel";//表名
	public static String CID="C_ID";//通过UUID生成
	public static String CNAME="C_NAME";//名称
	public static String CSHOWNAME="C_SHOWNAME";//值
	public static String CDATATYPE="C_DATATYPE";//字符类型(整数、小数、大写金额、日期等)
	public static String SYS="SYS";//是否为系统内置。0内置，1非内置
	
	public static String C_STATUS="c_status";//是否显示 0显示，1隐藏
	public static String C_SORT = "c_sort";//通用字典排序
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(CID).append(" varchar(255),");
		sb.append(CNAME).append(" varchar(255),");
		sb.append(CSHOWNAME).append(" varchar(255),");
		sb.append(CDATATYPE).append(" varchar(255),");
		sb.append(SYS).append(" varchar(255),");
		sb.append(C_STATUS).append(" varchar(10), ");
		sb.append(C_SORT).append(" int");
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
		sb.append(CID).append(" varchar(100),");
		sb.append(CNAME).append(" varchar(200),");
		sb.append(CSHOWNAME).append("  varchar(200),");
		sb.append(CDATATYPE).append("  varchar(200),");
		sb.append(SYS).append(" varchar(200), ");
		sb.append(C_STATUS).append(" varchar(10), ");
		sb.append(C_SORT).append(" int(9)");
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
	
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getCID() {
		return CID;
	}

	public static void setCID(String cID) {
		CID = cID;
	}

	public static String getC_STATUS() {
		return C_STATUS;
	}

	public static void setC_STATUS(String cSTATUS) {
		C_STATUS = cSTATUS;
	}

	public static String getC_SORT() {
		return C_SORT;
	}

	public static void setC_SORT(String cSORT) {
		C_SORT = cSORT;
	}

	public static String getCNAME() {
		return CNAME;
	}

	public static void setCNAME(String cNAME) {
		CNAME = cNAME;
	}

	public static String getCSHOWNAME() {
		return CSHOWNAME;
	}

	public static void setCSHOWNAME(String cSHOWNAME) {
		CSHOWNAME = cSHOWNAME;
	}

	public static String getCDATATYPE() {
		return CDATATYPE;
	}

	public static void setCDATATYPE(String cDATATYPE) {
		CDATATYPE = cDATATYPE;
	}

	public static String getSYS() {
		return SYS;
	}

	public static void setSYS(String sYS) {
		SYS = sYS;
	}

	public static void setTABLE_NAME(String tABLENAME) {
		TABLE_NAME = tABLENAME;
	}
	
}
