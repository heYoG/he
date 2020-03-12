package com.dj.seal.organise.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.impl.SysRoleService;
import com.dj.seal.organise.web.form.RoleForm;
import com.dj.seal.util.struts.BaseAction;

public class RoleAddAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(RoleAddAction.class.getName());
	
	private SysRoleService role_srv;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoleForm f = (RoleForm) form;
		role_srv.addRole(f);
		return mapping.findForward("success");
	}

	public SysRoleService getRole_srv() {
		return role_srv;
	}

	public void setRole_srv(SysRoleService role_srv) {
		this.role_srv = role_srv;
	}

}
