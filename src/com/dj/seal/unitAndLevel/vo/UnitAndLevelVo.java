package com.dj.seal.unitAndLevel.vo;

public class UnitAndLevelVo {
	private String unitno;//机构号
	private Number levelno;//级别号
	private String bak1;//bak1-bak2为备用字段
	private String bak2;
	private String bak3;

	public UnitAndLevelVo() {
		super();
	}
	
	public UnitAndLevelVo(String unitNo, Number levelNo) {
		super();
		this.unitno = unitNo;
		this.levelno = levelNo;
	}

	public String getUnitno() {
		return unitno;
	}

	public void setUnitno(String unitno) {
		this.unitno = unitno;
	}

	public Number getLevelno() {
		return levelno;
	}

	public void setLevelno(Number levelno) {
		this.levelno = levelno;
	}

	public String getBak1() {
		return bak1;
	}
	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}
	public String getBak2() {
		return bak2;
	}
	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}
	public String getBak3() {
		return bak3;
	}
	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}
	
	
}
