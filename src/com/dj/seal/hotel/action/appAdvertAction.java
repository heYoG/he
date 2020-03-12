package com.dj.seal.hotel.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import com.dj.seal.util.struts.BaseAction;


public class appAdvertAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(appAdvertAction.class.getName());
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		return mapping.findForward("success");
	}
	

}
