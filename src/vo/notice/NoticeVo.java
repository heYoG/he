package vo.notice;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import vo.userVo.UserVo;

@Component 
public class NoticeVo {
	private int id;//唯一标识
	private String theme ;//主题
	private String sender;//发送人
	private Timestamp createTime;//发送时间
	private String text;//发送内容
	private int sendStatus;//发送状态 1:发送成功;0:发送失败
	private int noticeStatus;//公告状态 1:正常;0:虚拟删除
	private UserVo user;//多对一关系中的一方变量
	
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
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	public int getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(int noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public UserVo getUser() {
		return user;
	}
	public void setUser(UserVo user) {
		this.user = user;
	}
	
}
