package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

public class SealBodyBak extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer seal_id; // 印章id
	private String temp_name; //  印模名称
	private String dept_name; // 部门名称
	private String seal_name; // 印章名，唯一
	private String seal_type;//印章类型
	private String seal_creator; // 外键 制章人
	private Timestamp create_time; // 制章时间
	private String seal_czid;//财政sealID
	private String seal_base64;//财政base64值
	public Integer getSeal_id() {
		return seal_id;
	}
	public void setSeal_id(Integer seal_id) {
		this.seal_id = seal_id;
	}
	public String getTemp_name() {
		return temp_name;
	}
	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	public String getSeal_creator() {
		return seal_creator;
	}
	public void setSeal_creator(String seal_creator) {
		this.seal_creator = seal_creator;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getSeal_czid() {
		return seal_czid;
	}
	public void setSeal_czid(String seal_czid) {
		this.seal_czid = seal_czid;
	}
	public String getSeal_base64() {
		return seal_base64;
	}
	public void setSeal_base64(String seal_base64) {
		this.seal_base64 = seal_base64;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((create_time == null) ? 0 : create_time.hashCode());
		result = prime * result
				+ ((dept_name == null) ? 0 : dept_name.hashCode());
		result = prime * result
				+ ((seal_base64 == null) ? 0 : seal_base64.hashCode());
		result = prime * result
				+ ((seal_creator == null) ? 0 : seal_creator.hashCode());
		result = prime * result
				+ ((seal_czid == null) ? 0 : seal_czid.hashCode());
		result = prime * result + ((seal_id == null) ? 0 : seal_id.hashCode());
		result = prime * result
				+ ((seal_name == null) ? 0 : seal_name.hashCode());
		result = prime * result
				+ ((seal_type == null) ? 0 : seal_type.hashCode());
		result = prime * result
				+ ((temp_name == null) ? 0 : temp_name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SealBodyBak other = (SealBodyBak) obj;
		if (create_time == null) {
			if (other.create_time != null)
				return false;
		} else if (!create_time.equals(other.create_time))
			return false;
		if (dept_name == null) {
			if (other.dept_name != null)
				return false;
		} else if (!dept_name.equals(other.dept_name))
			return false;
		if (seal_base64 == null) {
			if (other.seal_base64 != null)
				return false;
		} else if (!seal_base64.equals(other.seal_base64))
			return false;
		if (seal_creator == null) {
			if (other.seal_creator != null)
				return false;
		} else if (!seal_creator.equals(other.seal_creator))
			return false;
		if (seal_czid == null) {
			if (other.seal_czid != null)
				return false;
		} else if (!seal_czid.equals(other.seal_czid))
			return false;
		if (seal_id == null) {
			if (other.seal_id != null)
				return false;
		} else if (!seal_id.equals(other.seal_id))
			return false;
		if (seal_name == null) {
			if (other.seal_name != null)
				return false;
		} else if (!seal_name.equals(other.seal_name))
			return false;
		if (seal_type == null) {
			if (other.seal_type != null)
				return false;
		} else if (!seal_type.equals(other.seal_type))
			return false;
		if (temp_name == null) {
			if (other.temp_name != null)
				return false;
		} else if (!temp_name.equals(other.temp_name))
			return false;
		return true;
	}

}
