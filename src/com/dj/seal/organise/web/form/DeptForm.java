package com.dj.seal.organise.web.form;

import org.apache.struts.action.ActionForm;

/**
 * @title DeptForm
 * @description 部门信息显示ActionForm
 * @author oyxy
 * @since 2009-11-10
 * 
 */
public class DeptForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dept_no; // 部门编号
	private String dept_name; // 部门名称
	private String tel_no; // 电话号码
	private String fax_no; // 传真号码
	private String dept_tab; // 部门排序号
	private String dept_parent; // 父部门编号
	private String parent_name; // 父部门名称
	private String dept_func; // 部门职能
	private String bank_dept; // 对应的银行序列号
	private String priv_no;// 自身编号
	private String dept_lever;// 部门所属级别

	// private String is_detpflow;//是否有下级部门

	// public String getIs_detpflow() {
	// return is_detpflow;
	// }
	// public void setIs_detpflow(String is_detpflow) {
	// this.is_detpflow = is_detpflow;
	// }
	public String getBank_dept() {
		return bank_dept;
	}

	public void setBank_dept(String bank_dept) {
		this.bank_dept = bank_dept;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getTel_no() {
		return tel_no;
	}

	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public String getFax_no() {
		return fax_no;
	}

	public void setFax_no(String fax_no) {
		this.fax_no = fax_no;
	}

	public String getDept_tab() {
		return dept_tab;
	}

	public void setDept_tab(String dept_tab) {
		this.dept_tab = dept_tab;
	}

	public String getDept_parent() {
		return dept_parent;
	}

	public void setDept_parent(String dept_parent) {
		this.dept_parent = dept_parent;
	}

	public String getDept_func() {
		return dept_func;
	}

	public void setDept_func(String dept_func) {
		this.dept_func = dept_func;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getPriv_no() {
		return priv_no;
	}

	public void setPriv_no(String priv_no) {
		this.priv_no = priv_no;
	}

	public String getDept_lever() {
		return dept_lever;
	}

	public void setDept_lever(String dept_lever) {
		this.dept_lever = dept_lever;
	}

}
