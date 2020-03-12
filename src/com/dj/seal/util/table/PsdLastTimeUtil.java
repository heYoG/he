package com.dj.seal.util.table;

/**
 * 杂类参数表
 * @author WB000520
 *
 */
public class PsdLastTimeUtil {
	public static String TABLE_NAME="PZLCS";//表名
	private static String CANSMC="CANSMC";//参数名称
	private static String CANSHZ="CANSHZ";//参数值 
	private static String CANSSJ="CANSSJ"; //参数数据
	private static String BYCSSJ="BYCSSJ"; //备用数据
	private static String  SHMING="SHMING"; //参数说明
	private static String  SHJNCH="SHJNCH"; //时间戳
	private static String  JILUZT="JILUZT"; //记录状态
	private static String  REMARK1="REMARK1"; //备用字段1
	private static String  REMARK2="REMARK2";//备用字段2
	
	public static String getCANSMC() {
		return CANSMC;
	}
	public static void setCANSMC(String cANSMC) {
		CANSMC = cANSMC;
	}
	public static String getCANSHZ() {
		return CANSHZ;
	}
	public static void setCANSHZ(String cANSHZ) {
		CANSHZ = cANSHZ;
	}
	public static String getCANSSJ() {
		return CANSSJ;
	}
	public static void setCANSSJ(String cANSSJ) {
		CANSSJ = cANSSJ;
	}
	public static String getBYCSSJ() {
		return BYCSSJ;
	}
	public static void setBYCSSJ(String bYCSSJ) {
		BYCSSJ = bYCSSJ;
	}
	public static String getSHMING() {
		return SHMING;
	}
	public static void setSHMING(String sHMING) {
		SHMING = sHMING;
	}
	public static String getSHJNCH() {
		return SHJNCH;
	}
	public static void setSHJNCH(String sHJNCH) {
		SHJNCH = sHJNCH;
	}
	public static String getJILUZT() {
		return JILUZT;
	}
	public static void setJILUZT(String jILUZT) {
		JILUZT = jILUZT;
	}
	public static String getREMARK1() {
		return REMARK1;
	}
	public static void setREMARK1(String rEMARK1) {
		REMARK1 = rEMARK1;
	}
	public static String getREMARK2() {
		return REMARK2;
	}
	public static void setREMARK2(String rEMARK2) {
		REMARK2 = rEMARK2;
	}
	
}
