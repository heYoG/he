package com.dj.seal.sealTemplate.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 新增印模时进入新增页面 所用
 * 
 * @author oyxy
 * @since 2009-11-25
 * 
 */
public class SealTempAppListAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(SealTempAppListAction.class.getName());

	private ISealTemplateService temp_srv;
	private ISysDeptService dept_srv;

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
			throws GeneralException {
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
		SysUser user = (SysUser) session
				.getAttribute(Constants.SESSION_CURRENT_USER);
		// 获得当前用户所在部门并作为默认部门
		String dept_no = user.getDept_no();
		// 获得当前登录用户为审批人进行查询得到当前审批的待审批记录
		String user_id =user.getUser_id();
		// 根据条件获得数据(部门编号和审批人)
		PageSplit pageSplit = temp_srv.showSealTempByApp(pageIndex, pageSize, user_id);

		request.setAttribute("pageSplit", pageSplit);// 封装数据

		return mapping.findForward("success");
	}
}
