package com.dj.seal.hotel.po;


import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**
 * ģ��汾�ű�
 * 
 */
public class VersionPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8069557264621596226L;
	private String cid;// ���
	private String cversionno;// �汾��
	private Timestamp ccreatetime;// ����ʱ��
	private String edithtmlcode;// �༭ʱ��
	private String printhtmlcode;// ��ӡʱ��
	private String masterplateid;// ģ����
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCversionno() {
		return cversionno;
	}
	public void setCversionno(String cversionno) {
		this.cversionno = cversionno;
	}
	public Timestamp getCcreatetime() {
		return ccreatetime;
	}
	public void setCcreatetime(Timestamp ccreatetime) {
		this.ccreatetime = ccreatetime;
	}
	public String getEdithtmlcode() {
		return edithtmlcode;
	}
	public void setEdithtmlcode(String edithtmlcode) {
		this.edithtmlcode = edithtmlcode;
	}
	public String getPrinthtmlcode() {
		return printhtmlcode;
	}
	public void setPrinthtmlcode(String printhtmlcode) {
		this.printhtmlcode = printhtmlcode;
	}
	public String getMasterplateid() {
		return masterplateid;
	}
	public void setMasterplateid(String masterplateid) {
		this.masterplateid = masterplateid;
	}
	public static long getSerialVersionUID() {
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
	
	
}