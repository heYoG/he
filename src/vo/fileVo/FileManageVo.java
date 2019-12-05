package vo.fileVo;

import java.sql.Blob;
import java.sql.Timestamp;

import util.CommenClass;
import vo.userVo.UserVo;


public class FileManageVo {
	private int id; //文件唯一标识
	private String myFile; //文件保存路径
	private Blob fileData; //文件大小
	private Timestamp uploadTime; //文件上传时间
	private String operator; //文件上传者
	private int status; //文件状态(0:已删除,1:正常)
	private UserVo user;
	private String originalFileName;//文件原名
	private String fileSize;
	
	public FileManageVo() {
		super();
	}

	public FileManageVo(int id, String myFile, Blob fileData, Timestamp uploadTime, String operator, int status,
			String originalFileName,String fileSize) {
		super();
		this.id = id;
		this.myFile = myFile;
		this.fileData = fileData;
		this.uploadTime = uploadTime;
		this.operator = operator;
		this.status = status;
		this.originalFileName = originalFileName;
		this.fileSize=fileSize;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMyFile() {
		return myFile;
	}

	public void setMyFile(String myFile) {
		this.myFile = myFile;
	}

	public Blob getFileData() {
		return fileData;
	}

	public void setFileData(Blob fileData) {
		this.fileData = fileData;
	}

	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}
