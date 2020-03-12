package com.dj.seal.organise.web.form;

import org.apache.struts.action.ActionForm;

/**
 * @title RoleForm
 * @description 角色ActionForm
 * @author oyxy
 * @since 2009-11-10
 * 
 */
public class RoleForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String role_tab; // 角色排序号
	private String role_name; // 角色名称
	private Integer role_id; // 角色ID
	private Integer role_fun1; // 权限组一的权限值
	private Integer role_fun2; // 权限组二的权限值
	private Integer role_fun3; // 权限组三的权限值
	private Integer role_fun4; // 权限组四的权限值
	private Integer role_fun5; // 权限组五的权限值

	private String func_v;// 权限组一的权限值
	private String func_v2;// 权限组一的权限值
	private String sel_users;// 用户列表
	private String sel_rules;// 规则列表

	public String getFunc_v() {
		return func_v;
	}

	public void setFunc_v(String func_v) {
		this.func_v = func_v;
	}

	public String getSel_users() {
		return sel_users;
	}

	public void setSel_users(String sel_users) {
		this.sel_users = sel_users;
	}

	public String getSel_rules() {
		return sel_rules;
	}

	public void setSel_rules(String sel_rules) {
		this.sel_rules = sel_rules;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getRole_fun1() {
		return role_fun1;
	}

	public void setRole_fun1(Integer role_fun1) {
		this.role_fun1 = role_fun1;
	}

	public Integer getRole_fun2() {
		return role_fun2;
	}

	public void setRole_fun2(Integer role_fun2) {
		this.role_fun2 = role_fun2;
	}

	public Integer getRole_fun3() {
		return role_fun3;
	}

	public void setRole_fun3(Integer role_fun3) {
		this.role_fun3 = role_fun3;
	}

	public Integer getRole_fun4() {
		return role_fun4;
	}

	public void setRole_fun4(Integer role_fun4) {
		this.role_fun4 = role_fun4;
	}

	public Integer getRole_fun5() {
		return role_fun5;
	}

	public void setRole_fun5(Integer role_fun5) {
		this.role_fun5 = role_fun5;
	}

	public String getRole_tab() {
		return role_tab;
	}

	public void setRole_tab(String role_tab) {
		this.role_tab = role_tab;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getFunc_v2() {
		return func_v2;
	}

	public void setFunc_v2(String func_v2) {
		this.func_v2 = func_v2;
	}

}
