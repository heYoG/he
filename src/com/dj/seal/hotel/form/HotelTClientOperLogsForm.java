package com.dj.seal.hotel.form;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

public class HotelTClientOperLogsForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5780027825416847079L;
	
	private String cid;// 通过UUID生成
	private String operuserid;// 用户ID
	private Timestamp copertime;// 操作时间
	private String cip;// IP地址
	private String cmac;// MAC地址
	private String opertype;// 操作类型 (1登录，2上传文件)
	private String objid;// 对象ID
	private String objname;// 对象名称
	private String result;// 结果 (0成功，1失败)
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getOperuserid() {
		return operuserid;
	}
	public void setOperuserid(String operuserid) {
		this.operuserid = operuserid;
	}
	
	public Timestamp getCopertime() {
		return copertime;
	}
	public void setCopertime(Timestamp copertime) {
		this.copertime = copertime;
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
	public String getOpertype() {
		return opertype;
	}
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	public String getObjname() {
		return objname;
	}
	public void setObjname(String objname) {
		this.objname = objname;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	

	

}
