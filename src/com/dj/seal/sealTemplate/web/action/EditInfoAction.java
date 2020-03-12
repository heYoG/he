package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.sealTemplate.web.form.SealTempForm;
import com.dj.seal.structure.dao.po.SealTemplate;
import com.dj.seal.util.struts.BaseAction;

/**
 * 编辑印模信息 所用
 * 
 * @author oyxy
 * @since 2009-12-2
 * 
 */
public class EditInfoAction extends BaseAction {
	static Logger logger = LogManager.getLogger(EditInfoAction.class.getName());
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
	
		// 如果是正式提交修改
		if (request.getParameter("type").equals("update")) {//进入更新界面
			BeanUtils.copyProperties(tempForm, form);
			temp_srv.updateTemp(tempForm);
			return mapping.findForward("update");
		}else if(request.getParameter("type").equals("edit")){//进入编辑界面
			// 获得印模ID
			String temp_id=request.getParameter("temp_id");
			SealTemplate temp = temp_srv.showTempByTemp(temp_id);
			request.setAttribute("temp", temp);
			return mapping.findForward("guide");
		}
		return mapping.findForward("guide");
	}
}
