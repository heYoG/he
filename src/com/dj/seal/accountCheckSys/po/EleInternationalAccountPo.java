package com.dj.seal.accountCheckSys.po;


import java.sql.Timestamp;
import java.util.Date;

/**
 * 电子对账、国际结算、现管、移动银行、微信银行
 * 属性名称不能使用驼峰格式，setPo方法会将get方法拼写转为小写，调用方法时驼峰格式无法找到对应方法
 * 
 * */
public class EleInternationalAccountPo {
	private String channelNo;//渠道号
	private String orgConsumerSeqNo;//流水号
	private String tranNo;//交易码
	private Timestamp tranDate;//交易日期
	private String valcode;//验证码
	private String operator;//操作人名字
	private String tranOrg;//交易机构号
	private String tranOrgName;//交易机构名称
	private String tranName;//交易名称
	private String sealPictureData;//印章图片数据
	private Timestamp createTime;//创建时间
	/*移动银行、微信银行新增*/
	private String accountNumber;//账户账号
	private String accountName;//账户名称
	private Timestamp startDate;//查询开始日期
	private Timestamp endDate;//查询结束日期
	private Timestamp accumulationCreateDate;//账单生成日期
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getOrgConsumerSeqNo() {
		return orgConsumerSeqNo;
	}
	public void setOrgConsumerSeqNo(String orgConsumerSeqNo) {
		this.orgConsumerSeqNo = orgConsumerSeqNo;
	}
	public String getTranNo() {
		return tranNo;
	}
	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}
	
	public Timestamp getTranDate() {
		return tranDate;
	}
	public void setTranDate(Timestamp tranDate) {
		this.tranDate = tranDate;
	}
	public String getValcode() {
		return valcode;
	}
	public void setValcode(String valcode) {
		this.valcode = valcode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTranOrg() {
		return tranOrg;
	}
	public void setTranOrg(String tranOrg) {
		this.tranOrg = tranOrg;
	}
	public String getTranOrgName() {
		return tranOrgName;
	}
	public void setTranOrgName(String tranOrgName) {
		this.tranOrgName = tranOrgName;
	}
	public String getTranName() {
		return tranName;
	}
	public void setTranName(String tranName) {
		this.tranName = tranName;
	}
	public String getSealPictureData() {
		return sealPictureData;
	}
	public void setSealPictureData(String sealPictureData) {
		this.sealPictureData = sealPictureData;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Timestamp getAccumulationCreateDate() {
		return accumulationCreateDate;
	}
	public void setAccumulationCreateDate(Timestamp accumulationCreateDate) {
		this.accumulationCreateDate = accumulationCreateDate;
	}
	
	
}
