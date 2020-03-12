package com.dj.seal.sealBody.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;
/**
 * 绑定证书的action
 * @author Hp
 *
 */
public class SealBindAction extends BaseAction {

	static Logger logger = LogManager.getLogger(SealBindAction.class.getName());

	
	private ISealBodyService seal_srv;
	
	public ISealBodyService getSeal_srv() {
		return seal_srv;
	}

	public void setSeal_srv(ISealBodyService seal_srv) {
		this.seal_srv = seal_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String seal_id = "";
		if (request.getParameter("seal_id") != null) {
				seal_id = request.getParameter("seal_id");
		}
		String cert_id = "";
		if(request.getParameter("cert_id") != null){
			cert_id = request.getParameter("cert_id");
		}
		//seal_srv.objbind(seal_id, cert_id);
		request.setAttribute("seal_id", seal_id);
		request.setAttribute("cert_id", cert_id);
		return mapping.findForward("success");
	}
	
}
