package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * @description 系统日志表对应的po 
 * @author oyxy
 * @since 2009-11-2
 *
 */
public class LogSys extends BasePO{

	private static final long serialVersionUID = 1L;
	private Integer log_id;				//日志ID
	private String user_id;				//用户名
	private String user_name;			//用户姓名
	private Timestamp opr_time;			//操作时间
	private String opr_ip;				//操作IP
	private String opr_type;			//操作菜单
	private String log_remark;			//日志记录
	private String selectType;//选择类型 按日、按月、自定义
	private String selectTime; //选择的时间
	
	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
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

	public String getOpr_type() {
		return opr_type;
	}

	public void setOpr_type(String opr_type) {
		this.opr_type = opr_type;
	}

	public String getLog_remark() {
		return log_remark;
	}

	public void setLog_remark(String log_remark) {
		this.log_remark = log_remark;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LogSys other = (LogSys) obj;
		if (log_id == null) {
			if (other.log_id != null)
				return false;
		} else if (!log_id.equals(other.log_id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((log_id == null) ? 0 : log_id.hashCode());
		return result;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
