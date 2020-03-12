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

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.struts.BaseAction;

public class EditUserAction extends BaseAction {

	static Logger logger = LogManager.getLogger();

	private IUserService user_srv;
	private UserForm userForm;
	private ISysRoleService role_srv;
	private CertSrv cert_srv;

	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}

	public ISysRoleService getRole_srv() {
		return role_srv;
	}

	public void setRole_srv(ISysRoleService role_srv) {
		this.role_srv = role_srv;
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
		HttpSession session = request.getSession();
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		// BeanUtils.copyProperties(userForm, (UserForm) form);
		String user_id = request.getParameter("user_id");
		String type=request.getParameter("is_app");//判断是否为审批操作的修改
		SysUser sysuser = user_srv.editSysUser(user_id);// 根据用户ID查询用户信息
		String roleno7 = user_srv.serSysUserRole(user_id);//根据用户id查询角色
		List<SysRole> roles = role_srv.showSysRolesByUser_id(user_id);
		String rolename = "";
		for (SysRole sysRole : roles) {
			rolename += sysRole.getRole_name() + ",";
		}
		session.setAttribute("roleno7", roleno7); // 用户拥有的角色表的角色id
		String unitname = user_srv.serSysUnitUtil(sysuser.getDept_no()); // 用户拥有的部门范围
		// String[] seal_app=user_srv.editSealApp(user_id);//盖章拥有的业务
		// session.setAttribute("seal_app",seal_app);
		if (sysuser.getUseing_key() != null
				&& !sysuser.getUseing_key().equals("0")) {
			Cert cert = cert_srv.getObjByName(sysuser.getKey_sn());
			// logger.info("cert:"+cert.getFile_content());
			if (cert != null) {
				request.setAttribute("userCurrCert", cert);
			}
		}
		request.setAttribute("type", type);
		session.setAttribute("unitname", unitname);
		session.setAttribute("userip", sysuser.getUser_ip());
		session.setAttribute("rolename", rolename);
		request.setAttribute("sysuser", sysuser);
		return mapping.findForward("success");
	}
}
