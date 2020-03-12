package com.dj.seal.personInfo.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.util.struts.BaseAction;

/**
 * 进入控制面板模块
 * 
 * @author oyxy
 * 
 */
public class IndexAction extends BaseAction {

	static Logger logger = LogManager.getLogger(IndexAction.class.getName());


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
