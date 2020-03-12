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

public class GetInfoSealAction extends BaseAction{

	static Logger logger = LogManager.getLogger(GetInfoSealAction.class.getName());

	private ISealBodyService seal_body;

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
		//判断用户是否登陆
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		    int seal_id=0; 
		    if(request.getParameter("seal_id")!=null&&request.getParameter("seal_id")!="")
		    {
		    	seal_id=Integer.parseInt(request.getParameter("seal_id"));
		    }
		    SealBody seal=seal_body.getSealBodys(seal_id);
		    request.setAttribute("seal", seal);
			return mapping.findForward("success");
	}
	
	
}
