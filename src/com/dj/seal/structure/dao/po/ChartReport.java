package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class ChartReport extends BasePO {
	
	private String id;//标号ID
	private String businessType;//业务类型
	private String salesman;//营业员
	private String money;//金额
	private String time;//时间
	private String Counter;//部门
	private String satisfaction;//满意度评价 0非常满意 1满意 2一般 3不满意

	public String getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCounter() {
		return Counter;
	}

	public void setCounter(String counter) {
		Counter = counter;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
