package com.dj.seal.organise.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 获得部门列表所用
 * 
 * @author oyxy
 * @since2009-11-10
 * 
 */
public class DeptListAction extends BaseAction {
	static Logger logger = LogManager.getLogger(DeptListAction.class);
	private ISysDeptService dept_srv;
	private ISysUnitService unit_srv;
	private IUserService user_srv;

	/**
	 * 得到部门列表，跳转到各个部门树页面
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		// 判断用户是否已经登录，如果未登录且不是注册页面点的，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN
				&& request.getParameter("reg") == null) {
			return mapping.findForward("no_login");
		}
		// 从session中获得登录用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session
				.getAttribute(Constants.SESSION_CURRENT_USER);

		if (request.getParameter("reg") != null) {
			user = new SysUser();
			user.setUser_id("admin");
			user.setUser_status("1");
		}
		// 根据用户名得到其可以管理的部门树
		List<SysDepartment> list_dept = dept_srv.deptTreeByUser(user
				.getUser_id());
		// 得到单位是否在用户的管理范围内信息
		int unitIn = dept_srv.isInManageList(Constants.UNIT_DEPT_NO, user
				.getUser_id());

		// 如果是系统管理员，则可以管理所有部门
		if (user_srv.isSuperManager(user.getUser_id())) {
			list_dept = dept_srv.showAllDepts();
			unitIn = 1;
		}
		request.setAttribute("depts", list_dept);
		// 封装单位
		SysUnit unit = unit_srv.showSysUnit();
		request.setAttribute("unit", unit);
		// 封装单位是否是在管理范围内
		request.setAttribute("unitIn", unitIn);
		// 获得跳转类型
		String type = "";
		if (request.getParameter("type") != null) {
			type = request.getParameter("type");
		}
		// 根据跳转类型转向各个不同的部门树页面
		if (type.equals("mode2")) {
            
			return mapping.findForward("mode2");
		} else if (type.equals("mode")) {
			return mapping.findForward("mode");
		} else if ("tempApp".equals(type)) {// 新增印模
			return mapping.findForward("tempApp");
		} else if ("tempManage".equals(type)) {// 印模管理
			return mapping.findForward("tempManage");
		} else if ("tempQuery".equals(type)) {// 印模查询
			return mapping.findForward("tempQuery");
		}else if ("tempQuery2".equals(type)) {// 印模查询
			return mapping.findForward("tempQuery2");
		} else if ("docPrint".equals(type)) {// 文档打印
			return mapping.findForward("docPrint");
		} else if ("sealBody".equals(type)) {// 印章
			return mapping.findForward("sealBody");
		}else if ("usermanage".equals(type)) {// 用户查询
			return mapping.findForward("usermanage");
		}else if ("servermanage".equals(type)) {// 盖章服务器查询
			return mapping.findForward("servermanage");
		}

		return mapping.findForward("success");
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
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
