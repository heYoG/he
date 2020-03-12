package com.dj.seal.util.table;

public class HotelTmasterplateJudgeUtil {
	
	public static String TABLE_NAME="T_HotelTmasterplateJudge";//表名
	public static String C_ID="C_ID";// 通过UUID生成
	public static String C_NAME="C_NAME";// 名称
	public static String C_VALUE="C_VALUE";// 值
	public static String MASTER_PLATECID="MASTER_PLATECID";// 模版id
	public static String C_VALUETYPE="C_VALUETYPE";//值对应的类型（1.非空、2.空、3特定值）
//	public static String C_WIDTH="c_width";//内容宽度
//	public static String C_HEIGHT="c_height";//内容高度
//	public static String C_POSITION="c_position";//坐标偏移量
	
	
	
	/**
	 * 返回文档表oracle建表语句
	 * @return
	 */
	public static String createSql4oracle() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(C_ID).append(" int,");
		sb.append(C_NAME).append(" varchar(255),");
		sb.append(C_VALUE).append(" varchar(255),");
		sb.append(MASTER_PLATECID).append(" varchar(255),");
		sb.append(C_VALUETYPE).append(" varchar(255)");
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
		sb.append(C_NAME).append(" varchar(200),");
		sb.append(C_VALUE).append("  varchar(200),");
		sb.append(MASTER_PLATECID).append("  varchar(200),");
		sb.append(C_VALUETYPE).append(" varchar(200) ");
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
	public static String getC_ID() {
		return C_ID;
	}
	public static String getC_NAME() {
		return C_NAME;
	}
	public static String getC_VALUE() {
		return C_VALUE;
	}
	public static String getMASTER_PLATECID() {
		return MASTER_PLATECID;
	}
	public static String getC_VALUETYPE() {
		return C_VALUETYPE;
	}
	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	public static void setC_ID(String c_ID) {
		C_ID = c_ID;
	}
	public static void setC_NAME(String c_NAME) {
		C_NAME = c_NAME;
	}
	public static void setC_VALUE(String c_VALUE) {
		C_VALUE = c_VALUE;
	}
	public static void setMASTER_PLATECID(String mASTER_PLATECID) {
		MASTER_PLATECID = mASTER_PLATECID;
	}
	public static void setC_VALUETYPE(String c_VALUETYPE) {
		C_VALUETYPE = c_VALUETYPE;
	}

//	public static String getC_WIDTH() {
//		return C_WIDTH;
//	}

//	public static void setC_WIDTH(String c_WIDTH) {
//		C_WIDTH = c_WIDTH;
//	}

//	public static String getC_HEIGHT() {
//		return C_HEIGHT;
//	}

//	public static void setC_HEIGHT(String c_HEIGHT) {
//		C_HEIGHT = c_HEIGHT;
//	}

//	public static String getC_POSITION() {
//		return C_POSITION;
//	}
//
//	public static void setC_POSITION(String c_POSITION) {
//		C_POSITION = c_POSITION;
//	}

}
