package com.dj.seal.organise.web.action;

import java.lang.reflect.InvocationTargetException;

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
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 新增部门 所用
 * 
 * @author oyxy
 * @since2009-11-10
 * 
 */
public class DeptAddAction extends BaseAction {
	static Logger logger = LogManager.getLogger(DeptAddAction.class.getName());
	private ISysDeptService dept_srv;
	private DeptForm deptForm;

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

	/**
	 * 新增部门
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		// 判断用户是否已经登录，如果未登录，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		// 从session中获得登录用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session
				.getAttribute(Constants.SESSION_CURRENT_USER);
		// 把部门表单内容交给Spring管理下的部门Form，接受AOP方法处理（去除多余的字符，比如单引号）
		try {
			BeanUtils.copyProperties(deptForm, form);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		// 根据父部门名称得到父部门编号，并设置给部门Form
		int dept_tab=Integer.parseInt(request.getParameter("dept_tab"));
		deptForm.setDept_tab(String.valueOf(dept_tab));
		String dept_parent = dept_srv
				.getDeptNoByName(deptForm.getParent_name());
		deptForm.setDept_parent(dept_parent);
		// 调用业务层方法新增部门
		//logger.info("woengienige");
		dept_srv.addDept(user.getUser_id(), deptForm);
		return mapping.findForward("success");
	}

}
