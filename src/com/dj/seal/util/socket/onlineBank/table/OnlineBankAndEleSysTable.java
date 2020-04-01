package com.dj.seal.util.socket.onlineBank.table;

/**
 * 	数据库表字段映射
 * @author Administrator
 *
 */
public class OnlineBankAndEleSysTable {
	public static String TABLE_NAME="ONLINEBANKTABLE";//原始表
	public static String CHANNELNO="CHANNELNO";//渠道号
	public static String ORGCONSUMERSEQNO= "ORGCONSUMERSEQNO";//流水号
	public static String TRANNO="TRANNO";//交易码
	public static String TRANDATE="TRANDATE";//交易日期
	public static String VALCODE="VALCODE";//交易码
	public static String CREATETIME="CREATETIME";//数据保存具体时间
	public static String INFO_PLUS="INFO_PLUS";//附加信息(大数据字段)
	public static String BACKUP1="BACKUP1";//备用字段1
	public static String BACKUP2="BACKUP2";//备用字段2
	public static String BACKUP3="BACKUP3";//备用字段3
	
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	public static String getCHANNELNO() {
		return CHANNELNO;
	}
	public static void setCHANNELNO(String cHANNELNO) {
		CHANNELNO = cHANNELNO;
	}
	public static String getORGCONSUMERSEQNO() {
		return ORGCONSUMERSEQNO;
	}
	public static void setORGCONSUMERSEQNO(String oRGCONSUMERSEQNO) {
		ORGCONSUMERSEQNO = oRGCONSUMERSEQNO;
	}
	public static String getTRANNO() {
		return TRANNO;
	}
	public static void setTRANNO(String tRANNO) {
		TRANNO = tRANNO;
	}
	public static String getTRANDATE() {
		return TRANDATE;
	}
	public static void setTRANDATE(String tRANDATE) {
		TRANDATE = tRANDATE;
	}
	public static String getVALCODE() {
		return VALCODE;
	}
	public static void setVALCODE(String vALCODE) {
		VALCODE = vALCODE;
	}
	public static String getCREATETIME() {
		return CREATETIME;
	}
	public static void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public static String getINFO_PLUS() {
		return INFO_PLUS;
	}
	public static void setINFO_PLUS(String iNFO_PLUS) {
		INFO_PLUS = iNFO_PLUS;
	}
	public static String getBACKUP1() {
		return BACKUP1;
	}
	public static void setBACKUP1(String bACKUP1) {
		BACKUP1 = bACKUP1;
	}
	public static String getBACKUP2() {
		return BACKUP2;
	}
	public static void setBACKUP2(String bACKUP2) {
		BACKUP2 = bACKUP2;
	}
	public static String getBACKUP3() {
		return BACKUP3;
	}
	public static void setBACKUP3(String bACKUP3) {
		BACKUP3 = bACKUP3;
	}
	
	
}
