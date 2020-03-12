package com.dj.seal.hotel.form;

import org.apache.struts.action.ActionForm;

public class HotelTRecordAffileateForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;// 编号
	private String name;// 名称
	private String cvalue;// 属性值
	private String recordId;// 单据记录编号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCvalue() {
		return cvalue;
	}
	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

}
