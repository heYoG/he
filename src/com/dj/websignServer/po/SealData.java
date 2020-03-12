package com.dj.websignServer.po;

public class SealData {
	private String cert_id;//证书序列号
	private String seal_x;//偏移x
	private String seal_y;//偏移y
	private String user_id;//盖章用户
	private String oriData_sha1;//盖章时绑定的数据的sha-1
	private String signRes;//盖章时绑定的数据的sha-1的P1签名结果
	private String seal_id;//印章id
	private String seal_data;//印章数据
	private String seal_name;//盖章后唯一标识名称
	private String seal_position;//盖章位置
	private String seal_time;//盖章时间
	
	
	
	
	
	public String getCert_id() {
		return cert_id;
	}
	public void setCert_id(String cert_id) {
		this.cert_id = cert_id;
	}
	public String getSeal_id() {
		return seal_id;
	}
	public void setSeal_id(String seal_id) {
		this.seal_id = seal_id;
	}
	public String getSeal_name() {
		return seal_name;
	}
	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}
	public String getSeal_position() {
		return seal_position;
	}
	public void setSeal_position(String seal_position) {
		this.seal_position = seal_position;
	}
	public String getSeal_x() {
		return seal_x;
	}
	public void setSeal_x(String seal_x) {
		this.seal_x = seal_x;
	}
	public String getSeal_y() {
		return seal_y;
	}
	public void setSeal_y(String seal_y) {
		this.seal_y = seal_y;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOriData_sha1() {
		return oriData_sha1;
	}
	public void setOriData_sha1(String oriData_sha1) {
		this.oriData_sha1 = oriData_sha1;
	}
	public String getSignRes() {
		return signRes;
	}
	public void setSignRes(String signRes) {
		this.signRes = signRes;
	}
	public String getSeal_data() {
		return seal_data;
	}
	public void setSeal_data(String seal_data) {
		this.seal_data = seal_data;
	}
	public String getSeal_time() {
		return seal_time;
	}
	public void setSeal_time(String seal_time) {
		this.seal_time = seal_time;
	}
	
	

}