package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.sealTemplate.web.form.SealTempForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;

/**
 * 进入印模管理列表页面 所用
 * 
 * @author oyxy
 * @since 2009-12-1
 * 
 */
public class SealTempManageListAction extends BaseAction {
	private ISealTemplateService temp_srv;
	private SealTempForm tempForm;
	
	
	public SealTempForm getTempForm() {
		return tempForm;
	}

	public void setTempForm(SealTempForm tempForm) {
		this.tempForm = tempForm;
	}

	public ISealTemplateService getTemp_srv() {
		return temp_srv;
	}

	public void setTemp_srv(ISealTemplateService temp_srv) {
		this.temp_srv = temp_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
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
		String type=request.getParameter("type");//flag1是查询页面，flag2是管理页面
		
		PageSplit pageSplit; 
		if(type.equals("flag1")){
			BeanUtils.copyProperties(tempForm, form);
			String is_junior=request.getParameter("is_junior");//是否允许下级
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String temp_name = request.getParameter("temp_name");
			String seal_type = request.getParameter("seal_type");
			String dept_no = request.getParameter("dept_no");
			tempForm.setStart_time(start_time);
			tempForm.setEnd_time(end_time);
			tempForm.setTemp_name(temp_name);
			tempForm.setSeal_type(seal_type);
			tempForm.setDept_no(dept_no);
			pageSplit = temp_srv.showTempQueryList(tempForm, pageSize, pageIndex, is_junior);
			request.setAttribute("is_junior", is_junior);// 封装数据
			request.setAttribute("start_time", start_time);
			request.setAttribute("end_time", end_time);
			request.setAttribute("temp_name", temp_name);
			request.setAttribute("seal_type", seal_type);
			request.setAttribute("dept_no", dept_no);
			
		}else{
			String is_junior=request.getParameter("is_junior");//是否允许下级
			pageSplit = temp_srv.showTempList(is_junior,user.getUser_id(),pageSize,pageIndex);
			request.setAttribute("is_junior", is_junior);
			
		}
		
		request.setAttribute("pageSplit", pageSplit);// 封装数据
		return mapping.findForward("success");
	}
}
