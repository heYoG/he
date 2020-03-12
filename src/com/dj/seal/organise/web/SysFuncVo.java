package com.dj.seal.organise.web;

public class SysFuncVo {
	private Integer func_id; // 权限id
	private String menu_no; // 菜单编号
	private Integer func_value; // 权限值
	private String func_name; // 权限名
	private String func_group; // 权限组名
	private String func_image; // 菜单图片
	private String func_url; // 链接地址
	private Integer checked = 0; // 是否被选，“1”为被选，“0”不被选
	private String menu_name;// 菜单名
	private Integer user_num;// 用户数量
	private Integer role_num;// 角色数量
	private String sel_users;// 用户字符串
	private String sel_roles;// 角色字条串

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public Integer getUser_num() {
		return user_num;
	}

	public void setUser_num(Integer user_num) {
		this.user_num = user_num;
	}

	public Integer getRole_num() {
		return role_num;
	}

	public void setRole_num(Integer role_num) {
		this.role_num = role_num;
	}

	public String getSel_users() {
		return sel_users;
	}

	public void setSel_users(String sel_users) {
		this.sel_users = sel_users;
	}

	public String getSel_roles() {
		return sel_roles;
	}

	public void setSel_roles(String sel_roles) {
		this.sel_roles = sel_roles;
	}

	public Integer getFunc_id() {
		return func_id;
	}

	public void setFunc_id(Integer func_id) {
		this.func_id = func_id;
	}

	public String getMenu_no() {
		return menu_no;
	}

	public void setMenu_no(String menu_no) {
		this.menu_no = menu_no;
	}

	public Integer getFunc_value() {
		return func_value;
	}

	public void setFunc_value(Integer func_value) {
		this.func_value = func_value;
	}

	public String getFunc_name() {
		return func_name;
	}

	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}

	public String getFunc_group() {
		return func_group;
	}

	public void setFunc_group(String func_group) {
		this.func_group = func_group;
	}

	public String getFunc_image() {
		return func_image;
	}

	public void setFunc_image(String func_image) {
		this.func_image = func_image;
	}

	public String getFunc_url() {
		return func_url;
	}

	public void setFunc_url(String func_url) {
		this.func_url = func_url;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}
}
