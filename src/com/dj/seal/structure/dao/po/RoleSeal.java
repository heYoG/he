package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class RoleSeal extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String seal_id; // 印章id
	public String role_id; // 角色id
	public String getSeal_id() {
		return seal_id;
	}
	public void setSeal_id(String seal_id) {
		this.seal_id = seal_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result=1;
		result = prime * result + ((role_id == null) ? 0 : role_id.hashCode());
		result = prime * result + ((seal_id == null) ? 0 : seal_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final RoleSeal other = (RoleSeal) obj;
		if (role_id == null) {
			if (other.role_id != null)
				return false;
		} else if (!role_id.equals(other.role_id))
			return false;
		if (seal_id == null) {
			if (other.seal_id != null)
				return false;
		} else if (!seal_id.equals(other.seal_id))
			return false;
		return true;
	}
	
}
