package vo;

import java.util.Date;

/**
 * 额外字段管理，如查询条件字段
 * @author Administrator
 *
 */
public class CommenVo {
	private String fileName;//文件名，用于查询
	private String userNo;//用户名，用于查询
	private Date date;//文件上传日期，用于查询
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
