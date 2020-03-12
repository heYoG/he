package com.dj.seal.util.table;

/**
 * 单据表
 */
public class NSHRecordUtil {

	public static String TABLE_NAME = "T_NSHRECORD";
	public static String CID = "cid";// 通过UUID生成
	public static String CIP = "cip";// 访问IP地址
	// public static String CCREATETIME = "ccreateTime";// 单据创建时间
	public static String CUPLOADTIME = "cuploadtime";// 上传时间
	// public static String MTPLID = "mtplId";// 模版ID
	public static String CASESEQID = "caseseqid";// 交易流水号
	public static String TRANCODE = "trancode";// 交易码
	public static String TRANORGNAME = "tranorgname";// 交易机构名称
	public static String AUTHTELLERNO = "authtellerno";// 授权柜员号
	public static String VALCODE = "valcode";// 验证码
	public static String STATUS = "status";// 状态码
	public static String REQUIREDNUM = "requirednum";// 请求次数
	public static String BP1 = "bp1";// 业务凭证编号(弃用原有字段voucherno)
	public static String BP2 = "bp2";// 凭证类型
	public static String BP3 = "bp3";// BP3-BP10为预留字段
	public static String BP4 = "bp4";
	public static String BP5 = "bp5";
	public static String BP6 = "bp6";
	public static String BP7 = "bp7";
	public static String BP8 = "bp8";
	public static String BP9 = "bp9";
	public static String BP10 = "bp10";
	public static String INFO_PLUS = "info_plus";// 附加信息
	public static String PRINTNUM = "printnum";// 打印份数

	public static String getCASESEQID() {
		return CASESEQID;
	}

	public static void setCASESEQID(String cASESEQID) {
		CASESEQID = cASESEQID;
	}

	public static String TELLERID = "tellerid";// 柜员号
	public static String ORGUNIT = "orgunit";// 机构号
	public static String D_CUACNO = "d_cuacno";// 交易账号
	public static String D_JIOYJE = "d_jiaoyije";// 交易金额
	public static String D_TRANDT = "d_trandt";// 交易日期
	public static String D_TRANNAME = "d_tranname";// 交易名称
	public static String VOUCHERNO = "voucherno";// 凭证编号(旧)

	public static String getD_CUACNO() {
		return D_CUACNO;
	}

	public static String getD_TRANNAME() {
		return D_TRANNAME;
	}

	public static void setD_TRANNAME(String d_TRANNAME) {
		D_TRANNAME = d_TRANNAME;
	}

	public static void setD_CUACNO(String d_CUACNO) {
		D_CUACNO = d_CUACNO;
	}

	public static String getD_JIOYJE() {
		return D_JIOYJE;
	}

	public static void setD_JIOYJE(String d_JIOYJE) {
		D_JIOYJE = d_JIOYJE;
	}

	public static String getVOUCHERNO() {
		return VOUCHERNO;
	}

	public static void setVOUCHERNO(String vOUCHERNO) {
		VOUCHERNO = vOUCHERNO;
	}

	public static String getD_TRANDT() {
		return D_TRANDT;
	}

	public static void setD_TRANDT(String d_TRANDT) {
		D_TRANDT = d_TRANDT;
	}

	public static String getTRANCODE() {
		return TRANCODE;
	}

	public static void setTRANCODE(String tRANCODE) {
		TRANCODE = tRANCODE;
	}

	public static String getTELLERID() {
		return TELLERID;
	}

	public static void setTELLERID(String tELLERID) {
		TELLERID = tELLERID;
	}

	public static String getORGUNIT() {
		return ORGUNIT;
	}

	public static void setORGUNIT(String oRGUNIT) {
		ORGUNIT = oRGUNIT;
	}

	public static String getCEID() {
		return CEID;
	}

	public static void setCEID(String cEID) {
		CEID = cEID;
	}

	public static String CEID = "ceid";// 上传后的CEID号
	public static String REMARKS = "remarks";// 备用字段

	/**
	 * @description ORACLE
	 * @return
	 */
	public static String createSql4Oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(CID).append(" varchar(255),");
		sb.append(CIP).append(" varchar(100),");
		sb.append(CUPLOADTIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(CASESEQID).append(" varchar(100),");
		sb.append(TRANCODE).append(" varchar(100),");
		sb.append(TELLERID).append(" varchar(100),");
		sb.append(ORGUNIT).append(" varchar(100),");
		sb.append(CEID).append(" varchar(255),");
		sb.append(REMARKS).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * @description MySql
	 * @return
	 */
	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(CID).append(" varchar(255),");
		sb.append(CIP).append(" varchar(100),");
		sb.append(CUPLOADTIME).append(" datetime,");
		sb.append(REMARKS).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	public static String dropSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("drop table ").append(TABLE_NAME);
		return sb.toString();
	}

	public static void main(String[] args) {
		createSql4Oracle();
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getCID() {
		return CID;
	}

	public static void setCID(String cid) {
		CID = cid;
	}

	public static String getCIP() {
		return CIP;
	}

	public static void setCIP(String cip) {
		CIP = cip;
	}

	public static String getCUPLOADTIME() {
		return CUPLOADTIME;
	}

	public static void setCUPLOADTIME(String cuploadtime) {
		CUPLOADTIME = cuploadtime;
	}

	public static String getREMARKS() {
		return REMARKS;
	}

	public static void setREMARKS(String remarks) {
		REMARKS = remarks;
	}

	public static String getTRANORGNAME() {
		return TRANORGNAME;
	}

	public static void setTRANORGNAME(String tRANORGNAME) {
		TRANORGNAME = tRANORGNAME;
	}

	public static String getAUTHTELLERNO() {
		return AUTHTELLERNO;
	}

	public static void setAUTHTELLERNO(String aUTHTELLERNO) {
		AUTHTELLERNO = aUTHTELLERNO;
	}

	public static String getVALCODE() {
		return VALCODE;
	}

	public static void setVALCODE(String vALCODE) {
		VALCODE = vALCODE;
	}

	public static String getSTATUS() {
		return STATUS;
	}

	public static void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public static String getREQUIREDNUM() {
		return REQUIREDNUM;
	}

	public static void setREQUIREDNUM(String rEQUIREDNUM) {
		REQUIREDNUM = rEQUIREDNUM;
	}

	public static String getBP1() {
		return BP1;
	}

	public static void setBP1(String bP1) {
		BP1 = bP1;
	}

	public static String getBP2() {
		return BP2;
	}

	public static void setBP2(String bP2) {
		BP2 = bP2;
	}

	public static String getBP3() {
		return BP3;
	}

	public static void setBP3(String bP3) {
		BP3 = bP3;
	}

	public static String getBP4() {
		return BP4;
	}

	public static void setBP4(String bP4) {
		BP4 = bP4;
	}

	public static String getBP5() {
		return BP5;
	}

	public static void setBP5(String bP5) {
		BP5 = bP5;
	}

	public static String getBP6() {
		return BP6;
	}

	public static void setBP6(String bP6) {
		BP6 = bP6;
	}

	public static String getBP7() {
		return BP7;
	}

	public static void setBP7(String bP7) {
		BP7 = bP7;
	}

	public static String getBP8() {
		return BP8;
	}

	public static void setBP8(String bP8) {
		BP8 = bP8;
	}

	public static String getBP9() {
		return BP9;
	}

	public static void setBP9(String bP9) {
		BP9 = bP9;
	}

	public static String getBP10() {
		return BP10;
	}

	public static void setBP10(String bP10) {
		BP10 = bP10;
	}

	public static String getINFO_PLUS() {
		return INFO_PLUS;
	}

	public static void setINFO_PLUS(String iNFO_PLUS) {
		INFO_PLUS = iNFO_PLUS;
	}

	public static String getPRINTNUM() {
		return PRINTNUM;
	}

	public static void setPRINTNUM(String pRINTNUM) {
		PRINTNUM = pRINTNUM;
	}

}
