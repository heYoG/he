package com.dj.seal.util.table;

public class ModelFileRuleUtil {
	
	public static String TABLE_NAME="t_hotelmodelfilerule";//建表名称
	public static String MID = "id";//模板规则ID
	
	public static String MODEL_ID = "modelid";//对应的模板编号
	public static String RULE_NO = "ruleno";//对应的规则编号
	
	
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static void setTABLE_NAME(String tABLENAME) {
		TABLE_NAME = tABLENAME;
	}
	public static String getMID() {
		return MID;
	}
	public static void setMID(String mID) {
		MID = mID;
	}

	
	public static String getMODEL_ID() {
		return MODEL_ID;
	}
	public static void setMODEL_ID(String mODELID) {
		MODEL_ID = mODELID;
	}
	public static String getRULE_NO() {
		return RULE_NO;
	}
	public static void setRULE_NO(String rULENO) {
		RULE_NO = rULENO;
	}
	
	/**
	 * @description MySql
	 * @return
	 */
	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(MID).append(" varchar(100) ,");
		sb.append(MODEL_ID).append(" varchar(100), ");
		sb.append(RULE_NO).append(" varchar(255) ");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	
	/**
	 * 返回oracle建表的SQL语句
	 * 
	 * @return
	 */
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(MID).append(" varchar(255),");
		sb.append(MODEL_ID).append(" varchar(100),");
		sb.append(RULE_NO).append(" varchar(255) ");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}
	
	/**
	 * @description 返回删除市民表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}
	

}
