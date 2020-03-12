package com.dj.seal.sealBody.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.struts.BaseAction;

public class SealSetGuideAction extends BaseAction{

	static Logger logger = LogManager.getLogger(SealSetGuideAction.class.getName());

	private ISealBodyService seal_body;

	public ISealBodyService getSeal_body() {
		return seal_body;
	}

	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//判断用户是否登陆
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String seal_id=request.getParameter("seal_id");
		List<SysUser> listuser=seal_body.getUser(seal_id);
		List<SysRole> listrole=seal_body.getRole(seal_id);
		SealBody sealBody = seal_body.getSealBodys(Integer.valueOf(seal_id));
		String seal_types = sealBody.getSeal_types();
		if(listuser==null){
			request.setAttribute("user_id", "");
			request.setAttribute("user_name", "");
		}else{
			String userid="";
			String username="";
			for(SysUser obj:listuser){
				userid+=obj.getUser_id()+",";
				username+=obj.getUser_name()+",";
			}
			request.setAttribute("user_id", userid);
			request.setAttribute("user_name",username);
		}
		if(listrole==null){
			request.setAttribute("role_nos", "");
			request.setAttribute("role_names", "");
		}else{
			String roleid="";
			String rolename="";
			for(SysRole obj:listrole){
				roleid+=obj.getRole_id()+",";
				rolename+=obj.getRole_name()+",";
			}
			
			request.setAttribute("role_nos", roleid);
			request.setAttribute("role_names", rolename);
		}
		request.setAttribute("seal_id", seal_id);
		request.setAttribute("seal_types", seal_types);
		return mapping.findForward("success");
	}
	
}
