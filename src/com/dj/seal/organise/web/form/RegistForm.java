package com.dj.seal.organise.web.form;

import org.apache.struts.action.ActionForm;

/**
 * @title RegistForm
 * @description 注册ActionForm
 * @author oyxy
 * @since 2009-11-6
 * 
 */
public class RegistForm extends ActionForm  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String user_id;						//用户名
	private String user_name;					//真实姓名
	private String user_psd;					//密码
	private String key_sn;						//证书序列号
	private String user_sex;					//性别
	private String user_birth;					//生日
	private String mobil_no;					//手机
	private String user_email;					//电子邮箱
	private String dept_no;						//部门
	private String tel_no_dept;					//单位电话
	private String user_remark;					//备注
	private String dept_name;					//部门名称（新加）
	
	public String getUser_remark() {
		return user_remark;
	}
	public void setUser_remark(String user_remark) {
		this.user_remark = user_remark;
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
	public String getUser_psd() {
		return user_psd;
	}
	public void setUser_psd(String user_psd) {
		this.user_psd = user_psd;
	}
	public String getKey_sn() {
		return key_sn;
	}
	public void setKey_sn(String key_sn) {
		this.key_sn = key_sn;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_birth() {
		return user_birth;
	}
	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}
	public String getMobil_no() {
		return mobil_no;
	}
	public void setMobil_no(String mobil_no) {
		this.mobil_no = mobil_no;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getTel_no_dept() {
		return tel_no_dept;
	}
	public void setTel_no_dept(String tel_no_dept) {
		this.tel_no_dept = tel_no_dept;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	
	
}
