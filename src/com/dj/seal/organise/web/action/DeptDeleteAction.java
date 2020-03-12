package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 删除部门 所用
 * 
 * @author oyxy
 * @since2009-11-12
 * 
 */
public class DeptDeleteAction extends BaseAction {
	static Logger logger = LogManager.getLogger(DeptDeleteAction.class.getName());
	private ISysDeptService dept_srv;
//	private ILogSysService log_srv;
//	
//	
//	public ILogSysService getLog_srv() {
//		return log_srv;
//	}
//
//	public void setLog_srv(ILogSysService log_srv) {
//		this.log_srv = log_srv;
//	}

	public ISysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(ISysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}

	/**
	 * 删除部门
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		// 判断用户是否已经登录，如果未登录，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		// 从请求中获得部门编号
		String dept_no = "";
		if (request.getParameter("dept_no") != null) {
			dept_no = request.getParameter("dept_no");
			// 调用业务层方法删除部门
			dept_srv.deleteDept(dept_no);
		}
	//	log_srv.logAdd(userid, username, ip, viewMenu, logdetail)
		return mapping.findForward("success");
	}
}
