package com.dj.seal.sealBody.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;
/**
 * 跳转到显示角色列表的模式窗口时所用
 * @author yc
 * @since2009-12-01
 * 
 */
public class SealSetRoleAction extends BaseAction{

	static Logger logger = LogManager.getLogger(SealSetRoleAction.class.getName());

	private ISealBodyService seal_body;
	private ISysRoleService role_srv;
	public ISealBodyService getSeal_body() {
		return seal_body;
	}
	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}
	public ISysRoleService getRole_srv() {
		return role_srv;
	}
	public void setRole_srv(ISysRoleService role_srv) {
		this.role_srv = role_srv;
	}
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//判断用户是否登陆
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String user_status=Constants.USER_STATUS_NOMOR;
		if(request.getParameter("user_status")!=null){
			user_status=request.getParameter("user_status");
		}
		String user_id="";
		if(request.getParameter("user_id")!=null){
			user_id=request.getParameter("user_id");
		}
		//封装备选角色列表
		List<SysRole> list1=role_srv.showRolesByTab(user_status);
		request.setAttribute("roles1", list1);
		//封装已选角色列表
		List<SysRole> list2=role_srv.showSysRolesByUser_id(user_id);
		request.setAttribute("roles2", list2);
		
		return mapping.findForward("success");
	}
	
}
