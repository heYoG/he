package com.dj.seal.cert.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CertForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cert_no;// 证书号
	private String cert_name;// 证书别名
	private String cert_dn;// 证书DN项
	private String cert_src;// 证书来源:客户端、服务器文件证书、签名服务器
	private String cert_detail;// 证书详细：路径或别名
	private String cert_psd;// 证书密码
	private String cert_type;// 证书类型：个人证书、公证书
	private String cert_status;// 证书状态
	private String begin_time_str;// 证书有效期起始
	private String end_time_str;// 证书有效期结束
	private String dept_no;// 部门号
	private String dept_name;// 部门名称
	private String reg_user;// 登记人
	private String reg_time_str;//登记时间
	private String reg_user_name;// 登记人姓名
	private FormFile cert_path;// 证书文件
	private Integer seal_num;// 印章数量
	private String seal_sel;// 印章ID字符串
	private String is_Sealbody;// 是否绑定印章
    private String file_content;
    
	

	public String getFile_content() {
		return file_content;
	}

	public void setFile_content(String file_content) {
		this.file_content = file_content;
	}

	public String getIs_Sealbody() {
		return is_Sealbody;
	}

	public void setIs_Sealbody(String is_Sealbody) {
		this.is_Sealbody = is_Sealbody;
	}

	public FormFile getCert_path() {
		return cert_path;
	}

	public void setCert_path(FormFile cert_path) {
		this.cert_path = cert_path;
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

	public String getBegin_time_str() {
		return begin_time_str;
	}

	public void setBegin_time_str(String begin_time_str) {
		this.begin_time_str = begin_time_str;
	}

	public String getEnd_time_str() {
		return end_time_str;
	}

	public void setEnd_time_str(String end_time_str) {
		this.end_time_str = end_time_str;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getReg_user() {
		return reg_user;
	}

	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}

	
	public String getReg_time_str() {
		return reg_time_str;
	}

	public void setReg_time_str(String reg_time_str) {
		this.reg_time_str = reg_time_str;
	}

	public String getReg_user_name() {
		return reg_user_name;
	}

	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}

	public Integer getSeal_num() {
		return seal_num;
	}

	public void setSeal_num(Integer seal_num) {
		this.seal_num = seal_num;
	}

	public String getSeal_sel() {
		return seal_sel;
	}

	public void setSeal_sel(String seal_sel) {
		this.seal_sel = seal_sel;
	}

}
