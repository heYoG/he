package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class SysUserRole extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_id;
	private Integer role_id; //角色id
	private String user_role_status="1";
	
	public String getUser_role_status() {
		return user_role_status;
	}
	public void setUser_role_status(String user_role_status) {
		this.user_role_status = user_role_status;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
