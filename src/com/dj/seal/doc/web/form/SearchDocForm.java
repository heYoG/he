package com.dj.seal.doc.web.form;

import org.apache.struts.action.ActionForm;

public class SearchDocForm extends ActionForm{
	
	private static final long serialVersionUID = 1L;
	private String doc_no;//文档编号
	private String doc_name;//文档名称
	private String doc_title;//文档标题
	private String dept_no; // 外键 部门编号
	private String dept_name;    //部门名
	private String start_time; //操作时间
    private String  end_time;    //操作时间
    private String is_junior;//是否包含下属部门
    private int pageIndex;//暂存当前页码，用于从设置打印份数跳转回来时获取当前页
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	public String getDoc_title() {
		return doc_title;
	}
	public void setDoc_title(String doc_title) {
		this.doc_title = doc_title;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	public String getIs_junior() {
		return is_junior;
	}
	public void setIs_junior(String is_junior) {
		this.is_junior = is_junior;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
    
    
    
}
