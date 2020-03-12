package com.dj.seal.sealTemplate.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.sealTemplate.web.form.SealTempForm;
import com.dj.seal.structure.dao.po.SysDepartment;
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
public class SearchSealTempListAction extends BaseAction {
	static Logger logger = LogManager.getLogger(SearchSealTempListAction.class.getName());
	private ISealTemplateService temp_srv;
	private ISysDeptService dept_srv;
	private SealTempForm tempForm;
	
	public SealTempForm getTempForm() {
		return tempForm;
	}

	public void setTempForm(SealTempForm tempForm) {
		this.tempForm = tempForm;
	}

	public ISysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(ISysDeptService dept_srv) {
		this.dept_srv = dept_srv;
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
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		BeanUtils.copyProperties(tempForm, form);
		String type=null;
		String dept_no=null;
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
		PageSplit pageSplit;
		type=request.getParameter("type");
		//树桩节点
		if("flag1".equals(type))
		{  
			dept_no=request.getParameter("dept_no1");	
			SysDepartment sysDepartment=dept_srv.showDeptByNo(dept_no);
			request.setAttribute("dept_no", dept_no);	
			request.setAttribute("dept_name",sysDepartment.getDept_name());	
			pageSplit = temp_srv.showTempsByDeptManageSel(pageIndex,
						pageSize, dept_no);
			request.setAttribute("pageSplit", pageSplit);// 封装数据
			return mapping.findForward("successJIn");
		}//点击全部查看所有部门的印章	
	    if("all".equals(type))
		{
	    	// 获得当前用户管理下的部门
			List<SysDepartment> list=new ArrayList<SysDepartment>();
			try {
				list = dept_srv.showDeptByUser(user.getUser_id());
				request.setAttribute("dept_name","所有");	
				// 根据条件获得数据
			   pageSplit = temp_srv.showTempsByDeptManage(pageIndex,
						pageSize, list);
				request.setAttribute("pageSplit", pageSplit);// 封装数据
				return mapping.findForward("successAll");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		  
		}//查询界面	
	    if("flag2".equals(type))
		{ 
	    	dept_no=request.getParameter("dept_no");
			SysDepartment dept = dept_srv.showDeptByNo(dept_no);	
			request.setAttribute("dept_name", dept.getDept_name());
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String temp_name = request.getParameter("temp_name");
			String seal_type = request.getParameter("seal_type");
			tempForm.setStart_time(start_time);
			tempForm.setEnd_time(end_time);
			tempForm.setDept_no(dept_no);
			tempForm.setTemp_name(temp_name);
			tempForm.setSeal_type(seal_type);
		    String is_junior=request.getParameter("is_junior");
		    request.setAttribute("is_junior", is_junior);// 封装数据
		    request.setAttribute("start_time", start_time);
			request.setAttribute("end_time", end_time);
			request.setAttribute("temp_name", temp_name);
			request.setAttribute("seal_type", seal_type);
			request.setAttribute("dept_no", dept_no);
			pageSplit = temp_srv.findSealTemplate(pageIndex, pageSize,
					tempForm,is_junior);
			
			request.setAttribute("pageSplit", pageSplit);// 封装数据
			return mapping.findForward("successSer");
		}
	
		return mapping.findForward("success");
	}
}
