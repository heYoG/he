package com.dj.hotelApi.po;

import java.util.List;


public class HotelFilePo {

	private String billid;//受理单标识
	private String model_id;//对应的模板ID
	private List<BusiInfo> datas;//业务数据
	
	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	public List<BusiInfo> getDatas() {
		return datas;
	}
	public void setDatas(List<BusiInfo> datas) {
		this.datas = datas;
	}
	public String getBillid() {
		return billid;
	}
	public void setBillid(String billid) {
		this.billid = billid;
	}
}
