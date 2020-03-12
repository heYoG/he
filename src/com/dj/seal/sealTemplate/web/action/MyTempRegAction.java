package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;

public class MyTempRegAction extends BaseAction{
	
	static Logger logger = LogManager.getLogger(MyTempRegAction.class.getName());
	
	private ISealTemplateService temp_srv;
	
	public ISealTemplateService getTemp_srv() {
		return temp_srv;
	}

	public void setTemp_srv(ISealTemplateService temp_srv) {
		this.temp_srv = temp_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		//得到当前用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser)session.getAttribute(Constants.SESSION_CURRENT_USER);
		// 获取当前页
		int pageIndex = 1;
		if (request.getParameter("pageIndex") != null) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		}
		// 获取每页显示页
		int pageSize = Constants.PAGESIZE;
		String user_id = user.getUser_id();
		PageSplit pageSplit = temp_srv.LocalshowTempListSu(user_id, pageSize, pageIndex);
		request.setAttribute("pageSplit", pageSplit);
		return mapping.findForward("success");
	}
}
