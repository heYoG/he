package com.dj.seal.hotel.vo;

/**
 * 判定条件
 * @author Administrator
 *
 */
public class Masterplatejudgev {
	private String c_id;  //通过UUID生成
	private String c_name;  //判定属性
	private String c_value; //属性名
	private String master_platecid;  //模版ID
	private String c_valuetype;  //特定值
	
	private String masterplate_name;  //模版名称
	
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String cId) {
		c_id = cId;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String cName) {
		c_name = cName;
	}
	public String getC_value() {
		return c_value;
	}
	public void setC_value(String cValue) {
		c_value = cValue;
	}
	public String getMaster_platecid() {
		return master_platecid;
	}
	public void setMaster_platecid(String masterPlatecid) {
		master_platecid = masterPlatecid;
	}
	public String getC_valuetype() {
		return c_valuetype;
	}
	public void setC_valuetype(String cValuetype) {
		c_valuetype = cValuetype;
	}
	public String getMasterplate_name() {
		return masterplate_name;
	}
	public void setMasterplate_name(String masterplate_name) {
		this.masterplate_name = masterplate_name;
	}
	
	
}
