package com.dj.seal.hotel.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import com.dj.seal.hotel.form.HotelAdvertForm;
import com.dj.seal.hotel.service.impl.HotelAdvertServiceImpl;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;


public class AdApproveAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(AdApproveAction.class.getName());
	
	private HotelAdvertServiceImpl advertService;
	private HotelAdvertForm advertForm;

	public HotelAdvertForm getAdvertForm() {
		return advertForm;
	}

	public void setAdvertForm(HotelAdvertForm advertForm) {
		this.advertForm = advertForm;
	}

	public HotelAdvertServiceImpl getAdvertService() {
		return advertService;
	}

	public void setAdvertService(HotelAdvertServiceImpl advertService) {
		this.advertService = advertService;
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session
				.getAttribute(Constants.SESSION_CURRENT_USER);
		advertForm.setApprove_user(user.getUser_id());
		String ID = "";
		String opinion="";
		String ad_statu = "";
		String approve_user = "";
		if (request.getParameter("ad_id") != null) {
			ID = request.getParameter("ad_id");
		}
		if(request.getParameter("ad_opinion")!=null){
			opinion = request.getParameter("ad_opinion");
		}
		if(request.getParameter("ad_statu")!=null){
			ad_statu=request.getParameter("ad_statu");
		}
		if(request.getParameter("approve_user")!=null){
			approve_user = request.getParameter("approve_user");
		}
		advertForm.setAd_opinion(opinion);
		advertForm.setApprove_user(user.getUnique_id());
		advertForm.setAd_state(ad_statu);
		advertForm.setApprove_user(user.getUser_id());
		advertService.approveAd(advertForm, ID);
		return mapping.findForward("success");
	}
	

}
