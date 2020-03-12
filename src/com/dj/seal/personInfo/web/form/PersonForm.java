package com.dj.seal.personInfo.web.form;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

public class PersonForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_id; // 用户名
	private String user_sex; // 性别
	private Timestamp user_birth; // 生日
	private String add_home; // 家庭地址
	private String post_no_home; // 家庭邮政编码
	private String tel_no_home; // 家庭电话
	private String mobil_no; // 手机号码
	private String mobile_no_hidden; // 手机号是否隐藏
	private String user_email; // 邮箱号码
	private String tel_no_dept; // 部门电话

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public Timestamp getUser_birth() {
		return user_birth;
	}

	public void setUser_birth(Timestamp user_birth) {
		this.user_birth = user_birth;
	}

	public String getAdd_home() {
		return add_home;
	}

	public void setAdd_home(String add_home) {
		this.add_home = add_home;
	}

	public String getPost_no_home() {
		return post_no_home;
	}

	public void setPost_no_home(String post_no_home) {
		this.post_no_home = post_no_home;
	}

	public String getTel_no_home() {
		return tel_no_home;
	}

	public void setTel_no_home(String tel_no_home) {
		this.tel_no_home = tel_no_home;
	}

	public String getMobil_no() {
		return mobil_no;
	}

	public void setMobil_no(String mobil_no) {
		this.mobil_no = mobil_no;
	}

	public String getMobile_no_hidden() {
		return mobile_no_hidden;
	}

	public void setMobile_no_hidden(String mobile_no_hidden) {
		this.mobile_no_hidden = mobile_no_hidden;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getTel_no_dept() {
		return tel_no_dept;
	}

	public void setTel_no_dept(String tel_no_dept) {
		this.tel_no_dept = tel_no_dept;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
