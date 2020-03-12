package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class SealUser extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String seal_id; // 印章id
	public String user_id; // 用户id

	public String getSeal_id() {
		return seal_id;
	}
	public void setSeal_id(String seal_id) {
		this.seal_id = seal_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result=1;
		result = prime * result + ((seal_id == null) ? 0 : seal_id.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final SealUser other = (SealUser) obj;
		if (seal_id == null) {
			if (other.seal_id != null)
				return false;
		} else if (!seal_id.equals(other.seal_id))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	
}
