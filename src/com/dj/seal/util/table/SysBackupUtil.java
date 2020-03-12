package com.dj.seal.util.table;

/**
 * 
 * @description 数据备份
 * @author lzl
 * @since 2013-4-16
 *
 */
public class SysBackupUtil {

	public static String TABLE_NAME 	= "T_BU"; 		// 表名
	public static String ID 			= "B_BC";		// 唯一编号
	public static String STATUS 		= "B_BD"; 		// 状态
	public static String FREQUENCY 		= "B_BE"; 		// 频率
	public static String DATE 			= "B_BF"; 		// 日期
	public static String TIME 			= "B_BG"; 		// 时间

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME);
		sb.append("(").append(ID).append(" number(10,0),");
		sb.append(STATUS).append(" varchar2(2),");
		sb.append(FREQUENCY).append(" varchar2(20),");
		sb.append(DATE).append(" varchar2(20),");
		sb.append(TIME).append(" varchar2(20),");
		sb.append("primary key(").append(ID).append(")").append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME);
		sb.append("(").append(ID).append(" bigint,");
		sb.append(STATUS).append(" varchar(2),");
		sb.append(FREQUENCY).append(" varchar(20),");
		sb.append(DATE).append(" varchar(20),");
		sb.append(TIME).append(" varchar(20),");
		sb.append("primary key(").append(ID).append(")").append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}

	public static String dropSql() {
		return "drop table " + TABLE_NAME;
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

	public static String getSTATUS() {
		return STATUS;
	}

	public static void setSTATUS(String status) {
		STATUS = status;
	}

	public static String getFREQUENCY() {
		return FREQUENCY;
	}

	public static void setFREQUENCY(String frequency) {
		FREQUENCY = frequency;
	}

	public static String getDATE() {
		return DATE;
	}

	public static void setDATE(String date) {
		DATE = date;
	}

	public static String getTIME() {
		return TIME;
	}

	public static void setTIME(String time) {
		TIME = time;
	}


}
