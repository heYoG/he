package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

/**
 * 文档表
 * @author wj
 *
 */
public class DocmentBody {
	private static final long serialVersionUID = 1L;
	private String doc_no;//文档编号，由客户端控件产生
	private String doc_name;//文档名称
	private String doc_type;//文档类型
	private String doc_title;//文档标题
	private String doc_creator;//文档创建者
	private Timestamp create_time;//文档录入系统时间
	private String create_ip;//文档录入系统的ip
	private String doc_keys;//文档关键字
	private String doc_content;//文档内容
	private String dept_no;//部门编号
    private String dept_name;//部门名
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
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public String getDoc_title() {
		return doc_title;
	}
	public void setDoc_title(String doc_title) {
		this.doc_title = doc_title;
	}
	public String getDoc_creator() {
		return doc_creator;
	}
	public void setDoc_creator(String doc_creator) {
		this.doc_creator = doc_creator;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getCreate_ip() {
		return create_ip;
	}
	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}
	public String getDoc_keys() {
		return doc_keys;
	}
	public void setDoc_keys(String doc_keys) {
		this.doc_keys = doc_keys;
	}
	public String getDoc_content() {
		return doc_content;
	}
	public void setDoc_content(String doc_content) {
		this.doc_content = doc_content;
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
    
    
    
    

}
