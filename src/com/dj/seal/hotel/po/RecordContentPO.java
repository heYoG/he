package com.dj.seal.hotel.po;


import com.dj.seal.util.dao.BasePO;

/**
 * ���ݸ�����
 */
public class RecordContentPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rid;// ��Ӧ����ID
	private String content;//��������
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
}