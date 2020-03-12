package com.dj.seal.sealBody.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;
/**
 * 可用印章显示印章列表
 * @author YC
 *
 */
public class UseSealAction extends BaseAction{

	static Logger logger = LogManager.getLogger(UseSealAction.class.getName());

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
		// 获得当前页
		int pageIndex = 1;
		if (request.getParameter("pageIndex") != null) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		}
		// 获得每页显示数
		int pageSize = Constants.PAGESIZE;
		// 获得当前用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		String type=request.getParameter("type");//flag1是树状查询，all是管理页面
		PageSplit pageSplit; 
		if(type.equals("flag1")){
			String dept_no=request.getParameter("dept_no");//所属单位
			pageSplit = seal_body.showBodyQueryListByDeptNO(dept_no, pageSize, pageIndex);
			request.setAttribute("pageSplit", pageSplit);// 封装数据
			return mapping.findForward("success");
		}else if(type.equals("all")){
			pageSplit = seal_body.showBodyListById(user.getUser_id(),pageSize,pageIndex);
			request.setAttribute("pageSplit", pageSplit);// 封装数据
			return mapping.findForward("success");
		}	
		return mapping.findForward("index");
     }
}
