package com.dj.seal.hotel.vo;


public class HotelTClientOperLogVO {
		
	
	private String cid;// 通过UUID生成
	private String operUserId;// 用户ID
	private String coperTime;// 操作时间
	private String cip;// IP地址
	private String cmac;// MAC地址
	private String operType;// 操作类型 (1登录，2上传文件)
	private String objId;// 对象ID
	private String objName;// 对象名称
	private String result;// 结果 (0成功，1失败)
	
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getOperUserId() {
		return operUserId;
	}
	public void setOperUserId(String operUserId) {
		this.operUserId = operUserId;
	}
	
	public String getCip() {
		return cip;
	}
	public void setCip(String cip) {
		this.cip = cip;
	}
	public String getCmac() {
		return cmac;
	}
	public void setCmac(String cmac) {
		this.cmac = cmac;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCoperTime() {
		return coperTime;
	}
	public void setCoperTime(String coperTime) {
		this.coperTime = coperTime;
	}
}
