package com.dj.seal.hotel.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**
 * ����
 */
public class RecordPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6954630474162941534L;
	private String cid;// ���
	private String cip;// ip
	private Timestamp ccreatetime;// ���ݴ���ʱ��
	private Timestamp cuploadtime;// �����ϴ�ʱ��
	private String mtplid;// ģ����
	private String createuserid;// �����û�
	private String uploaduserid;// �ϴ��û�
	private String cfilefilename;// �ļ�չʾ����
	private String cdata;// �ļ�����  �������ļ�����·�� doc/hotelDocs/֮��
	private String cstatus; // ״̬��0���� 1������
	
	private String hasdone;// �Ƿ��Ѵ���(0Ϊ��Ҫ������δ����1Ϊ�Ѵ���)
	private String deptno;//��������
	
	private String agreementid;//Э��ID
	private String haveidcard;//�Ƿ����֤�� ��0Ϊ��1Ϊ�ǣ�
	private String haveattach;//�Ƿ���������� ��0Ϊ��1Ϊ�ǣ�
	private String checkstatus;//����״̬��1Ϊ��һ�����ˣ�2һ������ͨ�����������ˣ�3��������ͨ�����������ˣ�4��������ͨ�����ļ����ˣ�5�ļ�����ͨ��)��aΪһ������δͨ����bΪ��������δͨ����cΪ��������δͨ����dΪ�ļ�����δͨ����
	private String jiheuser;// ������
	private Timestamp jihetime;// ����ʱ��
	private String jihereason;// ���˲�ͨ��ԭ��
	private String remarks;// �����ֶ�
	
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