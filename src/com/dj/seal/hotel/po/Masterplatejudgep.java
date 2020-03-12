package com.dj.seal.hotel.po;

/**
 * 判定条件
 * @author Administrator
 *
 */
public class Masterplatejudgep {
	private String c_id;  //通过UUID生成
	private String c_name;  //判定属性
	private String c_value; //属性名
	private String master_platecid;  //模版ID
	private String c_valuetype;  //特定值
//	private int c_width;//内容宽度
//	private int c_height;//内容高度
//	private int c_position;//偏移量
	
//	public int getC_position() {
//		return c_position;
//	}
//	public void setC_position(int c_position) {
//		this.c_position = c_position;
//	}
//	public int getC_width() {
//		return c_width;
//	}
//	public void setC_width(int c_width) {
//		this.c_width = c_width;
//	}
//	public int getC_height() {
//		return c_height;
//	}
//	public void setC_height(int c_height) {
//		this.c_height = c_height;
//	}
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
	
	
}
