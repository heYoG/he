package com.dj.seal.personInfo.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.personInfo.web.form.PersonForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;

/**
 * 显示/修改个人信息
 * 
 * @author 
 * 
 */
public class UpdateInfoAction extends BaseAction {

	static Logger logger = LogManager.getLogger(UpdateInfoAction.class.getName());

	private IUserService user_srv;
	private PersonForm user_form;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		BeanUtils.copyProperties(user_form, form);
		user_srv.updateSysPerson(user_form);
		SysUser user = user_srv.editSysUser(user_form.getUser_id());
		HttpSession session = request.getSession();
		session.setAttribute(Constants.SESSION_CURRENT_USER, user);
		return mapping.findForward("success");
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	public PersonForm getUser_form() {
		return user_form;
	}

	public void setUser_form(PersonForm user_form) {
		this.user_form = user_form;
	}

}
