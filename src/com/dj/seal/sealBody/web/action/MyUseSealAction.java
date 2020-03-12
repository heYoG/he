package com.dj.seal.sealBody.web.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;

public class MyUseSealAction extends BaseAction{

	static Logger logger = LogManager.getLogger(MyUseSealAction.class.getName());

	 private ISealBodyService seal_body;
	   private IUserService user_srv;
	   private ISysDeptService dept_srv;
	   private ISysUnitService unit_srv;
	   
		public ISysUnitService getUnit_srv() {
		return unit_srv;
	}

	public void setUnit_srv(ISysUnitService unit_srv) {
		this.unit_srv = unit_srv;
	}

		public ISysDeptService getDept_srv() {
			return dept_srv;
		}

		public void setDept_srv(ISysDeptService dept_srv) {
			this.dept_srv = dept_srv;
		}

		public IUserService getUser_srv() {
			return user_srv;
		}

		public void setUser_srv(IUserService user_srv) {
			this.user_srv = user_srv;
		}
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
		int pageIndex=1;
		if (request.getParameter("pageIndex") != null) {
   			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
   		}
   		// 获得每页显示数
   		int pageSize = Constants.PAGESIZE;
		SysUser sysUser=(SysUser)request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);//获取当前用户		 		
		String user_id=sysUser.getUser_id(); //获得当前登录人为申请人
		PageSplit pageSplit=new PageSplit();
		pageSplit = seal_body.showAllSealsOfMine(pageSize, pageIndex, user_id);
			
		request.setAttribute("pageSplit",pageSplit);
		return mapping.findForward("success");
	}

}
