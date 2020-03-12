package com.dj.seal.util.struts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.upload.FormFile;

import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;

public class BaseAction extends Action implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(BaseAction.class.getClass());

	// 未登录状态
	public static final int SYSTEM_USER_STATUS_NOLOGIN = -1;
	// 已登录状态
	public static final int SYSTEM_USER_STATUS_LOGINED = 1;

	public int isLogin(HttpServletRequest request) {
		if (null == request.getSession().getAttribute(
				Constants.SESSION_CURRENT_USER)) {
			return SYSTEM_USER_STATUS_NOLOGIN;
		}
		return SYSTEM_USER_STATUS_LOGINED;
	}


	/*
	 * public PageSplitUtil getPageSplit(HttpServletRequest request){
	 * PageSplitUtil pageSplit=new PageSplitUtil(); pageSplit.setPageindex(1);
	 * String pageIndex=request.getParameter("pageIndex"); if(null != pageIndex &&
	 * !"".equals(pageIndex)){
	 * pageSplit.setPageindex(Integer.parseInt(pageIndex)); } return pageSplit; }
	 */

	public boolean Upload(FormFile file, String fileName, String path)
			throws GeneralException {
		boolean flag = false;
		try {
			InputStream stream = file.getInputStream();
			OutputStream bos = new FileOutputStream(path + fileName);
			byte[] buffer = new byte[file.getFileSize()];
			stream.read(buffer);
			bos.write(buffer);
			stream.close();
			bos.close();
			flag = true;
		} catch (IOException ex) {
			throw new GeneralException("");
		}
		return flag;
	}

}
