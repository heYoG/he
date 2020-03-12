/**    
 * @{#} HotelRecordRoomMergeUtil.java Create on 2015-7-19 下午09:55:55    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.util.table;

/**    
 * 房间单据合并表
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class HotelRecordRoomMergeUtil {
	public static String TABLE_NAME = "t_hotelrecordroommerge";
	public static String CID = "cid"; // 通过UUID生成
	public static String GUESTNAME = "guestname"; // 客户名称
	public static String ROOMNO = "roomno"; // 房间号
	public static String MERGEDATE = "mergedate"; // 合并日期
	public static String FILENAME = "filename"; // 合并文件名
	public static String SAVEFILENAME = "savefilename"; // 合并文件相对路径
	public static String CREATETIME = "createtime"; // 合并文件创建时间
	
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static void setTABLE_NAME(String tABLENAME) {
		TABLE_NAME = tABLENAME;
	}
	public static String getCID() {
		return CID;
	}
	public static void setCID(String cID) {
		CID = cID;
	}
	public static String getGUESTNAME() {
		return GUESTNAME;
	}
	public static void setGUESTNAME(String gUESTNAME) {
		GUESTNAME = gUESTNAME;
	}
	public static String getROOMNO() {
		return ROOMNO;
	}
	public static void setROOMNO(String rOOMNO) {
		ROOMNO = rOOMNO;
	}
	public static String getMERGEDATE() {
		return MERGEDATE;
	}
	public static void setMERGEDATE(String mERGEDATE) {
		MERGEDATE = mERGEDATE;
	}
	public static String getFILENAME() {
		return FILENAME;
	}
	public static void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public static String getSAVEFILENAME() {
		return SAVEFILENAME;
	}
	public static void setSAVEFILENAME(String sAVEFILENAME) {
		SAVEFILENAME = sAVEFILENAME;
	}
	public static String getCREATETIME() {
		return CREATETIME;
	}
	public static void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	
	/**
	 * @description MySql
	 * @return
	 */
	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(CID).append(" varchar(255),");
		sb.append(GUESTNAME).append(" varchar(255),");
		sb.append(ROOMNO).append(" varchar(255),");
		sb.append(MERGEDATE).append(" varchar(10),");
		sb.append(FILENAME).append(" varchar(255),");
		sb.append(SAVEFILENAME).append(" varchar(255),");
		sb.append(CREATETIME).append(" datetime");
		sb.append(")");
		return sb.toString();
	}
	
	public static String dropSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("drop table ").append(TABLE_NAME);
		return sb.toString();
	}
}

