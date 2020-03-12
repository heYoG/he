package com.dj.seal.gaizhangRule.vo;

import com.dj.seal.util.dao.BasePO;

/**
 * 盖章规则
 * 
 * @author Administrator
 * 
 */
public class GaiZhangRuleVo extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rule_no;// 规则编号
	private String rule_name;// 规则名称
	private String seal_id;// 印章编号
	private Integer use_cert;// 是否使用证书
	private String cert_no;// 证书编号
	private Integer rule_type;// 规则类型（七大类）
	private String arg_desc;// 参数描述
    private String rule_state;//盖章规则状态 1 正常 2 注销
    
    private String seal_name;// 印章名称
    
	public String getRule_no() {
		return rule_no;
	}
	public void setRule_no(String rule_no) {
		this.rule_no = rule_no;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getSeal_id() {
		return seal_id;
	}
	public void setSeal_id(String seal_id) {
		this.seal_id = seal_id;
	}
	public Integer getUse_cert() {
		return use_cert;
	}
	public void setUse_cert(Integer use_cert) {
		this.use_cert = use_cert;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public Integer getRule_type() {
		return rule_type;
	}
	public void setRule_type(Integer rule_type) {
		this.rule_type = rule_type;
	}
	public String getArg_desc() {
		return arg_desc;
	}
	public void setArg_desc(String arg_desc) {
		this.arg_desc = arg_desc;
	}
	public String getRule_state() {
		return rule_state;
	}
	public void setRule_state(String rule_state) {
		this.rule_state = rule_state;
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
	public String getSeal_name() {
		return seal_name;
	}
	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}
    
    
}
