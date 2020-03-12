package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

public class Cert extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cert_no;// 证书号
	private String cert_name;// 证书别名
	private String cert_dn;// 证书DN项
	private String cert_user;//证书使用者（证书详细信息）
	private String cert_issue;//证书颁发机构（证书详细信息）
	private String cert_src;// 证书来源<详见CertType类>:客户端公钥证书(clientCert)、客户端pfx证书(clientPFX)、服务器文件证书、签名服务器
	private String cert_detail;// 证书详细：路径或别名
	private String cert_psd;// 证书密码
	private String cert_type;// 证书类型：个人证书、公证书
	private String cert_status;// 证书状态
	private Timestamp begin_time;// 证书有效期起始
	private Timestamp end_time;// 证书有效期结束
	private String dept_no;// 证书所属部门
	private String reg_user;//登记人
	private Timestamp reg_time;//登记时间
	private String file_content;// 文件内容
	private String pfx_content;//是客户端pfx时，客户端的pfx存在这个字段里。


	
	public String getFile_content() {
		return file_content;
	}

	public void setFile_content(String file_content) {
		this.file_content = file_content;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

	public String getCert_name() {
		return cert_name;
	}

	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}

	public String getCert_dn() {
		return cert_dn;
	}

	public void setCert_dn(String cert_dn) {
		this.cert_dn = cert_dn;
	}

	public String getCert_src() {
		return cert_src;
	}

	public void setCert_src(String cert_src) {
		this.cert_src = cert_src;
	}

	public String getCert_detail() {
		return cert_detail;
	}

	public void setCert_detail(String cert_detail) {
		this.cert_detail = cert_detail;
	}

	public String getCert_psd() {
		return cert_psd;
	}

	public void setCert_psd(String cert_psd) {
		this.cert_psd = cert_psd;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_status() {
		return cert_status;
	}

	public void setCert_status(String cert_status) {
		this.cert_status = cert_status;
	}

	public Timestamp getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Timestamp begin_time) {
		this.begin_time = begin_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
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

	public String getReg_user() {
		return reg_user;
	}

	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}

	public Timestamp getReg_time() {
		return reg_time;
	}

	public void setReg_time(Timestamp reg_time) {
		this.reg_time = reg_time;
	}

	public String getCert_user() {
		return cert_user;
	}

	public void setCert_user(String cert_user) {
		this.cert_user = cert_user;
	}

	public String getCert_issue() {
		return cert_issue;
	}

	public void setCert_issue(String cert_issue) {
		this.cert_issue = cert_issue;
	}

	public String getPfx_content() {
		return pfx_content;
	}

	public void setPfx_content(String pfx_content) {
		this.pfx_content = pfx_content;
	}


	

}
