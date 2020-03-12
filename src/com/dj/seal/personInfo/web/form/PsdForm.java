package com.dj.seal.personInfo.web.form;

import org.apache.struts.action.ActionForm;

public class PsdForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_id;// 用户名
	private String old_psd; // 旧密码
	private String new_psd;// 新密码
	private String password1;//旧密码1
	private String password2;//旧密码2
	private String password1md5;//旧密码1的MD5加密
	private String password2md5;//旧密码2的MD5加密

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOld_psd() {
		return old_psd;
	}

	public void setOld_psd(String old_psd) {
		this.old_psd = old_psd;
	}

	public String getNew_psd() {
		return new_psd;
	}

	public void setNew_psd(String new_psd) {
		this.new_psd = new_psd;
	}


	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword1md5() {
		return password1md5;
	}

	public void setPassword1md5(String password1md5) {
		this.password1md5 = password1md5;
	}

	public String getPassword2md5() {
		return password2md5;
	}

	public void setPassword2md5(String password2md5) {
		this.password2md5 = password2md5;
	}

}
