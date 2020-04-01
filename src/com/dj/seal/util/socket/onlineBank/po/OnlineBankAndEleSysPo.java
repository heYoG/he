package com.dj.seal.util.socket.onlineBank.po;

import java.sql.Timestamp;
/**
 * 	企业网银和电子对账系统实例类
 * @author Administrator
 *
 */
public class OnlineBankAndEleSysPo {
	private String channelno;//渠道号
	private String orgconsumerseqno;//流水号
	private String tranno;//交易码
	private Timestamp trandate;//交易日期
	private String valcode;//验证码
	private Timestamp createtime;//数据保存时间
	private String info_plus;//附加信息(大数据字段)
	private String backup1;//备用字段1
	private String backup2;//备用字段2
	private String backup3;//备用字段3
	
	public String getChannelno() {
		return channelno;
	}
	public void setChannelno(String channelno) {
		this.channelno = channelno;
	}
	public String getOrgconsumerseqno() {
		return orgconsumerseqno;
	}
	public void setOrgconsumerseqno(String orgconsumerseqno) {
		this.orgconsumerseqno = orgconsumerseqno;
	}
	public String getTranno() {
		return tranno;
	}
	public void setTranno(String tranno) {
		this.tranno = tranno;
	}
	public Timestamp getTrandate() {
		return trandate;
	}
	public void setTrandate(Timestamp trandate) {
		this.trandate = trandate;
	}
	public String getValcode() {
		return valcode;
	}
	public void setValcode(String valcode) {
		this.valcode = valcode;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getInfo_plus() {
		return info_plus;
	}
	public void setInfo_plus(String info_plus) {
		this.info_plus = info_plus;
	}
	public String getBackup1() {
		return backup1;
	}
	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	public String getBackup2() {
		return backup2;
	}
	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	public String getBackup3() {
		return backup3;
	}
	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}

}
