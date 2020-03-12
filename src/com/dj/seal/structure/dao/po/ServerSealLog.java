package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * @description 印章写入key对应的po
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class ServerSealLog extends BasePO {

	private static final long serialVersionUID = 1L;
	private Integer log_id; // 日志ID
	private String user_id; // 用户名
	private String caseseqid; // 流水号
	private String server_id; // 应用系统id
	private Timestamp opr_time; // 操作时间
	private String opr_ip; // 操作IP
	private String seal_status; // 盖章状态
	private String file_name; // 文档名称
	private String opr_msg; // 盖章描述信息
	private String seal_name;
	private String seal_type;
	private String dept_no;
	private String dept_name;

	public String getCaseseqid() {
		return caseseqid;
	}

	public void setCaseseqid(String caseseqid) {
		this.caseseqid = caseseqid;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}

	public Timestamp getOpr_time() {
		return opr_time;
	}

	public void setOpr_time(Timestamp opr_time) {
		this.opr_time = opr_time;
	}

	public String getOpr_ip() {
		return opr_ip;
	}

	public void setOpr_ip(String opr_ip) {
		this.opr_ip = opr_ip;
	}

	public String getSeal_status() {
		return seal_status;
	}

	public void setSeal_status(String seal_status) {
		this.seal_status = seal_status;
	}

	public String getOpr_msg() {
		return opr_msg;
	}

	public void setOpr_msg(String opr_msg) {
		this.opr_msg = opr_msg;
	}

	public String getSeal_name() {
		return seal_name;
	}

	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((log_id == null) ? 0 : log_id.hashCode());
		result = prime * result + ((opr_ip == null) ? 0 : opr_ip.hashCode());
		result = prime * result
				+ ((opr_time == null) ? 0 : opr_time.hashCode());
		result = prime * result
				+ ((seal_status == null) ? 0 : seal_status.hashCode());
		result = prime * result
				+ ((server_id == null) ? 0 : server_id.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServerSealLog other = (ServerSealLog) obj;
		if (log_id == null) {
			if (other.log_id != null)
				return false;
		} else if (!log_id.equals(other.log_id))
			return false;
		if (opr_ip == null) {
			if (other.opr_ip != null)
				return false;
		} else if (!opr_ip.equals(other.opr_ip))
			return false;
		if (opr_time == null) {
			if (other.opr_time != null)
				return false;
		} else if (!opr_time.equals(other.opr_time))
			return false;
		if (seal_status == null) {
			if (other.seal_status != null)
				return false;
		} else if (!seal_status.equals(other.seal_status))
			return false;
		if (server_id == null) {
			if (other.server_id != null)
				return false;
		} else if (!server_id.equals(other.server_id))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}

}
