package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

public class HuiZhiLog {
	private static final long serialVersionUID = 1L;
	private String user_id; // 用户名
	private String caseseqid; // 流水号
	private Timestamp opr_time; // 操作时间
	private String status; // 盖章结果
	private String file_name; // 文档名称
	private String seal_type;
	private String dept_no;
	private String seal_name;
	private String dept_name;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCaseseqid() {
		return caseseqid;
	}

	public void setCaseseqid(String caseseqid) {
		this.caseseqid = caseseqid;
	}

	public Timestamp getOpr_time() {
		return opr_time;
	}

	public void setOpr_time(Timestamp opr_time) {
		this.opr_time = opr_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getSeal_type() {
		return seal_type;
	}

	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getSeal_name() {
		return seal_name;
	}

	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

}
