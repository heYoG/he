package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;

public class ShowUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserForm userForm;
	private IUserService user_srv;

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
		// 每页记录数
		int pageSize = 5;
		// 要显示审批信息的对应用户名
		String user_id = request.getParameter("userId");
		// 分页查询
		PageSplit pageSplit = user_srv.showUserByUserId(pageIndex, pageSize,
				user_id);
		request.setAttribute("showUser", pageSplit);//封装数据
		return mapping.findForward("success");

	}
}
