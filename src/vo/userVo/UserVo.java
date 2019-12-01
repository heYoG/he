package vo.userVo;

import java.util.*;

import vo.deptVo.DeptVo;
import vo.fileVo.FileManageVo;

public class UserVo {
	private int id;//用户id
	private String userNo;//用户号
	private String pwd;//用户密码
	private int age;//年龄
	private String userName;//真实姓名
	private AuthorityVo av;//多对一中一方的权限对象
	private DeptVo dept;//多对一中一方的部门对象
	private Set<FileManageVo> fileVo=new HashSet<FileManageVo>();//一对多中多方的文件对象
	private String status;//用户状态,1：正常,0：注销
	
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
	public AuthorityVo getAv() {
		return av;
	}
	public void setAv(AuthorityVo av) {
		this.av = av;
	}
	public DeptVo getDept() {
		return dept;
	}
	public void setDept(DeptVo dept) {
		this.dept = dept;
	}
	
	public Set<FileManageVo> getFileVo() {
		return fileVo;
	}
	public void setFileVo(Set<FileManageVo> fileVo) {
		this.fileVo = fileVo;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserVo() {
		super();
	}
	public UserVo(int id, String userNo, String pwd, int age, String userName) {
		super();
		this.id = id;
		this.userNo = userNo;
		this.pwd = pwd;
		this.age = age;
		this.userName = userName;
	}
}
