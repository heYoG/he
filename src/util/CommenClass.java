package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CommenClass{
	private int totalCount;//总记录数
	private int currentPage;//当前页
	private int pageSize;//每页显示数
	private int totalPage;//总页数
	private int jumpPage;//跳转页
	private int[] item;//页数
	
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
	public int[] getItem() {
		return item;
	}
	public void setItem(int[] item) {
		this.item = item;
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
