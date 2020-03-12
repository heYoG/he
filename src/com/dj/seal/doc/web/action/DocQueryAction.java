package com.dj.seal.doc.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.doc.service.api.IDocService;
import com.dj.seal.doc.web.form.SearchDocForm;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;
//import com.sun.org.apache.commons.beanutils.BeanUtils;

public class DocQueryAction extends BaseAction{
	
	static Logger logger = LogManager.getLogger(DocQueryAction.class.getName());
	
	private IDocService doc_service;

	public IDocService getDoc_service() {
		return doc_service;
	}

	public void setDoc_service(IDocService doc_service) {
		this.doc_service = doc_service;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//判断用户是否登陆
	   	  if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
	   		return mapping.findForward("no_login");
	   	  }

   		// 获得每页显示数
   		int pageSize = Constants.PAGESIZE;
   		HttpSession session=request.getSession();
   		String type=request.getParameter("type");
	   	SearchDocForm docQueryForm=(SearchDocForm)session.getAttribute("docQueryForm");
   		if(type.equals("flag1")){//用来表示新的查询条件到来
   			docQueryForm=(SearchDocForm)form;
   		}
	   	  // 获得当前页
   		int pageIndex = 1;
   		if (request.getParameter("pageIndex") != null) {
   			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
   		}
   		if(type.equals("flag2")){//用来表示从设置打印份数页面跳转过来的
   			pageIndex = docQueryForm.getPageIndex();
   		}
   		docQueryForm.setPageIndex(pageIndex);
   		PageSplit pageSplit =doc_service.getDocList(docQueryForm, pageSize, pageIndex);
   		request.setAttribute("pageSplit", pageSplit);// 封装数据
   		if(type.equals("flag1")){
   	   		session.setAttribute("docQueryForm", form);
   		}else{
   			session.setAttribute("docQueryForm", docQueryForm);
   		}
   		return mapping.findForward("success");
	}
	

}
