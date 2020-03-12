package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;

public class SerUserCertAction extends BaseAction {

	static Logger logger = LogManager.getLogger(SerUserCertAction.class.getName());

	private CertSrv cert_srv;
	
	
	public CertSrv getCert_srv() {
		return cert_srv;
	}


	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//request.setCharacterEncoding("GBK");
		HttpSession session =request.getSession();
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		// 获得当前页
		int pageIndex = 1;
		String type=null;
		String dept_no=null;
		if (request.getParameter("pageIndex") != null) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		}
		// 获得每页显示数
		int pageSize = Constants.PAGESIZE;
		String user_id=request.getParameter("user_id");
		PageSplit pageSplit = cert_srv.showClientCertsByUserid(pageIndex,pageSize,user_id);
		request.setAttribute("pageSplit", pageSplit);// 封装数据
		request.setAttribute("user_id", user_id);
		request.setAttribute("user_name", request.getParameter("user_name"));
	    return mapping.findForward("success");
	}

}
