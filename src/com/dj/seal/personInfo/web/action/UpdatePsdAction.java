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
import com.dj.seal.personInfo.web.form.PsdForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;

public class UpdatePsdAction extends BaseAction {
	

	static Logger logger = LogManager.getLogger(UpdatePsdAction.class.getName());


	private IUserService user_srv;
	private PsdForm psd_form;
	

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		BeanUtils.copyProperties(psd_form, form);
		SysUser user=user_srv.editSysUser(psd_form.getUser_id());
		if(user.getLogined()==1){//第二次更新密码保存第一次更新密码(当前使用)为旧密码
			psd_form.setPassword2(user.getCurrentpassword());
			psd_form.setPassword2md5(user.getUser_psd());
		}else{//2次更新后覆盖更新保留最近三个密码
			psd_form.setPassword1(user.getPassword2());//第一个旧密码更新为第二个旧密码
			psd_form.setPassword1md5(user.getPassword2md5());
			psd_form.setPassword2(user.getCurrentpassword());//第二个旧密码更新为当前密码(更新前正在使用密码)
			psd_form.setPassword2md5(user.getUser_psd());
		}	
		user_srv.updateSysUserPsd(psd_form);
		HttpSession session=request.getSession();
		session.setAttribute(Constants.SESSION_CURRENT_USER, user);
		return mapping.findForward("success");
	}
	
	public PsdForm getPsd_form() {
		return psd_form;
	}


	public void setPsd_form(PsdForm psd_form) {
		this.psd_form = psd_form;
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

}
