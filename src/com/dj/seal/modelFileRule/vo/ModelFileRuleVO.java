package com.dj.seal.modelFileRule.vo;

import com.dj.seal.util.dao.BasePO;

public class ModelFileRuleVO extends BasePO {
	
	
	private static final long serialVersionUID = -5624148164456189557L;
	
	private String mid;

	private String model_id;
	private String rule_no;
	private String model_name;
	private String rule_name;
	
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
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String modelName) {
		model_name = modelName;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String ruleName) {
		rule_name = ruleName;
	}
	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
}
