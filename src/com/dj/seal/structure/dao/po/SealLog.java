package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;
/**
 * 客户端操作日志
 * @author wj
 *
 */
public class SealLog extends BasePO {
	private static final long serialVersionUID = 1L;
	private String doc_id;
	private String log_type;//3为文档盖章 2为打印
	private int seal_id;
	private String client_system;
	private String key_sn;
	private String key_dn;
	private String mac_add;
	private String card_id;
	private String log_value;
	private String doc_title;
	private String ip;
	private Timestamp create_time;
	private String seal_name;
	private String dept_no;
	private String dept_name;
	private String user_id;
	private String user_name;
	
	public String getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}
	public int getSeal_id() {
		return seal_id;
	}
	public void setSeal_id(int seal_id) {
		this.seal_id = seal_id;
	}
	public String getClient_system() {
		return client_system;
	}
	public void setClient_system(String client_system) {
		this.client_system = client_system;
	}
	public String getKey_sn() {
		return key_sn;
	}
	public void setKey_sn(String key_sn) {
		this.key_sn = key_sn;
	}
	public String getKey_dn() {
		return key_dn;
	}
	public void setKey_dn(String key_dn) {
		this.key_dn = key_dn;
	}
	public String getMac_add() {
		return mac_add;
	}
	public void setMac_add(String mac_add) {
		this.mac_add = mac_add;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getLog_value() {
		return log_value;
	}
	public void setLog_value(String log_value) {
		this.log_value = log_value;
	}
	public String getDoc_title() {
		return doc_title;
	}
	public void setDoc_title(String doc_title) {
		this.doc_title = doc_title;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getSeal_name() {
		return seal_name;
	}
	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
}
