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
 * 新增印模 所用
 * 
 * @author oyxy
 * @since 2009-11-23
 * 
 */
public class SealTempAddAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(SealTempAddAction.class.getName());

	private ISealTemplateService temp_srv;
	private SealTempForm tempForm;
	
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

		// saveToken(request);
		// 获得当前用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		String type=request.getParameter("type");//类型
		logger.info("type:"+type);
		if(type.equals("1")){
			SysUser appMan = temp_srv.getAppMan(user.getUser_id());
			if(appMan != null){
				request.setAttribute("appMan_name", appMan.getUser_name());//得到初始的 审批人
				request.setAttribute("appMan_id", appMan.getUser_id());
			}
//			else{
//				request.setAttribute("appMan_name", null);
//				request.setAttribute("appMan_id", null);
//			}
			return mapping.findForward("index");
		}else if(type.equals("2")){
			SysUser appMan = temp_srv.getAppMan(user.getUser_id());
			if(appMan != null){
				request.setAttribute("appMan_name", appMan.getUser_name());//得到初始的 审批人
				request.setAttribute("appMan_id", appMan.getUser_id());
			}
			return mapping.findForward("indexCZ");
		}else if(type.equals("3")){//手动制作印章
			BeanUtils.copyProperties(tempForm, form);
			//if (isTokenValid(request, true)) { // 防止用户重复提交，例如刷新页面带来的提交
		    String approve_id=request.getParameter("approve_user");//选择审批人
		    String temp_remark=request.getParameter("temp_remark");//备注
		    tempForm.setTemp_remark(temp_remark);
		    tempForm.setTemp_creator(user.getUser_id());
		    tempForm.setApprove_id(approve_id);
			temp_srv.addSealTemp(tempForm);
		    return mapping.findForward("success");
		}else{
			BeanUtils.copyProperties(tempForm, form);
			//if (isTokenValid(request, true)) { // 防止用户重复提交，例如刷新页面带来的提交
		    String approve_id=request.getParameter("approve_user");//选择审批人
		    String temp_remark=request.getParameter("temp_remark");//备注
		    tempForm.setTemp_remark(temp_remark);
		    tempForm.setTemp_creator(user.getUser_id());
		    tempForm.setApprove_id(approve_id);
			temp_srv.addSealTemp(tempForm);
		    return mapping.findForward("success");
		}
		
	}

	public SealTempForm getTempForm() {
		return tempForm;
	}

	public void setTempForm(SealTempForm tempForm) {
		this.tempForm = tempForm;
	}
}
