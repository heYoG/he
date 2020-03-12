package com.dj.seal.structure.dao.po;

public class EvaluationPo {

	private String srctrancode;// 交易码
	private String srcsysid;// 源系统
	private String srctrndt;// 系统交易日期
	private String srcesqno;// 源系统流水号和交易流水号相同
	private String tranbrchno;// 交易机构
	private String trantlr;// 交易柜员
	private String trantrx;// 交易流水
	private String trannm;// 业务类型
	private String jdgdttm;// 评价时间
	private String jdgrslt;// 评价结果
	private String reason; // 评价原因
	private String trancode;// 交易代码

	public String getSrctrancode() {
		return srctrancode;
	}

	public void setSrctrancode(String srctrancode) {
		this.srctrancode = srctrancode;
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	public String getSrcsysid() {
		return srcsysid;
	}

	public void setSrcsysid(String srcsysid) {
		this.srcsysid = srcsysid;
	}

	public String getSrctrndt() {
		return srctrndt;
	}

	public void setSrctrndt(String srctrndt) {
		this.srctrndt = srctrndt;
	}

	public String getSrcesqno() {
		return srcesqno;
	}

	public void setSrcesqno(String srcesqno) {
		this.srcesqno = srcesqno;
	}

	public String getTranbrchno() {
		return tranbrchno;
	}

	public void setTranbrchno(String tranbrchno) {
		this.tranbrchno = tranbrchno;
	}

	public String getTrantlr() {
		return trantlr;
	}

	public void setTrantlr(String trantlr) {
		this.trantlr = trantlr;
	}

	public String getTrantrx() {
		return trantrx;
	}

	public void setTrantrx(String trantrx) {
		this.trantrx = trantrx;
	}

	public String getTrannm() {
		return trannm;
	}

	public void setTrannm(String trannm) {
		this.trannm = trannm;
	}

	public String getJdgdttm() {
		return jdgdttm;
	}

	public void setJdgdttm(String jdgdttm) {
		this.jdgdttm = jdgdttm;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getJdgrslt() {
		return jdgrslt;
	}

	public void setJdgrslt(String jdgrslt) {
		this.jdgrslt = jdgrslt;
	}

}
