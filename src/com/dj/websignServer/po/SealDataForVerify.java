package com.dj.websignServer.po;
//po类
public class SealDataForVerify {
	
	private String p7SignRes;//签章结果（单个完整的）
	private String oridata;//原始数据
	private String sealName;//签章名称
	private String dataToVerify;//待验证数据
	private String sealData;//印章数据
	private String pngData;//印章对应的图片数据
	
	public String getSealData() {
		return sealData;
	}
	public void setSealData(String sealData) {
		this.sealData = sealData;
	}
	public String getPngData() {
		return pngData;
	}
	public void setPngData(String pngData) {
		this.pngData = pngData;
	}
	public String getP7SignRes() {
		return p7SignRes;
	}
	public void setP7SignRes(String signRes) {
		p7SignRes = signRes;
	}
	public String getOridata() {
		return oridata;
	}
	public void setOridata(String oridata) {
		this.oridata = oridata;
	}
	public String getSealName() {
		return sealName;
	}
	public void setSealName(String sealName) {
		this.sealName = sealName;
	}
	public String getDataToVerify() {
		return dataToVerify;
	}
	public void setDataToVerify(String dataToVerify) {
		this.dataToVerify = dataToVerify;
	}
	
	

}
