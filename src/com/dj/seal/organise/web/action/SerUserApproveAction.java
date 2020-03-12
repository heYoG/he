package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;

public class SerUserApproveAction extends BaseAction {

	static Logger logger = LogManager.getLogger(SerUserApproveAction.class
			.getName());

	private IUserService user_srv;
	private UserForm userForm;
	private ISysDeptService dept_srv;

	public ISysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(ISysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}

	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		BeanUtils.copyProperties(userForm, form);
		// 获得当前页
		int pageIndex = 1;
		String type = null;
		if (request.getParameter("pageIndex") != null) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		}
		// 获得每页显示数
		int pageSize = Constants.PAGESIZE;
		String current_user=request.getParameter("current_user");//当前登录用户
		if (request.getParameter("type") != null
				&& request.getParameter("type") != "") {
			type = request.getParameter("type");
			request.setAttribute("type", type);
		}
		// 部门树查询
		if ("flag1".equals(type)) {
			String unique_id = request.getParameter("id");// 审批获取唯一ID
			String dept_no = request.getParameter("dept_no");
			String flag=request.getParameter("flag");//同意(flag1)或拒绝(flag2)判断标志
			String state=request.getParameter("state");//审批意见
			
			request.setAttribute("dept_no", dept_no);
			SysDepartment dept = dept_srv.showDeptByNo(dept_no);
			String dept_name = dept.getDept_name();
			request.setAttribute("dept_no", dept_no);
			request.setAttribute("dept_name", dept_name);

			if (unique_id != null && flag != null) {// 审批状态变更
				user_srv.approveUserByUniId(unique_id, flag,state,current_user);
			}
			// 变更审批状态后重新查询数据
			PageSplit pageSplit = user_srv.showTempsByDeptTree(pageIndex,
					pageSize, dept_no, 0);// 用户审批模块,status=0
			request.setAttribute("pageSplit", pageSplit);

			return mapping.findForward("successSer");
		}
		// 界面条件查询
		if ("flag2".equals(type)) {
			String unique_id = request.getParameter("id");//审批获取唯一ID
			String user_id = request.getParameter("user_id");//被审批用户
			String flag=request.getParameter("flag");//同意(flag1)或拒绝(flag2)判断标志
			String user_name = request.getParameter("user_name");
			String create_name = request.getParameter("create_name");
			String dept_no = request.getParameter("dept_no");
			String state=request.getParameter("state");//审批意见
			
			request.setAttribute("dept_no", dept_no);
			SysDepartment dept = dept_srv.showDeptByNo(dept_no);
			request.setAttribute("dept_name", dept.getDept_name());
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			userForm.setUser_id(user_id);
			userForm.setUser_name(user_name);
			userForm.setDept_no(dept_no);
			userForm.setCreate_name(create_name);
			userForm.setQstart_time(start_time);
			userForm.setQend_time(end_time);
			String is_junior = request.getParameter("is_junior");
			request.setAttribute("is_junior", is_junior);// 封装数据
			userForm.setIs_junior(is_junior);
			
			if (unique_id != null && flag != null) {// 审批状态变更
				user_srv.approveUserByUniId(unique_id, flag,state,current_user);
			}
			// 变更审批状态后重新查询数据
			PageSplit pageSplit = user_srv.showTempsByDeptManage(pageIndex,
					pageSize, userForm, 0);// 用户审批模块,status=0

			// if((pageSplit==null)||(pageSplit.equals(null))){
			// return mapping.findForward("error");
			// }if(pageSplit.getDatas().size()<=0){
			// return mapping.findForward("error");
			// }
			request.setAttribute("pageSplit", pageSplit);// 封装数据
			request.setAttribute("user_id", user_id);
			request.setAttribute("user_name", user_name);
			request.setAttribute("dept_no", dept_no);
			request.setAttribute("create_name", create_name);
			request.setAttribute("start_time", start_time);
			request.setAttribute("end_time", end_time);

			return mapping.findForward("successSer");
		}

		return mapping.findForward("success");
	}
}
