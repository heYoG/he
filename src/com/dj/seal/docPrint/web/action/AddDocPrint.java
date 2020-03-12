package com.dj.seal.docPrint.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.docPrint.service.api.IDocPrintService;
import com.dj.seal.util.struts.BaseAction;

public class AddDocPrint extends BaseAction{
	
	static Logger logger = LogManager.getLogger(AddDocPrint.class.getName());
	
	IDocPrintService docprint_Service;

	public void setDocprint_Service(IDocPrintService docprint_Service) {
		this.docprint_Service = docprint_Service;
	}
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String printNumStr=request.getParameter("printNum");
		int printNum=Integer.valueOf(printNumStr);
		String doc_no=request.getParameter("doc_id");//印章id
		String user_id=request.getParameter("MuserNo");//用户id
		String role_nos=request.getParameter("role_nos");//角色id
		docprint_Service.addRoleUser(doc_no, user_id, role_nos,printNum);
		return mapping.findForward("success");
	}
	
}
