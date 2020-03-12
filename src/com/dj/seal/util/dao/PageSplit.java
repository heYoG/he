package com.dj.seal.util.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 分页工具类
 * 
 * @author oyxy
 * 
 */
@SuppressWarnings("unchecked")
public class PageSplit implements Serializable {
	static Logger logger = LogManager.getLogger(PageSplit.class.getName());
	private static final long serialVersionUID = 1L;
	// 总记录数
	private int totalCount;
	// 总页数
	private int totalPage;
	// 当前页
	private int nowPage = 1;
	// 每页记录数
	private int pageSize = 10;
	// 数据
	private List datas = new ArrayList();

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		if (totalCount % pageSize == 0) {
			return totalCount / pageSize;
		} else {
			return totalCount / pageSize + 1;
		}
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("totalCount=").append(totalCount).append(",totalPage=")
				.append(totalPage).append(",nowPage=").append(nowPage)
				.append(",pageSize=").append(pageSize).append(",datas=");
		if (datas != null) {
			sb.append(datas.toString());
		} else {
			sb.append("null");
		}
		return sb.toString();
	}

}
