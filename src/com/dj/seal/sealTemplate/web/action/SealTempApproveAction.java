package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.sealTemplate.web.form.SealTempForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;

/**
 * 批准或者拒绝印模时 所用
 * 
 * @author oyxy
 * @since 2009-11-27
 * 
 */
public class SealTempApproveAction extends BaseAction {
	static Logger logger = LogManager.getLogger(SealTempApproveAction.class.getName());
	private ISealTemplateService temp_srv;
	private SealTempForm tempForm;

	public SealTempForm getTempForm() {
		return tempForm;
	}

	public void setTempForm(SealTempForm tempForm) {
		this.tempForm = tempForm;
	}

	public ISealTemplateService getTemp_srv() {
		return temp_srv;
	}

	public void setTemp_srv(ISealTemplateService temp_srv) {
		this.temp_srv = temp_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session
				.getAttribute(Constants.SESSION_CURRENT_USER);
		BeanUtils.copyProperties(tempForm, form);
		tempForm.setApprove_user(user.getUser_id());
		String ID = "";
		if (request.getParameter("ID") != null) {
			ID = request.getParameter("ID");
			logger.info("---ID---"+ID);
		}
		temp_srv.approveTemp(tempForm, ID);
		return mapping.findForward("success");
	}
}
