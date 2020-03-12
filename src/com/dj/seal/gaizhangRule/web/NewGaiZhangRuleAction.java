package com.dj.seal.gaizhangRule.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.gaizhangRule.form.GaiZhangRuleForm;
import com.dj.seal.gaizhangRule.service.impl.GaiZhangRuleSrvImpl;
import com.dj.seal.util.struts.BaseAction;

public class NewGaiZhangRuleAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(NewGaiZhangRuleAction.class.getName());
	
	private GaiZhangRuleSrvImpl rule_srv;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GaiZhangRuleForm f = (GaiZhangRuleForm) form;
		rule_srv.addRule(f);
		return mapping.findForward("success");
	}

	public GaiZhangRuleSrvImpl getRule_srv() {
		return rule_srv;
	}

	public void setRule_srv(GaiZhangRuleSrvImpl rule_srv) {
		this.rule_srv = rule_srv;
	}

}
