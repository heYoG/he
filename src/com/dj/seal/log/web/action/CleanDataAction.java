package com.dj.seal.log.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.web.form.RoleForm;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 新增/更新角色 所用
 * 
 * @author oyxy
 * @since2009-11-13
 * 
 */
public class CleanDataAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(CleanDataAction.class.getName());

	private ISysRoleService role_srv;
	private RoleForm roleForm;

	/**
	 * 新增/更新角色
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {

		// 判断用户是否已经登录，如果未登录，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String type=request.getParameter("type");
		if(type.equals("1")){//手动页面
			return mapping.findForward("shoudong");
		}else if(type.equals("0")){//自动页面
			return mapping.findForward("zidong");
		}
		return mapping.findForward("index");
	}
}
