package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.util.struts.BaseAction;



public class zhuxiaoAction extends BaseAction {

	static Logger logger = LogManager.getLogger(zhuxiaoAction.class.getName());

	private IUserService user_srv;
	private UserForm userForm;
	
	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		} 
		//   BeanUtils.copyProperties(userForm, (UserForm) form);
		  String user_id=request.getParameter("user_id");
		  
			  String id=request.getParameter("id");
			  if(id.equals("1")){
				  user_srv.zhuxiaoList(user_id);  
			  }if(id.equals("2")){		
				  user_srv.quzhuxiaoList(user_id);  
			  }
			  return mapping.findForward("success");
		 
	}
}
