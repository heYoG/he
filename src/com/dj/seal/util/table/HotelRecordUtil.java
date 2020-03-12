package com.dj.seal.util.table;

/**
 * 单据表
 */
public class HotelRecordUtil {

	public static String TABLE_NAME = "T_HOTELRECORD";
	public static String CID = "cid";// 通过UUID生成
	public static String CIP = "cip";// 访问IP地址
	public static String CCREATETIME = "ccreateTime";// 单据创建时间
	public static String CUPLOADTIME = "cuploadTime";// 上传时间
	public static String MTPLID = "mtplId";// 模版ID
	public static String CREATEUSERID = "createUserId";// 用户ID
	public static String UPLOADUSERID = "uploadUserId";// 上传用户ID
	public static String CFILEFILENAME = "cfileFileName";// 文件名称
	public static String CDATA = "saveFileName";// Pdf文件
	public static String CSTATUS = "cstatus";// 状态（0作废 1正常）
	public static String HASDONE = "hasdone";// 是否已处理(0为需要处理且未处理，1为已处理)
	public static String DEPTNO = "deptno";// 所属部门
	
	public static String AGREEMENTID = "agreementid";//协议ID
	public static String HAVEIDCARD = "haveidcard";//是否包含证件 （0为否，1为是）
	public static String HAVEATTACH = "haveattach";//是否包含附件照 （0为否，1为是）
	public static String CHECKSTATUS = "checkstatus";//稽核状态（1为待一级稽核，2一级稽核通过待二级稽核，3二级稽核通过待三级稽核，4三级稽核通过待四级稽核，5四级稽核通过)（a为一级稽核未通过，b为二级稽核未通过，c为三级稽核未通过，d为四级稽核未通过）
	public static String JIHEUSER = "jiheuser";// 稽核人
	public static String JIHETIME = "jihetime";// 稽核时间
	public static String JIHEREASON = "jihereason";// 稽核不通过原因
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
		sb.append(CCREATETIME).append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(CUPLOADTIME).append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(MTPLID).append(" varchar(100),");
		sb.append(CREATEUSERID).append(" varchar(100),");
		sb.append(UPLOADUSERID).append(" varchar(100),");
		sb.append(CFILEFILENAME).append(" varchar(255),");
		sb.append(CDATA).append(" varchar(255),");
		sb.append(CSTATUS).append(" varchar(100),");
		sb.append(HASDONE).append(" varchar(100),");
		sb.append(DEPTNO).append(" varchar(255),");
		sb.append(AGREEMENTID).append(" varchar(255),");
		sb.append(HAVEIDCARD).append(" varchar(100),");
		sb.append(HAVEATTACH).append(" varchar(100),");
		sb.append(CHECKSTATUS).append(" varchar(100),");
		sb.append(JIHEUSER).append(" varchar(100),");
		sb.append(JIHETIME).append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(JIHEREASON).append(" varchar(255),");
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
		sb.append(CCREATETIME).append(" datetime,");
		sb.append(CUPLOADTIME).append(" datetime,");
		sb.append(MTPLID).append(" varchar(100),");
		sb.append(CREATEUSERID).append(" varchar(100),");
		sb.append(UPLOADUSERID).append(" varchar(100),");
		sb.append(CFILEFILENAME).append(" varchar(255),");
		sb.append(CDATA).append(" varchar(255),");
		sb.append(CSTATUS).append(" varchar(100),");
		sb.append(HASDONE).append(" varchar(100),");
		sb.append(DEPTNO).append(" varchar(255),");
		sb.append(AGREEMENTID).append(" varchar(255),");
		sb.append(HAVEIDCARD).append(" varchar(100),");
		sb.append(HAVEATTACH).append(" varchar(100),");
		sb.append(CHECKSTATUS).append(" varchar(100),");
		sb.append(JIHEUSER).append(" varchar(100),");
		sb.append(JIHETIME).append(" datetime,");
		sb.append(JIHEREASON).append(" varchar(255),");
		sb.append(REMARKS).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString()+";");
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

	public static String getCCREATETIME() {
		return CCREATETIME;
	}

	public static void setCCREATETIME(String ccreatetime) {
		CCREATETIME = ccreatetime;
	}

	public static String getCUPLOADTIME() {
		return CUPLOADTIME;
	}

	public static void setCUPLOADTIME(String cuploadtime) {
		CUPLOADTIME = cuploadtime;
	}

	public static String getMTPLID() {
		return MTPLID;
	}

	public static void setMTPLID(String mtplid) {
		MTPLID = mtplid;
	}

	public static String getCREATEUSERID() {
		return CREATEUSERID;
	}

	public static void setCREATEUSERID(String createuserid) {
		CREATEUSERID = createuserid;
	}

	public static String getUPLOADUSERID() {
		return UPLOADUSERID;
	}

	public static void setUPLOADUSERID(String uploaduserid) {
		UPLOADUSERID = uploaduserid;
	}

	public static String getCFILEFILENAME() {
		return CFILEFILENAME;
	}

	public static void setCFILEFILENAME(String cfilefilename) {
		CFILEFILENAME = cfilefilename;
	}

	public static String getCDATA() {
		return CDATA;
	}

	public static void setCDATA(String cdata) {
		CDATA = cdata;
	}

	public static String getCSTATUS() {
		return CSTATUS;
	}

	public static void setCSTATUS(String cstatus) {
		CSTATUS = cstatus;
	}

	public static String getHASDONE() {
		return HASDONE;
	}

	public static void setHASDONE(String hasdone) {
		HASDONE = hasdone;
	}

	public static String getDEPTNO() {
		return DEPTNO;
	}

	public static void setDEPTNO(String deptno) {
		DEPTNO = deptno;
	}

	public static String getAGREEMENTID() {
		return AGREEMENTID;
	}

	public static void setAGREEMENTID(String agreementid) {
		AGREEMENTID = agreementid;
	}

	public static String getHAVEIDCARD() {
		return HAVEIDCARD;
	}

	public static void setHAVEIDCARD(String haveidcard) {
		HAVEIDCARD = haveidcard;
	}

	public static String getHAVEATTACH() {
		return HAVEATTACH;
	}

	public static void setHAVEATTACH(String haveattach) {
		HAVEATTACH = haveattach;
	}

	public static String getCHECKSTATUS() {
		return CHECKSTATUS;
	}

	public static void setCHECKSTATUS(String checkstatus) {
		CHECKSTATUS = checkstatus;
	}

	public static String getJIHEUSER() {
		return JIHEUSER;
	}

	public static void setJIHEUSER(String jiheuser) {
		JIHEUSER = jiheuser;
	}

	public static String getJIHETIME() {
		return JIHETIME;
	}

	public static void setJIHETIME(String jihetime) {
		JIHETIME = jihetime;
	}

	public static String getJIHEREASON() {
		return JIHEREASON;
	}

	public static void setJIHEREASON(String jihereason) {
		JIHEREASON = jihereason;
	}

	public static String getREMARKS() {
		return REMARKS;
	}

	public static void setREMARKS(String remarks) {
		REMARKS = remarks;
	}

}
