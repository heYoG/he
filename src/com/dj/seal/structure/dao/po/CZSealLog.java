package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

public class CZSealLog {
	private static final long serialVersionUID = 1L;
	private int id;//日志id
	private String seal_czid;//印章的财政id
	private String seal_name;//印章名称
	private String oper_type;//盖章类型，1 服务器盖章  2 客户端盖章
	private Timestamp oper_time;//盖章时间
	private String cert_info;//盖章使用的证书信息
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeal_czid() {
		return seal_czid;
	}
	public void setSeal_czid(String seal_czid) {
		this.seal_czid = seal_czid;
	}
	public String getSeal_name() {
		return seal_name;
	}
	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	public Timestamp getOper_time() {
		return oper_time;
	}
	public void setOper_time(Timestamp oper_time) {
		this.oper_time = oper_time;
	}
	public String getCert_info() {
		return cert_info;
	}
	public void setCert_info(String cert_info) {
		this.cert_info = cert_info;
	}
	

}
