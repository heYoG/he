package com.dj.seal.hotel.vo;

import java.sql.Timestamp;
import java.util.List;


import com.dj.seal.util.dao.BasePO;


public class RecordVO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7361062152056412664L;


	private String cid;// 编号
	private String cip;// ip
	private Timestamp ccreatetime;// 单据创建时间
	private Timestamp cuploadtime;// 单据上传时间
	private String mtplid;// 模板编号
	private String createuserid;// 创建用户
	private String uploaduserid;// 上传用户
	private String cfilefilename;// 文件展示名称
	private String cdata;// 文件保存
	private String cstatus; // 状态（0作废 1正常）
	
	private String hasdone;// 是否已处理(0为需要处理且未处理，1为已处理)
	private String deptno;//所属部门
	private String deptname;//部门名称
	
	private String createUserName;//创建单据用户名称
	private String roomId;//房间号
	private String guestname1;//客户姓名1
	private String indate;//入住时间
	private String outdate;//离店时间
	private String totalmoney;//消费金额
	private String deptId;//部门编号
	
	private List<ShowAffiliateValue> affiliateList;
	private String mtplname;// 模板名称
	
	private String cardNo;//证件号码
	
	private String cname; //属性名
	private String cvalue; //属性值
	
	
	private String agreementid;//协议ID
	private String haveidcard;//是否包含证件 （0为否，1为是）
	private String haveattach;//是否包含附件照 （0为否，1为是）
	private String checkstatus;//稽核状态（1为待一级稽核，2一级稽核通过待二级稽核，3二级稽核通过待三级稽核，4三级稽核通过待四级稽核，5四级稽核通过)（a为一级稽核未通过，b为二级稽核未通过，c为三级稽核未通过，d为四级稽核未通过）
	private String jiheuser;// 稽核人
	private Timestamp jihetime;// 稽核时间
	private String jihereason;// 稽核不通过原因
	private String remarks;// 备用字段
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCvalue() {
		return cvalue;
	}
	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}
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
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public String getGuestname1() {
		return guestname1;
	}
	public void setGuestname1(String guestname1) {
		this.guestname1 = guestname1;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	public String getOutdate() {
		return outdate;
	}
	public void setOutdate(String outdate) {
		this.outdate = outdate;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(String totalmoney) {
		this.totalmoney = totalmoney;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public List<ShowAffiliateValue> getAffiliateList() {
		return affiliateList;
	}
	public void setAffiliateList(List<ShowAffiliateValue> affiliateList) {
		this.affiliateList = affiliateList;
	}
	public String getMtplname() {
		return mtplname;
	}
	public void setMtplname(String mtplname) {
		this.mtplname = mtplname;
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
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
}
	
	
	