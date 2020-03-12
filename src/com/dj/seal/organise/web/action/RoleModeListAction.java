package com.dj.seal.organise.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 跳转到模式窗口时所用
 * 
 * @author oyxy
 * @since2009-11-24
 * 
 */
public class RoleModeListAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(RoleModeListAction.class.getName());

	private ISysRoleService role_srv;

	public ISysRoleService getRole_srv() {
		return role_srv;
	}

	public void setRole_srv(ISysRoleService role_srv) {
		this.role_srv = role_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String user_status = Constants.USER_STATUS_NOMOR;
		if (request.getParameter("user_status") != null) {
			user_status = request.getParameter("user_status");
		}
		String user_id = "";
		if (request.getParameter("user_id") != null) {
			user_id = request.getParameter("user_id");
		}
		List<SysRole> list3 = new ArrayList<SysRole>();
		// 封装已选角色列表
		List<SysRole> list2 = role_srv.showSysRolesByUser_id(user_id);
		request.setAttribute("roles2", list2);
		// 封装备选角色列表
		List<SysRole> list1 = role_srv.showRolesByTab(user_status);
		if(list2.size()!=0){
			list2.get(0);
			if(!list2.get(0).getRole_name().equals("系统管理员")){
				for (SysRole sysRole : list1) {
					if(sysRole.getRole_name().equals("系统管理员")||sysRole.getRole_name().equals("稽核员")){
						list3.add(sysRole);
					}
				}
			}
			list1.removeAll(list3);
		}
		request.setAttribute("roles1", list1);
		
		return mapping.findForward("success");
	}
}
