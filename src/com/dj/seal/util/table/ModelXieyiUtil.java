package com.dj.seal.util.table;

public class ModelXieyiUtil {
	public static String TABLE_NAME = "T_ModelXieyi"; // 表名
	
	public static String MODEL_ID = "MODEL_ID";// 模板ID
	public static String XIEYI_ID = "xieyi_id";// 模板ID
	public static String XIEYI_DEPT_NO="xieyi_dept_no";//部门
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(MODEL_ID).append(" int,");
		sb.append(XIEYI_ID).append(" int,");
		sb.append(XIEYI_DEPT_NO).append(" varchar(500) ");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(MODEL_ID).append(" int(11),");
		sb.append(XIEYI_ID).append(" int,");
		sb.append(XIEYI_DEPT_NO).append(" varchar(255) ");
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

	public static String getXIEYI_ID() {
		return XIEYI_ID;
	}

	public static void setXIEYI_ID(String xieyi_id) {
		XIEYI_ID = xieyi_id;
	}

	public static String getXIEYI_DEPT_NO() {
		return XIEYI_DEPT_NO;
	}

	public static void setXIEYI_DEPT_NO(String xieyi_dept_no) {
		XIEYI_DEPT_NO = xieyi_dept_no;
	}

	
	

}
