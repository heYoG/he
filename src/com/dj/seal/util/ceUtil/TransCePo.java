package com.dj.seal.util.ceUtil;

public class TransCePo {

	private String caseSeqID;// 交易流水号
	private String tranCode;// 交易码
	private String tellerId;// 柜员号&ce用户账号
	private String orgUnit;// 机构号
	private String voucherNo;// 凭证号(旧)
	private String voucherType;// 凭证类型
	private String sealFont;// 签章额文字标示
	private String xPoint;// 左偏移量
	private String yPoint;// 右偏移量
	private String D_CUACNO;// 账号
	private String D_JIOYJE;// 交易金额
	private String D_TRANDT;// 交易时间
	private String D_tranName;// 交易名称
	private String ceUserPS;// ce影像平台密码
	private String stampCode;// 验证码
	private String ZHPZHM;// 综合凭证号(替换原来的voucherNo,无纸化此字段值柜面暂时没传)

	public String getCeUserPS() {
		return ceUserPS;
	}

	public void setCeUserPS(String ceUserPS) {
		this.ceUserPS = ceUserPS;
	}

	public String getD_tranName() {
		return D_tranName;
	}

	public void setD_tranName(String d_tranName) {
		D_tranName = d_tranName;
	}

	public String getD_CUACNO() {
		return D_CUACNO;
	}

	public void setD_CUACNO(String d_CUACNO) {
		D_CUACNO = d_CUACNO;
	}

	public String getD_JIOYJE() {
		return D_JIOYJE;
	}

	public void setD_JIOYJE(String d_JIOYJE) {
		D_JIOYJE = d_JIOYJE;
	}

	public String getD_TRANDT() {
		return D_TRANDT;
	}

	public void setD_TRANDT(String d_TRANDT) {
		String year = d_TRANDT.substring(0, 4);
		String month = d_TRANDT.substring(4, 6);
		String day = d_TRANDT.substring(6, 8);
		D_TRANDT = year + "-" + month + "-" + day + " 00:00:00";
	}

	public String getCaseSeqID() {
		return caseSeqID;
	}

	public void setCaseSeqID(String caseSeqID) {
		this.caseSeqID = caseSeqID;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTellerId() {
		return tellerId;
	}

	public void setTellerId(String tellerId) {
		this.tellerId = tellerId;
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getSealFont() {
		return sealFont;
	}

	public void setSealFont(String sealFont) {
		this.sealFont = sealFont;
	}

	public String getxPoint() {
		return xPoint;
	}

	public void setxPoint(String xPoint) {
		this.xPoint = xPoint;
	}

	public String getyPoint() {
		return yPoint;
	}

	public void setyPoint(String yPoint) {
		this.yPoint = yPoint;
	}

	public String getStampCode() {
		return stampCode;
	}

	public void setStampCode(String stampCode) {
		this.stampCode = stampCode;
	}

	public String getZHPZHM() {
		return ZHPZHM;
	}

	public void setZHPZHM(String zHPZHM) {
		ZHPZHM = zHPZHM;
	}

}
