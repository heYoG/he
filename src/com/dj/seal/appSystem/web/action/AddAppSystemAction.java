package com.dj.seal.appSystem.web.action;


import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.appSystem.service.api.IAppSysService;
import com.dj.seal.appSystem.web.form.AppSystemForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;

public class AddAppSystemAction extends BaseAction {
	static Logger logger = LogManager.getLogger(AddAppSystemAction.class.getName());
	private IAppSysService appSystemService;
	private AppSystemForm appSystemForm;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		appSystemForm = (AppSystemForm) form;

		// 获得当前用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session
				.getAttribute(Constants.SESSION_CURRENT_USER);
		 appSystemForm.setCreate_date(new Timestamp((new Date()).getTime()));
		 appSystemForm.setCreate_username(user.getUser_name());
		 appSystemService.addAppSystem(appSystemForm);

		return mapping.findForward("success");
	}

	public IAppSysService getAppSystemService() {
		return appSystemService;
	}

	public void setAppSystemService(IAppSysService appSystemService) {
		this.appSystemService = appSystemService;
	}

	public AppSystemForm getAppSystemForm() {
		return appSystemForm;
	}

	public void setAppSystemForm(AppSystemForm appSystemForm) {
		this.appSystemForm = appSystemForm;
	}

}
