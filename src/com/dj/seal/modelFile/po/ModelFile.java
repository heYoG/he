package com.dj.seal.modelFile.po;

import java.sql.Timestamp;

/**
 * 
 * @description 模板实体类
 * @author zyl
 * @since 2013-05-09
 * 
 */

public class ModelFile {

	private Integer model_id;// 模板ID
	private String model_name;// 模板名称
	private String content_data;// 模板base64值
	private String field_data;// 模板里区域的base64值
	private Timestamp create_time;// 创建时间
	private String createTime;
	private Timestamp modify_time;// 最后修改时间
	private String modifyTime;
	private String create_user;// 创建人
	private String model_state;// 模板状态 1正常 2注销
	private String ishotel;// 是否无纸化系统模板 0或空为否 1为是
	private String multipart;// 是否套打 0 否 1 是
	private String printoredit;// 模版用户打印或者编辑，也可都用于（1 全包含，2打印，3编辑）

	private String role_nos;// 角色ID字符串

	private String isflow;// 是否需要进行二次签字 0或空为否 1为是
	private String page_no;// 二次签字位置页码
	private String x_position;// 二次签字位置X坐标
	private String y_position;// 二次签字位置Y坐标
	private String area_width;// 二次签字位置宽度
	private String area_height;// 二次签字位置高度
	private String area_name;// 二次签字区域名称

	private String dept_no;// 模版所属部门
	private String dept_name;//
	private String identitycard;// 身份证是否是必填项，1 必填
	private String approver;// 审批人
	private String approve_status;// 审批状态 0，申请 1 审批通过 2 退回
	private Timestamp approve_time;// 审批时间
	private String approve_reason;
	private String use_range;// 模版使用范围（保留字段）
	private String modelorxieyi;// 模版还是协议 0模板 1 协议
	private String modelwidth;// 模板宽度
	private String modelheight;// 模板高度

	private String modelchangex;// 打印内容左偏移量
	private String modelchangey;// 打印内容右偏移量

	private String printsize_width;// 打印纸张的宽（范围值）
	private String printsize_height;// 打印纸张的高（范围值）

	private String model_xtype;// x轴偏移方式
	private String model_ytype;// y轴偏移方式
	private String model_version;	//模板版本号

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getContent_data() {
		return content_data;
	}

	public void setContent_data(String content_data) {
		this.content_data = content_data;
	}

	public String getField_data() {
		return field_data;
	}

	public void setField_data(String field_data) {
		this.field_data = field_data;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getModify_time() {
		return modify_time;
	}

	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getModel_state() {
		return model_state;
	}

	public void setModel_state(String model_state) {
		this.model_state = model_state;
	}

	public String getMultipart() {
		return multipart;
	}

	public void setMultipart(String multipart) {
		this.multipart = multipart;
	}

	public String getPrintoredit() {
		return printoredit;
	}

	public void setPrintoredit(String printoredit) {
		this.printoredit = printoredit;
	}

	public String getIshotel() {
		return ishotel;
	}

	public void setIshotel(String ishotel) {
		this.ishotel = ishotel;
	}

	public String getRole_nos() {
		return role_nos;
	}

	public void setRole_nos(String role_nos) {
		this.role_nos = role_nos;
	}

	public String getIsflow() {
		return isflow;
	}

	public void setIsflow(String isflow) {
		this.isflow = isflow;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}

	public String getX_position() {
		return x_position;
	}

	public void setX_position(String x_position) {
		this.x_position = x_position;
	}

	public String getY_position() {
		return y_position;
	}

	public void setY_position(String y_position) {
		this.y_position = y_position;
	}

	public String getArea_width() {
		return area_width;
	}

	public void setArea_width(String area_width) {
		this.area_width = area_width;
	}

	public String getArea_height() {
		return area_height;
	}

	public void setArea_height(String area_height) {
		this.area_height = area_height;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getIdentitycard() {
		return identitycard;
	}

	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public Timestamp getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(Timestamp approve_time) {
		this.approve_time = approve_time;
	}

	public String getApprove_reason() {
		return approve_reason;
	}

	public void setApprove_reason(String approve_reason) {
		this.approve_reason = approve_reason;
	}

	public String getUse_range() {
		return use_range;
	}

	public void setUse_range(String use_range) {
		this.use_range = use_range;
	}

	public String getModelorxieyi() {
		return modelorxieyi;
	}

	public void setModelorxieyi(String modelorxieyi) {
		this.modelorxieyi = modelorxieyi;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getModelheight() {
		return modelheight;
	}

	public void setModelheight(String modelheight) {
		this.modelheight = modelheight;
	}

	public String getModelwidth() {
		return modelwidth;
	}

	public void setModelwidth(String modelwidth) {
		this.modelwidth = modelwidth;
	}

	public String getPrintsize_width() {
		return printsize_width;
	}

	public void setPrintsize_width(String printsize_width) {
		this.printsize_width = printsize_width;
	}

	public String getPrintsize_height() {
		return printsize_height;
	}

	public void setPrintsize_height(String printsize_height) {
		this.printsize_height = printsize_height;
	}

	public String getModel_xtype() {
		return model_xtype;
	}

	public void setModel_xtype(String model_xtype) {
		this.model_xtype = model_xtype;
	}

	public String getModel_ytype() {
		return model_ytype;
	}

	public void setModel_ytype(String model_ytype) {
		this.model_ytype = model_ytype;
	}

	public String getModelchangex() {
		return modelchangex;
	}

	public void setModelchangex(String modelchangex) {
		this.modelchangex = modelchangex;
	}

	public String getModelchangey() {
		return modelchangey;
	}

	public void setModelchangey(String modelchangey) {
		this.modelchangey = modelchangey;
	}
	public String getModel_version() {
		return model_version;
	}

	public void setModel_version(String modelVersion) {
		model_version = modelVersion;
	}

}
