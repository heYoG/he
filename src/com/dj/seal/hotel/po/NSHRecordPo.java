package com.dj.seal.hotel.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

public class NSHRecordPo extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cid;
	private String cip;
	private Timestamp cuploadtime;
	private String caseseqid;
	private String trancode;
	private String tellerid;
	private String orgunit;
	private String ceid;
	private String remarks;
	private String d_cuacno;
	private String d_jioyje;
	private String voucherno;
	private String d_tranname;// 交易名称
	private String tranorgname;// 交易机构名称
	private String authtellerno;// 授权柜员号
	private String valcode;// 验证码
	private String status;// 状态码
	private Number requirednum;// 请求次数
	private String bp1;// 业务凭证编号(弃用原有字段voucherno)
	private String bp2;// 凭证类型
	private String bp3;// 渠道号
	private String bp4;// bp4-bp10为预留字段
	private String bp5;
	private String bp6;
	private String bp7;
	private String bp8;
	private String bp9;
	private String bp10;
	private String info_plus;// 附加信息
	private Number printnum;// 打印份数

	public String getD_tranname() {
		return d_tranname;
	}

	public void setD_tranname(String d_tranname) {
		this.d_tranname = d_tranname;
	}

	public String getVoucherno() {
		return voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}

	private Timestamp d_trandt;// 交易日期

	public Timestamp getD_trandt() {
		return d_trandt;
	}

	public void setD_trandt(Timestamp d_trandt) {
		this.d_trandt = d_trandt;
	}

	public String getD_cuacno() {
		return d_cuacno;
	}

	public void setD_cuacno(String d_cuacno) {
		this.d_cuacno = d_cuacno;
	}

	public String getD_jioyje() {
		return d_jioyje;
	}

	public void setD_jioyje(String d_jioyje) {
		this.d_jioyje = d_jioyje;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public Timestamp getCuploadtime() {
		return cuploadtime;
	}

	public void setCuploadtime(Timestamp cuploadtime) {
		this.cuploadtime = cuploadtime;
	}

	public String getCaseseqid() {
		return caseseqid;
	}

	public void setCaseseqid(String caseseqid) {
		this.caseseqid = caseseqid;
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	public String getTellerid() {
		return tellerid;
	}

	public void setTellerid(String tellerid) {
		this.tellerid = tellerid;
	}

	public String getOrgunit() {
		return orgunit;
	}

	public void setOrgunit(String orgunit) {
		this.orgunit = orgunit;
	}

	public String getCeid() {
		return ceid;
	}

	public void setCeid(String ceid) {
		this.ceid = ceid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTranorgname() {
		return tranorgname;
	}

	public void setTranorgname(String tranorgname) {
		this.tranorgname = tranorgname;
	}

	public String getAuthtellerno() {
		return authtellerno;
	}

	public void setAuthtellerno(String authtellerno) {
		this.authtellerno = authtellerno;
	}

	public String getValcode() {
		return valcode;
	}

	public void setValcode(String valcode) {
		this.valcode = valcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Number getRequirednum() {
		return requirednum;
	}

	public void setRequirednum(Number requirednum) {
		this.requirednum = requirednum;
	}

	public String getBp1() {
		return bp1;
	}

	public void setBp1(String bp1) {
		this.bp1 = bp1;
	}

	public String getBp2() {
		return bp2;
	}

	public void setBp2(String bp2) {
		this.bp2 = bp2;
	}

	public String getBp3() {
		return bp3;
	}

	public void setBp3(String bp3) {
		this.bp3 = bp3;
	}

	public String getBp4() {
		return bp4;
	}

	public void setBp4(String bp4) {
		this.bp4 = bp4;
	}

	public String getBp5() {
		return bp5;
	}

	public void setBp5(String bp5) {
		this.bp5 = bp5;
	}

	public String getBp6() {
		return bp6;
	}

	public void setBp6(String bp6) {
		this.bp6 = bp6;
	}

	public String getBp7() {
		return bp7;
	}

	public void setBp7(String bp7) {
		this.bp7 = bp7;
	}

	public String getBp8() {
		return bp8;
	}

	public void setBp8(String bp8) {
		this.bp8 = bp8;
	}

	public String getBp9() {
		return bp9;
	}

	public void setBp9(String bp9) {
		this.bp9 = bp9;
	}

	public String getBp10() {
		return bp10;
	}

	public void setBp10(String bp10) {
		this.bp10 = bp10;
	}

	public String getInfo_plus() {
		return info_plus;
	}

	public void setInfo_plus(String info_plus) {
		this.info_plus = info_plus;
	}

	public Number getPrintnum() {
		return printnum;
	}

	public void setPrintnum(Number printnum) {
		this.printnum = printnum;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
