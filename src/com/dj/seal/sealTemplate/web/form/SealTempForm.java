package com.dj.seal.sealTemplate.web.form;

import org.apache.struts.action.ActionForm;

/**
 * @title SealTempForm
 * @description 印模Form
 * @author oyxy
 * @since 2009-11-23
 * 
 */
public class SealTempForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer temp_id; // 印模ID
	private String temp_name; // 印模名称，唯一
	private String temp_data; // 印模数据
	private String seal_type; // 印章类型
	private String dept_no; // 外键 部门编号
	private String dept_name; // 部门名称
	private String image_width; // 图片宽度
	private String image_height; // 图片高度
	private String seal_width; // 印章宽度
	private String seal_height; // 印章高度
	private Integer seal_bit; // 印章大小
	private String temp_creator; // 外键 创建人
	private String create_time; //  创建时间
	private String temp_remark; // 备注
	private String client_system; // 客户端软件
	private String temp_status; // 印模状态
	private String approve_text; // 审批意见
	private String approve_id; // 外键 审批人id
	private String approve_user; // 外键 审批人
    private String approve_time; //  审批时间
	private String use_status; // 印模使用状态
	private String start_time; // 查询开始时间字符串
	private String end_time; // 查询结束时间字符串
    private String user_apply; //印模申请人
    private String user_applyer; //申请人（申请制章中的申请人）
	private String seal_creator; // 外键 制章人
	private String approve_begintime; // 申请起始时间
	private String approve_endtime; // 申请结束时间
	private String key_sn; //证书号
	private String preview_img;//印章缩略图 
	private String seal_czid;//财政sealID
	private String seal_base64;//财政base64值

	private String able_btime; // 印章有效期起始
	private String able_etime; // 印章有效期结束
	

	public String getAble_btime() {
		return able_btime;
	}

	public void setAble_btime(String able_btime) {
		this.able_btime = able_btime;
	}

	public String getAble_etime() {
		return able_etime;
	}

	public void setAble_etime(String able_etime) {
		this.able_etime = able_etime;
	}

	public String getSeal_czid() {
		return seal_czid;
	}

	public void setSeal_czid(String seal_czid) {
		this.seal_czid = seal_czid;
	}

	public String getSeal_base64() {
		return seal_base64;
	}

	public void setSeal_base64(String seal_base64) {
		this.seal_base64 = seal_base64;
	}

	public String getPreview_img() {
		return preview_img;
	}

	public void setPreview_img(String preview_img) {
		this.preview_img = preview_img;
	}

	public String getUser_applyer() {
		return user_applyer;
	}

	public void setUser_applyer(String user_applyer) {
		this.user_applyer = user_applyer;
	}

	public String getApprove_id() {
		return approve_id;
	}

	public void setApprove_id(String approve_id) {
		this.approve_id = approve_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public String getKey_sn() {
		return key_sn;
	}

	public void setKey_sn(String key_sn) {
		this.key_sn = key_sn;
	}

	public String getUse_status() {
		return use_status;
	}

	public void setUse_status(String use_status) {
		this.use_status = use_status;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approve_user) {
		this.approve_user = approve_user;
	}

	public String getApprove_text() {
		return approve_text;
	}

	public void setApprove_text(String approve_text) {
		this.approve_text = approve_text;
	}

	public String getTemp_status() {
		return temp_status;
	}

	public void setTemp_status(String temp_status) {
		this.temp_status = temp_status;
	}

	public String getTemp_name() {
		return temp_name;
	}

	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}

	public String getTemp_data() {
		return temp_data;
	}

	public void setTemp_data(String temp_data) {
		this.temp_data = temp_data;
	}

	public String getSeal_type() {
		return seal_type;
	}

	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getImage_width() {
		return image_width;
	}

	public void setImage_width(String image_width) {
		this.image_width = image_width;
	}

	public String getImage_height() {
		return image_height;
	}

	public void setImage_height(String image_height) {
		this.image_height = image_height;
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

	public Integer getSeal_bit() {
		return seal_bit;
	}

	public void setSeal_bit(Integer seal_bit) {
		this.seal_bit = seal_bit;
	}

	public String getTemp_creator() {
		return temp_creator;
	}

	public void setTemp_creator(String temp_creator) {
		this.temp_creator = temp_creator;
	}

	public String getTemp_remark() {
		return temp_remark;
	}

	public void setTemp_remark(String temp_remark) {
		this.temp_remark = temp_remark;
	}

	public String getClient_system() {
		return client_system;
	}

	public void setClient_system(String client_system) {
		this.client_system = client_system;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Integer getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(Integer temp_id) {
		this.temp_id = temp_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getUser_apply() {
		return user_apply;
	}

	public void setUser_apply(String user_apply) {
		this.user_apply = user_apply;
	}

	public String getSeal_creator() {
		return seal_creator;
	}

	public void setSeal_creator(String seal_creator) {
		this.seal_creator = seal_creator;
	}

	public String getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
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
    
}
