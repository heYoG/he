package com.dj.seal.modelFileRule.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.modelFileRule.form.ModelFileRuleForm;
import com.dj.seal.modelFileRule.service.impl.ModelFileRuleServiceImpl;
import com.dj.seal.util.struts.BaseAction;

public class ModelFileRuleAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ModelFileRuleAction.class.getName());
	
	private ModelFileRuleServiceImpl modelFilleRuleService;

	public ModelFileRuleServiceImpl getModelFilleRuleService() {
		return modelFilleRuleService;
	}

	public void setModelFilleRuleService(
			ModelFileRuleServiceImpl modelFilleRuleService) {
		this.modelFilleRuleService = modelFilleRuleService;
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		ModelFileRuleForm f = (ModelFileRuleForm) form;

		modelFilleRuleService.addModelFileRule(f);
		return mapping.findForward("success");
	}

}
