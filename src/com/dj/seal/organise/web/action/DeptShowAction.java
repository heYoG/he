package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 进入部门修改页面时所用
 * 
 * @author oyxy
 * @since2009-11-10
 * 
 */
public class DeptShowAction extends BaseAction {
	static Logger logger = LogManager.getLogger(DeptShowAction.class.getName());
	private ISysDeptService dept_srv;
	private ISysUnitService unit_srv;

	/**
	 * 进入部门修改页面
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		// 判断用户是否已经登录，如果未登录，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		// 获得部门编号
		String dept_no = null;
		if (request.getParameter("dept_no") != null) {
			dept_no = request.getParameter("dept_no");
			if (request.getParameter("type") != null) {
				// 根据部门编号得到部门
				SysDepartment dept = dept_srv.showDeptByNo(dept_no);
				if (dept == null) {
					return mapping.findForward("error");
				}
				request.setAttribute("dept_name", dept.getDept_name());
				request.setAttribute("dept_no", dept.getDept_no());
				return mapping.findForward("newDept");
			}
			SysDepartment dept = dept_srv.showDeptByNo(dept_no);
			request.setAttribute("dept", dept);
			String parent_name = dept_srv.parentName(dept_no);
			request.setAttribute("parent_name", parent_name);
		}
		if (dept_no == null) {
			SysUnit unit = unit_srv.showSysUnit();
			request.setAttribute("dept_name", unit.getUnit_name());
			request.setAttribute("dept_no", unit.getDept_no());
			return mapping.findForward("newDept");
		}
		return mapping.findForward("success");
	}

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

}
