package com.dj.seal.modelFileRule.po;

import com.dj.seal.util.dao.BasePO;

public class ModelFileRulePO extends BasePO {

	
	
	private static final long serialVersionUID = -9193881772154273932L;
	
	private String mid;
	private String model_id;
	private String rule_no;

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


	@Override
	public boolean equals(Object obj) {
		return false;
	}


	@Override
	public int hashCode() {
		return 0;
	}

}
