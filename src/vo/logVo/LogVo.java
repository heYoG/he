package vo.logVo;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vo.userVo.UserVo;


public class LogVo {
	private int id;//主键,自增
	private String theme;//操作事项
	private Timestamp operateTime;//操作时间
	private int status;//操作结果状态	0:失败，1:成功
	private UserVo user;//操作者
	
	static Logger log=LogManager.getLogger(LogVo.class.getName());
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Timestamp getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}
	
}
