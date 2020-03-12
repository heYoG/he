package com.dj.seal.util.struts;

import java.io.Serializable;

public class RoleDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dept_name;			//部门名称
	private Integer count1;				//有效用户数
	private Integer count0;				//无效用户数
	private String users1;				//有效用户
	private String users0;				//无效用户
	
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public Integer getCount1() {
		return count1;
	}
	public void setCount1(Integer count1) {
		this.count1 = count1;
	}
	public Integer getCount0() {
		return count0;
	}
	public void setCount0(Integer count0) {
		this.count0 = count0;
	}
	public String getUsers1() {
		return users1;
	}
	public void setUsers1(String users1) {
		this.users1 = users1;
	}
	public String getUsers0() {
		return users0;
	}
	public void setUsers0(String users0) {
		this.users0 = users0;
	}

}
