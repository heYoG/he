package com.dj.seal.util.table;

public class ModelDeptUtil {
	public static String TABLE_NAME = "T_ModelDept"; // 表名
	
	public static String MODEL_ID = "MODEL_ID";// 模板ID
	public static String DEPT_NO="DEPT_NO";//部门
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(MODEL_ID).append(" int,");
		sb.append(DEPT_NO).append(" varchar(500) ");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(MODEL_ID).append(" int(11),");
		sb.append(DEPT_NO).append(" varchar(255) ");
		sb.append(")");
		System.out.println(sb.toString()+";");
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
	public static String getMODEL_ID() {
		return MODEL_ID;
	}
	public static void setMODEL_ID(String model_id) {
		MODEL_ID = model_id;
	}
	public static String getDEPT_NO() {
		return DEPT_NO;
	}
	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}
	
	

}
