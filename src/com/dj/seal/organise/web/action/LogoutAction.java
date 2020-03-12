package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;

public class LogoutAction extends BaseAction {
	static Logger logger = LogManager.getLogger(LogoutAction.class.getName());
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		request.getSession().removeAttribute(Constants.SESSION_CURRENT_USER);
		return mapping.findForward("success");
	}

}
