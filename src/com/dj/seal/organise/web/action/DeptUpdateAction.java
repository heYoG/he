package com.dj.seal.organise.web.action;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 修改部门信息时所用
 * 
 * @author oyxy
 * @since 2009-11-11
 * 
 */
public class DeptUpdateAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger();
	
	private ISysDeptService dept_srv;
	private DeptForm deptForm;

	/**
	 * 修改部门信息
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		// 判断用户是否已经登录，如果未登录，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		try {
			BeanUtils.copyProperties(deptForm, form);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		String dept_tab=request.getParameter("dept_tab");
		int dept_tab2=Integer.parseInt(dept_tab);
		deptForm.setDept_tab(dept_tab2+"");
		dept_srv.updateDept(deptForm);
		return mapping.findForward("success");
	}

	public DeptForm getDeptForm() {
		return deptForm;
	}

	public void setDeptForm(DeptForm deptForm) {
		this.deptForm = deptForm;
	}

	public ISysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(ISysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}
}
