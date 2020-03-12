package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 进入印模审批页面 所用
 * 
 * @author oyxy
 * @since 2009-11-27
 * 
 */
public class SealTempApproveGuide extends BaseAction {
	static Logger logger = LogManager.getLogger(SealTempApproveGuide.class.getName());
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String type = "index";
		if (request.getParameter("type") != null) {
			type = request.getParameter("type");
		}
		if ("index".equals(type)) {
			return mapping.findForward("index");
		} else {
			return mapping.findForward("bottom");
		}

	}
}
