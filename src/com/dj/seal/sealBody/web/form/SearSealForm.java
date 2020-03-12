package com.dj.seal.sealBody.web.form;

import org.apache.struts.action.ActionForm;

public class SearSealForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String dept_no; // 外键 部门编号
	private String seal_name; // 印章名，唯一
	private String seal_type; // 印章类型
	private  String create_time_start; //操作时间
    private String  create_time_end;    //操作时间
    private String dept_name;    //部门名
    private String time;         //按时间排序
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	public String getCreate_time_start() {
		return create_time_start;
	}
	public void setCreate_time_start(String create_time_start) {
		this.create_time_start = create_time_start;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
}
