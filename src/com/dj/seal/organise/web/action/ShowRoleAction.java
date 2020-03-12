package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.struts.BaseAction;



public class ShowRoleAction extends BaseAction {

	static Logger logger = LogManager.getLogger();

	private IUserService user_srv;
	private UserForm userForm;
	
	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session =request.getSession();
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String user_id=request.getParameter("user_id");
		SysUser sysuser=user_srv.editSysUser(user_id);
		session.setAttribute("rolename",sysuser.getRole_name());
	    return mapping.findForward("list");
	}

}
