package com.dj.seal.structure.dao.po;

import java.util.Date;

import com.dj.seal.util.dao.BasePO;
/**
 * @author ZBL
 * @time:2013/5/9
 * @describe:应用系统form类
 * */
public class AppSystem extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer app_id;//应用系统编号
	private String app_name;//应用系统名称
	private String app_ip;//应用系统IP
	private String app_pwd;//应用系统密码
	private Date create_date;//应用系统创建时间
	private Integer create_user;//应用系统创建人
	private String create_userName;//创建人名称
	
	public String getCreate_userName() {
		return create_userName;
	}
	public void setCreate_userName(String create_userName) {
		this.create_userName = create_userName;
	}
	public AppSystem() {
		super();
	}
	public AppSystem(String app_name, String app_ip, String app_pwd,
			Date create_date, Integer create_user) {
		super();
		this.app_name = app_name;
		this.app_ip = app_ip;
		this.app_pwd = app_pwd;
		this.create_date = create_date;
		this.create_user = create_user;
	}
	public AppSystem(Integer app_id, String app_name, String app_ip,
			String app_pwd, Date create_date, Integer create_user) {
		super();
		this.app_id = app_id;
		this.app_name = app_name;
		this.app_ip = app_ip;
		this.app_pwd = app_pwd;
		this.create_date = create_date;
		this.create_user = create_user;
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
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Integer getCreate_user() {
		return create_user;
	}
	public void setCreate_user(Integer create_user) {
		this.create_user = create_user;
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
