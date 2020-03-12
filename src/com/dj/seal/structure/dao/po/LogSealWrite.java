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
public class LogSealWrite extends BasePO{

	private static final long serialVersionUID = 1L;
	private Integer log_id;				//日志ID
	private String user_id;				//用户名
	private String user_name;			//用户姓名
	private Timestamp opr_time;			//操作时间
	private String opr_ip;				//操作IP
	private String opr_num;				//写入次数
	private String opr_sn;				//keySN序列号
	private String seal_id;				//印章id
	private String key_desc;            //写入描述信息
	
	
	
	public String getKey_desc() {
		return key_desc;
	}

	public void setKey_desc(String key_desc) {
		this.key_desc = key_desc;
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
	public String getOpr_num() {
		return opr_num;
	}

	public void setOpr_num(String opr_num) {
		this.opr_num = opr_num;
	}

	public String getOpr_sn() {
		return opr_sn;
	}

	public void setOpr_sn(String opr_sn) {
		this.opr_sn = opr_sn;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LogSealWrite other = (LogSealWrite) obj;
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

	public String getSeal_id() {
		return seal_id;
	}

	public void setSeal_id(String seal_id) {
		this.seal_id = seal_id;
	}

}
