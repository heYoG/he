package com.dj.seal.util.table;

/*查询记录表*/
public class QueryLogUtil {
	public static String TABLE_NAME = "T_QUERYLOG";// 表名
	public static String CASESEQID = "caseseqid";// 交易流水号
	public static String VALCODE = "valcode";// 验证码
	public static String CHANNEL_NO = "channel_no";// 渠道号
	public static String PHONE_NUM = "phone_num";// 手机号
	public static String D_TRANDT = "d_trandt";// 交易日期(含时间)
	public static String TELLERID = "tellerid";// 操作柜员号

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}

	public static String getCASESEQID() {
		return CASESEQID;
	}

	public static void setCASESEQID(String cASESEQID) {
		CASESEQID = cASESEQID;
	}

	public static String getVALCODE() {
		return VALCODE;
	}

	public static void setVALCODE(String vALCODE) {
		VALCODE = vALCODE;
	}

	public static String getCHANNEL_NO() {
		return CHANNEL_NO;
	}

	public static void setCHANNEL_NO(String cHANNEL_NO) {
		CHANNEL_NO = cHANNEL_NO;
	}

	public static String getPHONE_NUM() {
		return PHONE_NUM;
	}

	public static void setPHONE_NUM(String pHONE_NUM) {
		PHONE_NUM = pHONE_NUM;
	}

	public static String getD_TRANDT() {
		return D_TRANDT;
	}

	public static void setD_TRANDT(String d_TRANDT) {
		D_TRANDT = d_TRANDT;
	}

	public static String getTELLERID() {
		return TELLERID;
	}

	public static void setTELLERID(String tELLERID) {
		TELLERID = tELLERID;
	}

}
