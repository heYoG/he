package com.dj.seal.hotel.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**
 * 单据
 */
public class RecordPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6954630474162941534L;
	private String cid;// 编号
	private String cip;// ip
	private Timestamp ccreatetime;// 单据创建时间
	private Timestamp cuploadtime;// 单据上传时间
	private String mtplid;// 模板编号
	private String createuserid;// 创建用户
	private String uploaduserid;// 上传用户
	private String cfilefilename;// 文件展示名称
	private String cdata;// 文件保存  现用作文件保存路径 doc/hotelDocs/之后
	private String cstatus; // 状态（0作废 1正常）
	
	private String hasdone;// 是否已处理(0为需要处理且未处理，1为已处理)
	private String deptno;//所属部门
	
	private String agreementid;//协议ID
	private String haveidcard;//是否包含证件 （0为否，1为是）
	private String haveattach;//是否包含附件照 （0为否，1为是）
	private String checkstatus;//稽核状态（1为待一级稽核，2一级稽核通过待二级稽核，3二级稽核通过待三级稽核，4三级稽核通过待四级稽核，5四级稽核通过)（a为一级稽核未通过，b为二级稽核未通过，c为三级稽核未通过，d为四级稽核未通过）
	private String jiheuser;// 稽核人
	private Timestamp jihetime;// 稽核时间
	private String jihereason;// 稽核不通过原因
	private String remarks;// 备用字段
	
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
	public String getHasdone() {
		return hasdone;
	}
	public void setHasdone(String hasdone) {
		this.hasdone = hasdone;
	}
	public String getDeptno() {
		return deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	
	public String getAgreementid() {
		return agreementid;
	}
	public void setAgreementid(String agreementid) {
		this.agreementid = agreementid;
	}
	public String getHaveidcard() {
		return haveidcard;
	}
	public void setHaveidcard(String haveidcard) {
		this.haveidcard = haveidcard;
	}
	
	public String getHaveattach() {
		return haveattach;
	}
	public void setHaveattach(String haveattach) {
		this.haveattach = haveattach;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	public String getJiheuser() {
		return jiheuser;
	}
	public void setJiheuser(String jiheuser) {
		this.jiheuser = jiheuser;
	}
	public Timestamp getJihetime() {
		return jihetime;
	}
	public void setJihetime(Timestamp jihetime) {
		this.jihetime = jihetime;
	}
	public String getJihereason() {
		return jihereason;
	}
	public void setJihereason(String jihereason) {
		this.jihereason = jihereason;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
	
}