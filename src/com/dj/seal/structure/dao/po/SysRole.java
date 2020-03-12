package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class SysRole extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer role_id;					//角色id
	private String dept_no;					//部门编号
	private String role_status;				//角色状态
	private String role_name;				//角色名称
	private Integer role_fun1;				//权限组一的权限值
	private Integer role_fun2;				//权限组二的权限值
	private Integer role_fun3;				//权限组三的权限值
	private Integer role_fun4;				//权限组四的权限值
	private Integer role_fun5;				//权限组五的权限值
	private String role_tab;				//角色排序号
	
	private Integer users1;					//有效用户数
	private Integer users0;					//无效用户数
	
	public Integer getUsers1() {
		return users1;
	}

	public void setUsers1(Integer users1) {
		this.users1 = users1;
	}

	public Integer getUsers0() {
		return users0;
	}

	public void setUsers0(Integer users0) {
		this.users0 = users0;
	}

	public String getRole_tab() {
		return role_tab;
	}

	public void setRole_tab(String role_tab) {
		this.role_tab = role_tab;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SysRole other = (SysRole) obj;
		if (role_id == null) {
			if (other.role_id != null)
				return false;
		} else if (!role_id.equals(other.role_id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((role_id == null) ? 0 : role_id.hashCode());
		return result;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getRole_status() {
		return role_status;
	}

	public void setRole_status(String role_status) {
		this.role_status = role_status;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public Integer getRole_fun1() {
		return role_fun1;
	}

	public void setRole_fun1(Integer role_fun1) {
		this.role_fun1 = role_fun1;
	}

	public Integer getRole_fun2() {
		return role_fun2;
	}

	public void setRole_fun2(Integer role_fun2) {
		this.role_fun2 = role_fun2;
	}

	public Integer getRole_fun3() {
		return role_fun3;
	}

	public void setRole_fun3(Integer role_fun3) {
		this.role_fun3 = role_fun3;
	}

	public Integer getRole_fun4() {
		return role_fun4;
	}

	public void setRole_fun4(Integer role_fun4) {
		this.role_fun4 = role_fun4;
	}

	public Integer getRole_fun5() {
		return role_fun5;
	}

	public void setRole_fun5(Integer role_fun5) {
		this.role_fun5 = role_fun5;
	}

}
