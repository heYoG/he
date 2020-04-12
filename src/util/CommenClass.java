package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;

/**
 * 普通类,归集所有分散属性
 * @author Administrator
 *
 */
@Component
public class CommenClass{
	private int totalCount;//总记录数
	private int currentPage;//当前页
	private int pageSize;//每页显示数
	private int totalPage;//总页数
	private int jumpPage;//跳转页
	private List<Integer> itemList;//页数
	private int start;//起始位置
	private int end;//结束位置
	private String type;//查询类型，0：注销/收件箱，1：正常/发送箱, 2：草稿箱 3：已删除邮件
	public static final String CURRENTUSERSESSION ="USERSESSION";//登录用户session
	public static final String LOGINEDMAP="USERMAP";//用户登录状态，默认未登录
	public static final String NORMAL_RETURN="0000";//程序正常
	public static final String FILENOTFOUNDEXCEPTION="0001";//要下载文件不存在
	public static final String DELETESEALIMAGEEXCEPTION="0002";//删除印模失败
	public static final String SAVEDATA_EXCEPTION="0003";//保存数据异常
	public static final String DELETEEMAIL_FAIL="0004";//删除邮件失败
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getJumpPage() {
		return jumpPage;
	}

	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setJumpPage(int jumpPage) {
		this.jumpPage = jumpPage;
	}
	public List<Integer> getItemList() {
		return itemList;
	}
	public void setItemList(List<Integer> itemList) {
		this.itemList = itemList;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static String getProperty(String key) {
		ClassLoader loader = CommenClass.class.getClassLoader();
		InputStream inputStream = loader.getResourceAsStream("config.properties");
		Properties pro=new Properties();
		String value="";
		try {
			pro.load(inputStream);
			value = pro.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
}
