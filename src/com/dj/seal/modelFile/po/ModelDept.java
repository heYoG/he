package com.dj.seal.modelFile.po;
/**
 * 记录模版和协议和废弃他的部门间的关系，上级部门的模版和协议，下级可以选择废弃不使用，
 * @author ThinkPad
 *
 */
public class ModelDept {
	private Integer model_id;// 模板ID
	private String dept_no;//部门编号
	public Integer getModel_id() {
		return model_id;
	}
	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	
}
