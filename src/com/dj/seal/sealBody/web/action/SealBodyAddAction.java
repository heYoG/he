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
import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.structure.dao.po.SealTemplate;
import com.dj.seal.util.struts.BaseAction;

/**
 * 跳转到添加印章页面的action
 * @author YC
 *
 */
public class SealBodyAddAction extends BaseAction{

	static Logger logger = LogManager.getLogger(SealBodyAddAction.class.getName());

	 private ISealTemplateService seal_temp;
	 private ISysDeptService dept_srv;
	 private ISysUnitService unit_srv;  
	 
		public ISysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(ISysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}

	public ISysUnitService getUnit_srv() {
		return unit_srv;
	}

	public void setUnit_srv(ISysUnitService unit_srv) {
		this.unit_srv = unit_srv;
	}

	public ISealTemplateService getSeal_temp() {
		return seal_temp;
	}

	public void setSeal_temp(ISealTemplateService seal_temp) {
		this.seal_temp = seal_temp;
	}

		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			//判断用户是否登陆
			if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
				return mapping.findForward("no_login");
			}
		    String temp_id="";
		    if(request.getParameter("temp_id")!=null&&request.getParameter("temp_id")!="")
		    {
		      temp_id=request.getParameter("temp_id");
		    }
		    SealTemplate template=seal_temp.showTempByTemp(temp_id);
		    request.setAttribute("template", template);
//		     //保存令牌
//		    saveToken(request);
			return mapping.findForward("success");
		}
		
}
