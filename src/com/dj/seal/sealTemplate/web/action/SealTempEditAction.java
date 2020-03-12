package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.structure.dao.po.SealTemplate;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 印模被退回后修改action
 * @author Hp
 *
 */
public class SealTempEditAction extends BaseAction{
	static Logger logger = LogManager.getLogger(SealTempEditAction.class.getName());
	private ISealTemplateService temp_srv;
	private IUserService user_srv;
	public ISealTemplateService getTemp_srv() {
		return temp_srv;
	}

	public void setTemp_srv(ISealTemplateService temp_srv) {
		this.temp_srv = temp_srv;
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
			throws GeneralException {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String temp_id = "";
		if(request.getParameter("temp_id")!=null){
			temp_id=request.getParameter("temp_id");
		}
		SealTemplate temp=temp_srv.showTempByTemp(temp_id);
		String userId = temp.getApprove_user();
		SysUser user = user_srv.showSysUserByUser_id(userId);
		String userName = user.getUser_name();
		request.setAttribute("userName", userName);
		request.setAttribute("temp", temp);
		return mapping.findForward("success");
	}
}
