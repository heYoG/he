package com.dj.seal.hotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.hotel.form.MasterplatejudgeForm;
import com.dj.seal.hotel.service.api.MasterplatejudgeService;
import com.dj.seal.util.struts.BaseAction;

public class NewMasterplatejudgeAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger();
	
	private MasterplatejudgeService masterplatejudgeService;

	
	public MasterplatejudgeService getMasterplatejudgeService() {
		return masterplatejudgeService;
	}


	public void setMasterplatejudgeService(
			MasterplatejudgeService masterplatejudgeService) {
		this.masterplatejudgeService = masterplatejudgeService;
	}


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		MasterplatejudgeForm f =(MasterplatejudgeForm)form; 
		
		masterplatejudgeService.addMasterplatejudge(f);
		return mapping.findForward("success");

	}

}
