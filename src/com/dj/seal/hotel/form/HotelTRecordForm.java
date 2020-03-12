package com.dj.seal.hotel.form;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

public class HotelTRecordForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cid;// 编号
	private String cip;// ip
	private Timestamp ccreatetime;// 单据创建时间
	private Timestamp cuploadtime;// 单据上传时间
	private String mtplid;// 模板编号
	private String createuserid;// 创建用户
	private String uploaduserid;// 上传用户
	private String cfilefilename;// 文件展示名称
	private String cdata;// 文件保存
	private String cstatus; // 状态
	
	public HotelTRecordForm(){}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public Timestamp getCcreatetime() {
		return ccreatetime;
	}

	public void setCcreatetime(Timestamp ccreatetime) {
		this.ccreatetime = ccreatetime;
	}

	public Timestamp getCuploadtime() {
		return cuploadtime;
	}

	public void setCuploadtime(Timestamp cuploadtime) {
		this.cuploadtime = cuploadtime;
	}

	public String getMtplid() {
		return mtplid;
	}

	public void setMtplid(String mtplid) {
		this.mtplid = mtplid;
	}

	public String getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public String getUploaduserid() {
		return uploaduserid;
	}

	public void setUploaduserid(String uploaduserid) {
		this.uploaduserid = uploaduserid;
	}

	public String getCfilefilename() {
		return cfilefilename;
	}

	public void setCfilefilename(String cfilefilename) {
		this.cfilefilename = cfilefilename;
	}

	public String getCdata() {
		return cdata;
	}

	public void setCdata(String cdata) {
		this.cdata = cdata;
	}

	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

	public HotelTRecordForm(String cid, String cip, Timestamp ccreatetime,
			Timestamp cuploadtime, String mtplid, String createuserid,
			String uploaduserid, String cfilefilename, String cdata,
			String cstatus) {
		super();
		this.cid = cid;
		this.cip = cip;
		this.ccreatetime = ccreatetime;
		this.cuploadtime = cuploadtime;
		this.mtplid = mtplid;
		this.createuserid = createuserid;
		this.uploaduserid = uploaduserid;
		this.cfilefilename = cfilefilename;
		this.cdata = cdata;
		this.cstatus = cstatus;
	}
	
	

}
