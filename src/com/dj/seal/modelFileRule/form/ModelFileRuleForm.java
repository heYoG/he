package com.dj.seal.modelFileRule.form;

import org.apache.struts.action.ActionForm;

public class ModelFileRuleForm extends ActionForm {
	
	
	private static final long serialVersionUID = 2718538245244489083L;
	
	private String mid;
	private String model_id; //模板ID
	private String rule_no; //规则ID
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String modelId) {
		model_id = modelId;
	}
	public String getRule_no() {
		return rule_no;
	}
	public void setRule_no(String ruleNo) {
		rule_no = ruleNo;
	}
	

}
