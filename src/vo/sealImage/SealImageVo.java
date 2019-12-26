package vo.sealImage;

import java.sql.Blob;
import java.sql.Timestamp;

import vo.userVo.UserVo;

public class SealImageVo {
	private int imgId;//印模id
	private String originalName;//印模原名称
	private String saveName;//印模保存名称
	private long imgSize;//图片大小(byte)
	private Timestamp uploadtime;//图片上传时间
	private String operator;//操作人员
	private int user_id;//从表外键,对应主表userTable的id
	private Blob imgData;//印模数据
	private int status;//印模状态 0:已注销；1:正常；2:待审批
	private UserVo user;
	
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public long getImgSize() {
		return imgSize;
	}
	public void setImgSize(long imgSize) {
		this.imgSize = imgSize;
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
	public Blob getImgData() {
		return imgData;
	}
	public void setImgData(Blob imgData) {
		this.imgData = imgData;
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
