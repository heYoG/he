package com.dj.seal.organise.web.form;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_no; // 用户名
	private String user_psd; // 密码
	private String key_sn; // 证书序列号
	


	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
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

}
