package com.dj.seal.hotel.vo;

import java.sql.Timestamp;
import com.dj.seal.util.dao.BasePO;


public class ScannerRecordVO extends BasePO {
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
	
	private String id;//唯一编号
	private String filename;//文档名称
	private String createuserid;//创建ID
	private String deptno;//创建部门
	private Timestamp createtime;//创建时间
	private String ip;//创建IP
	private String context;//OCR识别的文字内容
	private String filepath;//文件路径
	

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
	
	
	