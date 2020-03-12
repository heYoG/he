package com.dj.seal.organise.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.dj.seal.organise.service.impl.UploadDeptAndUserService;
import com.dj.seal.organise.web.form.UploadDeptAndUserForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.struts.BaseAction;
import com.dj.seal.util.Constants;
public class UploadDeptAndUserAction extends BaseAction {

	static Logger logger = LogManager.getLogger(UploadDeptAndUserAction.class.getName());

	private UploadDeptAndUserService udau_srv;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		// 从session中获得登录用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		UploadDeptAndUserForm f = (UploadDeptAndUserForm) form;	
		FormFile file = f.getFile_path();
		String filepath="";
//		String bpath = bpath();
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		String file_name = time.format(new Date());
		String path = request.getSession().getServletContext().getRealPath("");// 获取web服务器地址
//		filepath=bpath + "/doc/excel/"+ file_name+ ".xls";
		filepath=path + "/doc/excel/"+ file_name+ ".xls";
		logger.info(filepath);
		try {
			byte[] content1 = file.getFileData();
			logger.info("file-length:"+new String(content1).length());
			if (file.getFileSize() > 0) {
				File file1 = new File(filepath);
				FileOutputStream out = new FileOutputStream(file1);
				out.write(content1);
				out.close();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		String result="";
		if(f.getType().equals("dept")){
			result = udau_srv.uploadDeptExcel(filepath);
			request.setAttribute("fail_msg", result);
			return mapping.findForward("deptresult");
		}else if(f.getType().equals("user")){
			result = udau_srv.uploadUserExcel(filepath,user.getUser_id());
			request.setAttribute("fail_msg", result);
			return mapping.findForward("deptresult");
		}else{
			result="导入类型错误，请联系管理员!";
			request.setAttribute("fail_msg", result);
			return mapping.findForward("failed");
		}
	}

	private static String bpath() throws Exception {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return bpath;
	}

	public UploadDeptAndUserService getUdau_srv() {
		return udau_srv;
	}

	public void setUdau_srv(UploadDeptAndUserService udau_srv) {
		this.udau_srv = udau_srv;
	}

}
