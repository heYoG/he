package vo.deptVo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import vo.userVo.UserVo;

@Component
public class DeptVo {
	private int deptID;
	private String deptName;
	private int flag;//是否有下级标识,0无下级，1有下级
	private Set<UserVo> user=new HashSet<UserVo>();
	
	public DeptVo() {
		super();
	}
	
	public DeptVo(int deptID, String deptName, int flag) {
		super();
		this.deptID = deptID;
		this.deptName = deptName;
		this.flag = flag;
	}

	public int getDeptID() {
		return deptID;
	}
	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Set<UserVo> getUser() {
		return user;
	}

	public void setUser(Set<UserVo> user) {
		this.user = user;
	}
}
