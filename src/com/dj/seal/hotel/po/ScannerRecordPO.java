package com.dj.seal.hotel.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**
 * ����
 */
public class ScannerRecordPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6954630474162941534L;

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
	
	private String id;//Ψһ���
	private String filename;//�ĵ�����
	private String createuserid;//����ID
	private String deptno;//��������
	private Timestamp createtime;//����ʱ��
	private String ip;//����IP
	private String context;//OCRʶ�����������
	private String filepath;//�ļ�·��
	

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	
	
	
}