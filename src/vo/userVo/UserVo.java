package vo.userVo;

import java.util.*;

import org.springframework.stereotype.Component;

import vo.logVo.LogVo;
import vo.notice.NoticeVo;

@Component
public class UserVo {
	private int id;//用户id
	private String userNo;//用户号
	private String pwd;//用户密码
	private int age;//年龄
	private String userName;//真实姓名
	private String status;//用户状态,1：正常,0：注销
	private List<NoticeVo> noticeVo;//=new HashSet<NoticeVo>();//一对多的多方变量
	private List<LogVo> log;//=new HashSet<LogVo>();//一对多的多方变量

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String user) {
		this.userNo = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//	public Set<NoticeVo> getNoticeVo() {
//		return noticeVo;
//	}
//	public void setNoticeVo(Set<NoticeVo> noticeVo) {
//		this.noticeVo = noticeVo;
//	}
//	public Set<LogVo> getLog() {
//		return log;
//	}
//	public void setLog(Set<LogVo> log) {
//		this.log = log;
//	}
	public List<NoticeVo> getNoticeVo() {
		return noticeVo;
	}
	public void setNoticeVo(List<NoticeVo> noticeVo) {
		this.noticeVo = noticeVo;
	}
	public List<LogVo> getLog() {
		return log;
	}
	public void setLog(List<LogVo> log) {
		this.log = log;
	}
	

}
