package vo.emailVo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import vo.userVo.UserVo;

@Component(value="emailVo")//指定EmailVo的bean值为emailVo,等价于配置文件<bean id="emailVo" class="...">
public class EmailVo {
	private int id;//邮件id
	private String sender;//发送人
	private Timestamp sendTime;//发送时间
	private String addressee;//收件人
	private String theme;//主题
	private String text;//发送内容
	private String accessory;//附件,上传后的文件保存地址
	private int status;//状态 0:发送失败；1:发送成功
	private int type;//邮件类型 0：收件箱 1：发送箱 2：草稿箱 3：已删除邮件
	private UserVo user;//hibernate中多对一关系的一方——用户类
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public String getAddressee() {
		return addressee;
	}
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAccessory() {
		return accessory;
	}
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public UserVo getUser() {
		return user;
	}
	public void setUser(UserVo user) {
		this.user = user;
	}
}
