package vo.userVo;

import java.util.HashSet;
import java.util.Set;

public class AuthorityVo {
	private int id;
	private int authVal;
	private String authName;
	private Set<UserVo> user=new HashSet<UserVo>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAuthVal() {
		return authVal;
	}
	public void setAuthVal(int authVal) {
		this.authVal = authVal;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	public Set<UserVo> getUser() {
		return user;
	}
	public void setUser(Set<UserVo> user) {
		this.user = user;
	}
	public AuthorityVo(int id, int authVal, String authName) {
		super();
		this.id = id;
		this.authVal = authVal;
		this.authName = authName;
	}
	public AuthorityVo() {
		super();
	}
	
}
