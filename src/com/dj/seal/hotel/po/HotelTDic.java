package com.dj.seal.hotel.po;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * 数据字典
 *
 */
public class HotelTDic extends BasePO {
	private static final long serialVersionUID = 1L;
	private String cid;//通过UUID生成
	private String cname;//名称
	private String cshowname;//值
	private String cdatatype;//字符类型(整数、小数、大写金额、日期等)
	private String sys;//是否为系统内置。0内置，1非内置
	
	private String c_status;//是否显示。0显示，1隐藏
	private int c_sort;//显示顺序
	
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

	public HotelTDic(){
		
	}
	
	public HotelTDic(String cid, String cname, String cshowname,
			String cdatatype, String sys,String c_status,int c_sort) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.cshowname = cshowname;
		this.cdatatype = cdatatype;
		this.sys = sys;
		this.c_status = c_status;
		this.c_sort = c_sort;
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

}
