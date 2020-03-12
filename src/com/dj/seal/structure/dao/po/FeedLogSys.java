package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * @description 评价系统日志表对应的po
 * @author zw
 * @since 2016-03-07
 * 
 */
public class FeedLogSys extends BasePO {

	private static final long serialVersionUID = 1L;
	private Integer log_id; // 日志ID
	private String user_id; // 用户名
	private Timestamp opr_time; // 操作时间
	private String feed_id; // 票号ID
	private String feed_ret; // 评价结果
	private String reason; // 评价原因
	private String log_remark; // 返回记录

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getLog_remark() {
		return log_remark;
	}

	public void setLog_remark(String log_remark) {
		this.log_remark = log_remark;
	}

	public String getFeed_id() {
		return feed_id;
	}

	public void setFeed_id(String feed_id) {
		this.feed_id = feed_id;
	}

	public String getFeed_ret() {
		return feed_ret;
	}

	public void setFeed_ret(String feed_ret) {
		this.feed_ret = feed_ret;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FeedLogSys other = (FeedLogSys) obj;
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

}
