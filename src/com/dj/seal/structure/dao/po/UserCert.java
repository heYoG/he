package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;
/**
 * 客户端盖章用户和证书关联表
 * @author wj
 *
 */
public class UserCert  extends BasePO{

	private static final long serialVersionUID = 1L;
	private String user_id;//用户名
	private String cert_no;//用户证书序列号
	private String is_active;//当前证书是否是用户正在使用的证书。0,历史证书。1,正在使用的证书
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
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
