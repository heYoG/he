package com.dj.seal.hotel.po;


import com.dj.seal.util.dao.BasePO;

/**
 * ���ݸ�����
 */
public class RecordAffiliatePO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// ���
	private String cname;// ����
	private String cvalue;// ����ֵ
	private String recordid;// ���ݼ�¼���
	
	public RecordAffiliatePO(){}
	
	
	public RecordAffiliatePO(String id, String cname, String cvalue,
			String recordId) {
		super();
		this.id = id;
		this.cname = cname;
		this.cvalue = cvalue;
		this.recordid = recordId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public String getRecordid() {
		return recordid;
	}


	public void setRecordid(String recordid) {
		this.recordid = recordid;
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