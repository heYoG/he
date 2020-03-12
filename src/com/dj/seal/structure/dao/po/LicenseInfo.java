package com.dj.seal.structure.dao.po;

import java.util.Date;

import com.dj.seal.util.dao.BasePO;
/**
 * @author ZBL
 * @time:2013/5/9
 * @describe:应用系统form类
 * */
public class LicenseInfo extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Date able_data;//授权系统有效期
	private String sealnum;//授权印章数量
	private String usernum;//授权用户数量
	private String dept;//授权单位名称
	private String usernumeuse;//已使用用户数量
	private String sealnumuse;//已使用印章数量
	
	public Date getAble_data() {
		return able_data;
	}
	public void setAble_data(Date able_data) {
		this.able_data = able_data;
	}
	public String getSealnum() {
		return sealnum;
	}
	public void setSealnum(String sealnum) {
		this.sealnum = sealnum;
	}
	public String getUsernum() {
		return usernum;
	}
	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public String getUsernumeuse() {
		return usernumeuse;
	}
	public void setUsernumeuse(String usernumeuse) {
		this.usernumeuse = usernumeuse;
	}
	public String getSealnumuse() {
		return sealnumuse;
	}
	public void setSealnumuse(String sealnumuse) {
		this.sealnumuse = sealnumuse;
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
