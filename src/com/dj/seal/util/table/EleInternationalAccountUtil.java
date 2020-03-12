package com.dj.seal.util.table;

/*电子对账、国际结算表*/
public class EleInternationalAccountUtil {
	public static String TABLE_NAME="T_EleInternationalAccount";//表名
	public static String CHANNELNO="CHANNELNO";//渠道号
	public static String ORGCONSUMERSEQNO="ORGCONSUMERSEQNO";//交易流水号
	public static String TRANNO="TRANNO";//交易码
	public static String TRANDATE="TRANDATE";//交易日期
	public static String VALCODE="VALCODE";//验证码
	public static String OPERATOR="OPERATOR";//交易操作人名字
	public static String TRANORG="TRANORG";//交易机构号
	public static String TRANORGNAME="TRANORGNAME";//交易机构名称
	public static String TRANNAME="TRANNAME";//交易名称
	public static String SEALPICTUREDATA="SEALPICTUREDATA";//印章图片数据
	public static String CREATETIME="CREATETIME";//创建时间
	/*移动银行、微信银行*/
	public static String ACCOUNTNUMBER="ACCOUNTNUMBER";//账户账号
	public static String ACCOUNTNAME="ACCOUNTNAME";//账户名称
	public static String STARTDATE="STARTDATE";//查询开始日期
	public static String ENDDATE="ENDDATE";//查询结束日期
	public static String ACCUMULATIONCREATEDATE="ACCUMULATIONCREATEDATE";//账单生成日期
	
	public static String getCHANNELNO() {
		return CHANNELNO;
	}
	public static void setCHANNELNO(String cHANNELNO) {
		CHANNELNO = cHANNELNO;
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
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
	public static String getOPERATOR() {
		return OPERATOR;
	}
	public static void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}
	public static String getTRANORG() {
		return TRANORG;
	}
	public static void setTRANORG(String tRANORG) {
		TRANORG = tRANORG;
	}
	public static String getTRANORGNAME() {
		return TRANORGNAME;
	}
	public static void setTRANORGNAME(String tRANORGNAME) {
		TRANORGNAME = tRANORGNAME;
	}
	public static String getTRANNAME() {
		return TRANNAME;
	}
	public static void setTRANNAME(String tRANNAME) {
		TRANNAME = tRANNAME;
	}
	public static String getSEALPICTUREDATA() {
		return SEALPICTUREDATA;
	}
	public static void setSEALPICTUREDATA(String sEALPICTUREDATA) {
		SEALPICTUREDATA = sEALPICTUREDATA;
	}
	public static String getCREATETIME() {
		return CREATETIME;
	}
	public static void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public static String getACCOUNTNUMBER() {
		return ACCOUNTNUMBER;
	}
	public static void setACCOUNTNUMBER(String aCCOUNTNUMBER) {
		ACCOUNTNUMBER = aCCOUNTNUMBER;
	}
	public static String getACCOUNTNAME() {
		return ACCOUNTNAME;
	}
	public static void setACCOUNTNAME(String aCCOUNTNAME) {
		ACCOUNTNAME = aCCOUNTNAME;
	}
	public static String getSTARTDATE() {
		return STARTDATE;
	}
	public static void setSTARTDATE(String sTARTDATE) {
		STARTDATE = sTARTDATE;
	}
	public static String getENDDATE() {
		return ENDDATE;
	}
	public static void setENDDATE(String eNDDATE) {
		ENDDATE = eNDDATE;
	}
	public static String getACCUMULATIONCREATEDATE() {
		return ACCUMULATIONCREATEDATE;
	}
	public static void setACCUMULATIONCREATEDATE(String aCCUMULATIONCREATEDATE) {
		ACCUMULATIONCREATEDATE = aCCUMULATIONCREATEDATE;
	}
	
}
