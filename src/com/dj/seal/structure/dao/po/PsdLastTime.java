package com.dj.seal.structure.dao.po;

/**
 * 杂类参数实例
 * @author WB000520
 *
 */

public class PsdLastTime {
	private String cansmc; //参数名称
	private String canshz; //参数值
	private String canssj; //参数数据
	private String bycssj; //备用数据
	private String  shming; //参数说明
	private Number  shjnch; //时间戳
	private String  jiluzt; //记录状态
	private String  remark1; //备用字段1
	private String  remark2;//备用字段2
	
	public String getCansmc() {
		return cansmc;
	}
	public void setCansmc(String cansmc) {
		this.cansmc = cansmc;
	}
	public String getCanshz() {
		return canshz;
	}
	public void setCanshz(String canshz) {
		this.canshz = canshz;
	}
	public String getCanssj() {
		return canssj;
	}
	public void setCanssj(String canssj) {
		this.canssj = canssj;
	}
	public String getBycssj() {
		return bycssj;
	}
	public void setBycssj(String bycssj) {
		this.bycssj = bycssj;
	}
	public String getShming() {
		return shming;
	}
	public void setShming(String shming) {
		this.shming = shming;
	}
	
	public Number getShjnch() {
		return shjnch;
	}
	public void setShjnch(Number shjnch) {
		this.shjnch = shjnch;
	}
	public String getJiluzt() {
		return jiluzt;
	}
	public void setJiluzt(String jiluzt) {
		this.jiluzt = jiluzt;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	

}
