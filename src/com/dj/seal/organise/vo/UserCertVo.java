package com.dj.seal.organise.vo;

import java.sql.Timestamp;

public class UserCertVo {
	private String cert_no;// 证书号
	private String cert_name;// 证书别名
	private String cert_dn;// 证书DN项
	private String cert_user;//证书使用者（证书详细信息）
	private String cert_issue;//证书颁发机构（证书详细信息）
	private Timestamp begin_time;// 证书有效期起始
	private Timestamp end_time;// 证书有效期结束
	private String is_active;//证书是否是被绑定状态
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getCert_name() {
		return cert_name;
	}
	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}
	public String getCert_dn() {
		return cert_dn;
	}
	public void setCert_dn(String cert_dn) {
		this.cert_dn = cert_dn;
	}
	public String getCert_user() {
		return cert_user;
	}
	public void setCert_user(String cert_user) {
		this.cert_user = cert_user;
	}
	public String getCert_issue() {
		return cert_issue;
	}
	public void setCert_issue(String cert_issue) {
		this.cert_issue = cert_issue;
	}
	public Timestamp getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Timestamp begin_time) {
		this.begin_time = begin_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	
	

}
