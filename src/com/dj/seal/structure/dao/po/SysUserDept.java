package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class SysUserDept extends BasePO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_id;
	private String dept_no;
   
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
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
