package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.util.struts.BaseAction;

/**
 * 查询印模页面跳转 所用
 * 
 * @author oyxy
 * @since 2009-12-3
 * 
 */
public class SearchGuide extends BaseAction {
	
	static Logger logger = LogManager.getLogger(SearchGuide.class.getName());

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		return mapping.findForward("success");
	}

	
}
