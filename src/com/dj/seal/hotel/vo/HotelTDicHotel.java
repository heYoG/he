package com.dj.seal.hotel.vo;

/**
 * 数据字典
 * 
 *
 */
public class HotelTDicHotel {
	
	private String cid;//通过UUID生成
	private String cname;//名称
	private String cshowname;//值
	private String cdatatype;//字符类型(整数、小数、大写金额、日期等)
	private String sys;//是否为系统内置。0内置，1非内置
	
	private String c_status;//是否显示。0显示，1隐藏
	private int c_sort;//排序号码
	
	
	public String getC_status() {
		return c_status;
	}
	public void setC_status(String cStatus) {
		c_status = cStatus;
	}
	public int getC_sort() {
		return c_sort;
	}
	public void setC_sort(int cSort) {
		c_sort = cSort;
	}

	
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCshowname() {
		return cshowname;
	}
	public void setCshowname(String cshowname) {
		this.cshowname = cshowname;
	}
	public String getCdatatype() {
		return cdatatype;
	}
	public void setCdatatype(String cdatatype) {
		this.cdatatype = cdatatype;
	}
	public String getSys() {
		return sys;
	}
	public void setSys(String sys) {
		this.sys = sys;
	}
	

}
