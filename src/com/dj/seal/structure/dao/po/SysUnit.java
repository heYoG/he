package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * @description 单位表 
 * @author oyxy
 * @since 2009-11-2
 *
 */
public class SysUnit extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String unit_name; 				//单位名称
	private  String tel_no;					//电话号码
	private  String fax_no;					//传真号码
	private  String post_no;				//邮政编码
	private  String unit_address;			//单位地址
	private  String unit_url;				//单位网址
	private  String unit_email;				//单位邮箱
	private  String bank_name;				//开户银行
	private  String bank_no;				//银行帐号
	private  String dept_no;				//部门编号	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SysUnit other = (SysUnit) obj;
		if (unit_name == null) {
			if (other.unit_name != null)
				return false;
		} else if (!unit_name.equals(other.unit_name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((unit_name == null) ? 0 : unit_name.hashCode());
		return result;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
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

	public String getPost_no() {
		return post_no;
	}

	public void setPost_no(String post_no) {
		this.post_no = post_no;
	}

	public String getUnit_address() {
		return unit_address;
	}

	public void setUnit_address(String unit_address) {
		this.unit_address = unit_address;
	}

	public String getUnit_url() {
		return unit_url;
	}

	public void setUnit_url(String unit_url) {
		this.unit_url = unit_url;
	}

	public String getUnit_email() {
		return unit_email;
	}

	public void setUnit_email(String unit_email) {
		this.unit_email = unit_email;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_no() {
		return bank_no;
	}

	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	
}
