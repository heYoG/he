package com.dj.seal.highcharts.form;

import org.apache.struts.action.ActionForm;

public class ChartServiceForm extends ActionForm {
	
	private String businessType;//业务类型
	private String salesman;//营业员
	private String stime;//开始时间
	private String etime;//结束时间
	private String Counter;//部门
	
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getCounter() {
		return Counter;
	}
	public void setCounter(String counter) {
		Counter = counter;
	}

}
