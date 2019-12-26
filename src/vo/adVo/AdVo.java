package vo.adVo;

import java.sql.Timestamp;

import vo.userVo.UserVo;


/*广告javabean*/
public class AdVo {
	private int id;
	private String adName;
	private String saveName;
	private long adSize;
	private Timestamp uploadtime;
	private String operator;
	private int user_id;
	private UserVo user;
	
	public AdVo() {
		super();
	}
	
	public AdVo(int id, String adName, String saveName, long adSize, Timestamp uploadtime, String operator,int user_id) {
		super();
		this.id = id;
		this.adName = adName;
		this.saveName = saveName;
		this.adSize = adSize;
		this.uploadtime = uploadtime;
		this.operator = operator;
		this.user_id=user_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public long getAdSize() {
		return adSize;
	}
	public void setAdSize(long adSize) {
		this.adSize = adSize;
	}
	public Timestamp getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}
	
}
