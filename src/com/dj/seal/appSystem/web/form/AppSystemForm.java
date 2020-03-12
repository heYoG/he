package com.dj.seal.appSystem.web.form;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;
/**
 * @author ZBL
 * @time:2013/5/9
 * @describe:应用系统form类
 * */
public class AppSystemForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer app_id;
	private String app_no;//应用系统编号
	private String app_name;//应用系统名称
	private String app_ip;//应用系统IP
	private String app_pwd;//应用系统密码
	private Timestamp create_date;//应用系统创建时间
	private String createdate1;
	private String create_username;//应用系统创建人名称
	
	
	
	public String getCreatedate1() {
		return createdate1;
	}
	public void setCreatedate1(String createdate1) {
		this.createdate1 = createdate1;
	}
	public Integer getApp_id() {
		return app_id;
	}
	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getApp_ip() {
		return app_ip;
	}
	public void setApp_ip(String app_ip) {
		this.app_ip = app_ip;
	}
	public String getApp_pwd() {
		return app_pwd;
	}
	public void setApp_pwd(String app_pwd) {
		this.app_pwd = app_pwd;
	}
	public Timestamp getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}
	public String getCreate_username() {
		return create_username;
	}
	public void setCreate_username(String create_username) {
		this.create_username = create_username;
	}
	public String getApp_no() {
		return app_no;
	}
	public void setApp_no(String app_no) {
		this.app_no = app_no;
	}
	
	
}
