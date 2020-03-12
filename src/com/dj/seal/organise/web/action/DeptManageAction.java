package com.dj.seal.organise.web.action;

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
 * 进入部门管理页面时所用
 * 
 * @author oyxy
 * @since2009-11-10
 * 
 */
public class DeptManageAction extends BaseAction {
	static Logger logger = LogManager.getLogger(DeptManageAction.class.getName());

	/**
	 * 部门管理
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		// 判断用户是否已经登录，如果未登录，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		return mapping.findForward("success");
	}

}
