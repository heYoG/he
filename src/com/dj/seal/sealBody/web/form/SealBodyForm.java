package com.dj.seal.sealBody.web.form;

import org.apache.struts.action.ActionForm;

public class SealBodyForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private Integer seal_id; // 印章id
	private Integer temp_id; // 外键 印模id
	private String dept_no; // 外键 部门编号
	private String dept_name; // 外键 部门名称（新增）
	private String seal_name; // 印章名，唯一
	private String seal_type; // 印章类型
	private String seal_data; // 印章数据
	private String role_list; // 印章授权角色列表
	private String user_list; // 印章授权用户列表
	private String seal_creator; // 外键 制章人
	private String seal_status; // 印章状态
	private String client_system; // 客户端软件
	private String temp_name; // 印模名称（新增）
	private String user_apply; // 待批用户列表
	private String client; // 单个客户端软件（新增）
	private String seal_width; // 印章宽度（新增）
	private String seal_height; // 印章高度（新增）
	private String seal_bit; // 印章颜色深度（新增）
	private String is_flow; // 是否需要走印章使用申请流程
	private String approve_id;// 审批id
	private String approve_user;// 审批人
	private String approve_begintime;// 审批开始时间
	private String approve_endtime;// 审批结束时间
	private String apply_id; // 申请id
	private String apply_user; // 申请人
	private String key_no; // 证书号
	private String seal_applytime; // 申请时间
	private String temp_remark; // 备注
	private String preview_img;// 印章缩略图

	public String getPreview_img() {
		return preview_img;
	}

	public void setPreview_img(String preview_img) {
		this.preview_img = preview_img;
	}

	public String getTemp_remark() {
		return temp_remark;
	}

	public void setTemp_remark(String temp_remark) {
		this.temp_remark = temp_remark;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getSeal_applytime() {
		return seal_applytime;
	}

	public void setSeal_applytime(String seal_applytime) {
		this.seal_applytime = seal_applytime;
	}

	public String getKey_no() {
		return key_no;
	}

	public void setKey_no(String key_no) {
		this.key_no = key_no;
	}

	public String getApprove_id() {
		return approve_id;
	}

	public void setApprove_id(String approve_id) {
		this.approve_id = approve_id;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approve_user) {
		this.approve_user = approve_user;
	}

	public String getApprove_begintime() {
		return approve_begintime;
	}

	public void setApprove_begintime(String approve_begintime) {
		this.approve_begintime = approve_begintime;
	}

	public String getApprove_endtime() {
		return approve_endtime;
	}

	public void setApprove_endtime(String approve_endtime) {
		this.approve_endtime = approve_endtime;
	}

	public String getApply_id() {
		return apply_id;
	}

	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}

	public String getApply_user() {
		return apply_user;
	}

	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}

	public String getIs_flow() {
		return is_flow;
	}

	public void setIs_flow(String is_flow) {
		this.is_flow = is_flow;
	}

	public String getSeal_width() {
		return seal_width;
	}

	public void setSeal_width(String seal_width) {
		this.seal_width = seal_width;
	}

	public String getSeal_height() {
		return seal_height;
	}

	public void setSeal_height(String seal_height) {
		this.seal_height = seal_height;
	}

	public String getSeal_bit() {
		return seal_bit;
	}

	public void setSeal_bit(String seal_bit) {
		this.seal_bit = seal_bit;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getUser_apply() {
		return user_apply;
	}

	public void setUser_apply(String user_apply) {
		this.user_apply = user_apply;
	}

	public String getTemp_name() {
		return temp_name;
	}

	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}

	public Integer getSeal_id() {
		return seal_id;
	}

	public void setSeal_id(Integer seal_id) {
		this.seal_id = seal_id;
	}

	public Integer getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(Integer temp_id) {
		this.temp_id = temp_id;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getSeal_name() {
		return seal_name;
	}

	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}

	public String getSeal_type() {
		return seal_type;
	}

	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}

	public String getSeal_data() {
		return seal_data;
	}

	public void setSeal_data(String seal_data) {
		this.seal_data = seal_data;
	}

	public String getRole_list() {
		return role_list;
	}

	public void setRole_list(String role_list) {
		this.role_list = role_list;
	}

	public String getUser_list() {
		return user_list;
	}

	public void setUser_list(String user_list) {
		this.user_list = user_list;
	}

	public String getSeal_creator() {
		return seal_creator;
	}

	public void setSeal_creator(String seal_creator) {
		this.seal_creator = seal_creator;
	}

	public String getSeal_status() {
		return seal_status;
	}

	public void setSeal_status(String seal_status) {
		this.seal_status = seal_status;
	}

	public String getClient_system() {
		return client_system;
	}

	public void setClient_system(String client_system) {
		this.client_system = client_system;
	}

}
