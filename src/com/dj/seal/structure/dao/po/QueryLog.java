package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

/*查询记录实例*/
public class QueryLog {
	private String caseseqid;// 流水号
	private String valcode;// 验证码
	private String channel_no;// 渠道号
	private String phone_num;// 手机号
	private Timestamp d_trandt;// 交易日期(含时间)
	private String tellerid;// 操作柜员号

	/* 构造函数 */
	public QueryLog() {
		super();
	}

	public QueryLog(String caseseqid, String valcode, String channel_no,
			String phone_num, Timestamp d_trandt, String tellerid) {
		super();
		this.caseseqid = caseseqid;
		this.valcode = valcode;
		this.channel_no = channel_no;
		this.phone_num = phone_num;
		this.d_trandt = d_trandt;
		this.tellerid = tellerid;
	}

	public String getCaseseqid() {
		return caseseqid;
	}

	public void setCaseseqid(String caseseqid) {
		this.caseseqid = caseseqid;
	}

	public String getValcode() {
		return valcode;
	}

	public void setValcode(String valcode) {
		this.valcode = valcode;
	}

	public String getChannel_no() {
		return channel_no;
	}

	public void setChannel_no(String channel_no) {
		this.channel_no = channel_no;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public Timestamp getD_trandt() {
		return d_trandt;
	}

	public void setD_trandt(Timestamp d_trandt) {
		this.d_trandt = d_trandt;
	}

	public String getTellerid() {
		return tellerid;
	}

	public void setTellerid(String tellerid) {
		this.tellerid = tellerid;
	}
}
