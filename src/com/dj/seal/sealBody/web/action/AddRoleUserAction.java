package com.dj.seal.sealBody.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.util.struts.BaseAction;

public class AddRoleUserAction extends BaseAction{

	static Logger logger = LogManager.getLogger(AddRoleUserAction.class.getName());

	private  ISealBodyService seal_body;
	
	public ISealBodyService getSeal_body() {
		return seal_body;
	}

	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		
		String seal_id=request.getParameter("seal_id");//印章id
		SealBody sealBody = seal_body.getSealBodys(Integer.valueOf(seal_id));
		if(sealBody.getSeal_types().equals("1")){
			String user_id=request.getParameter("MuserNo");//用户id
			String role_nos=request.getParameter("role_nos");//角色id
			seal_body.addRoleUser(seal_id, user_id, role_nos);
		}else{
			String user_id=request.getParameter("MuserNo1");//用户id
			seal_body.addRoleUser(seal_id, user_id, "");
		}
		
		
		return mapping.findForward("success");
	}
	
}
