package com.dj.seal.organise.web.form;

import org.apache.struts.action.ActionForm;

/**
 * @title UnitForm
 * @description 单位信息显示ActionForm
 * @author oyxy
 * @since 2009-11-10
 * 
 */
public class UnitForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String unit_name; 				//单位名称
	private  String tel_no;					//电话号码
	private  String fax_no;					//传真号码
	private  String post_no;				//邮政编码
	private  String unit_address;			//单位地址
	private  String unit_url;				//单位网址
	private  String unit_email;				//单位邮箱
	private  String bank_name;				//开户银行
	private  String bank_no;				//银行帐号
	
	private String old_name;				//旧单位名称
	
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getFax_no() {
		return fax_no;
	}
	public void setFax_no(String fax_no) {
		this.fax_no = fax_no;
	}
	public String getPost_no() {
		return post_no;
	}
	public void setPost_no(String post_no) {
		this.post_no = post_no;
	}
	public String getUnit_address() {
		return unit_address;
	}
	public void setUnit_address(String unit_address) {
		this.unit_address = unit_address;
	}
	public String getUnit_url() {
		return unit_url;
	}
	public void setUnit_url(String unit_url) {
		this.unit_url = unit_url;
	}
	public String getUnit_email() {
		return unit_email;
	}
	public void setUnit_email(String unit_email) {
		this.unit_email = unit_email;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getOld_name() {
		return old_name;
	}
	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}

}
