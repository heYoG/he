package com.dj.seal.modelFile.form;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

public class ModelFileForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer model_id;// 模板ID
	private String model_name;// 模板名称
	private String content_data;// 模板base64值
	private String field_data;// 模板里区域的base64值
	private Timestamp create_time;// 创建时间
	private String createTime;
	private Timestamp modify_time;// 最后修改时间
	private String create_user;// 创建人
	private String model_state;//模板状态 1正常 2注销
	private String ishotel;// 是否无纸化系统模板  0或空为否  1为是
	private String multipart;// 是否套打 0 否   1 是
	private String printoredit;//模版用户打印或者编辑，也可都用于（1 全包含，2打印，3编辑）
	private String role_nos;//角色ID字符串
	private String modelwidth;//模板宽度
	private String modelheight;//模板高度
	
	private String modelchangex;//打印内容左偏移量
	private String modelchangey;//打印内容右偏移量
	
	private String printsize_width;//打印纸张的宽（范围值）
	private String printsize_height;//打印纸张的高（范围值）
	
	private String model_xtype;//x轴偏移方式
	private String model_ytype;//y轴偏移方式
	
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getIshotel() {
		return ishotel;
	}
	public void setIshotel(String ishotel) {
		this.ishotel = ishotel;
	}
	public String getPrintoredit() {
		return printoredit;
	}
	public void setPrintoredit(String printoredit) {
		this.printoredit = printoredit;
	}
	public String getRole_nos() {
		return role_nos;
	}
	public void setRole_nos(String role_nos) {
		this.role_nos = role_nos;
	}
	public String getModelwidth() {
		return modelwidth;
	}
	public void setModelwidth(String modelwidth) {
		this.modelwidth = modelwidth;
	}
	public String getModelheight() {
		return modelheight;
	}
	public void setModelheight(String modelheight) {
		this.modelheight = modelheight;
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
	

}
