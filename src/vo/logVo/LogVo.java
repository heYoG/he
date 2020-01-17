package vo.logVo;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class LogVo {
	private int id;//主键,自增
	private String theme;//操作事项
	private Timestamp operateTime;//操作时间
	private int status;//操作结果状态	0:失败，1:成功
	
	static Logger log=LoggerFactory.getLogger(LogVo.class);
	public LogVo() {
		super();
//		log.info("日志实例已初始化...");
		System.out.println("日志实例已初始化...");
	}
	
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
	
}
