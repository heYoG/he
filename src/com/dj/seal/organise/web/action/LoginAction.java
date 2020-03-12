package com.dj.seal.organise.web.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.organise.service.api.ISysFuncService;
import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.LoginForm;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.ViewInterface;
import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.system.service.api.ISystemService;
import com.dj.seal.system.service.api.ITableModuleService;
import com.dj.seal.util.Constants;
import com.dj.seal.util.License;
import com.dj.seal.util.encrypt.MD5Util;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;
import com.dj.seal.util.struts.SysMenu;

/**
 * 登录
 * 
 * @author oyxy
 * 
 */
public class LoginAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(LoginAction.class.getName());
	private IUserService user_srv;
	private ISysFuncService func_srv;
	private ISystemService sys_srv;
	private ITableModuleService modu_srv;
	private LoginForm loginForm;
    private ISysUnitService sys_unit;
    private ISysRoleService role_srv;
    private CertSrv cert_srv;
    
	public ISysUnitService getSys_unit() {
		return sys_unit;
	}

	public void setSys_unit(ISysUnitService sys_unit) {
		this.sys_unit = sys_unit;
	}

	public ISystemService getSys_srv() {
		return sys_srv;
	}

	public void setSys_srv(ISystemService sys_srv) {
		this.sys_srv = sys_srv;
	}

	public ISysRoleService getRole_srv() {
		return role_srv;
	}

	public void setRole_srv(ISysRoleService role_srv) {
		this.role_srv = role_srv;
	}

	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}

	
	/**
	 * 登录系统平台
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		try {
			BeanUtils.copyProperties(loginForm, form);
			loginForm.setUser_psd(MD5Util.MD5(loginForm.getUser_psd()));//MD5加密密码
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		HttpSession session = request.getSession();
		//判断系统是否过期
		try {
			String ad=License.getAbleDate();
			if (ad.equals("1")){
				request.setAttribute("fail_msg", "对不起，系统已过有效期，请联系管理员！");
				return mapping.findForward("fail");
			}
			String licenseUnitName=sys_unit.LicenseUnit();//授权单位名称
			//logger.info(licenseUnitName);
			session.setAttribute("licenseUnitName",licenseUnitName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
		
		// 获得ip
		String ip = request.getRemoteHost();
		// 调用业务层方法 登录
		SysUser user =null;
		try{
			String type = request.getParameter("type");//决定跳转页面
			String psdLastTime = user_srv.getPasswordLastTime();//获取密码有效时长(天)
			int fb=user_srv.isActive(loginForm);
			if(fb==1){
				// 用户为禁止登录用户
				request.setAttribute("fail_msg", "对不起，您的用户名已被注销不能登录系统!");
				return mapping.findForward("fail");
			}else if(fb==0){
				// 如果返回结果为空，说明登录信息输入有误，跳转至错误页面
				request.setAttribute("fail_msg", "用户名或密码错误，注意大小写!");
				return mapping.findForward("fail");
			}else{
				user = user_srv.userLogin(loginForm, ip);
				if(user==null){
					request.setAttribute("fail_msg", "用户名或密码错误，注意大小写!");
					return mapping.findForward("fail");
				}else{
					long existTime=(System.currentTimeMillis()-user.getOperate_time().getTime())/(1000*60*60*24);//密码已使用时长(天)
					
					if(user.getLogined()==0||existTime>Integer.parseInt(psdLastTime) ){//第一次登录或密码已过期
						if(type.equals("toUpdatePassword")){
							request.setAttribute("user_id", user.getUser_id());
							request.setAttribute("fail_msg", "首次登录或密码已过期,请修改密码!");
							return mapping.findForward("toUpdatePassword");							
						}else{
							String rolename = "";
							SysUser sysuser = user_srv.editSysUser(user.getUser_id());// 根据用户ID查询用户信息
							String roleno7 = user_srv.serSysUserRole(user.getUser_id());//根据用户id查询角色
							List<SysRole> roles = role_srv.showSysRolesByUser_id(user.getUser_id());
							for (SysRole sysRole : roles) {
								rolename += sysRole.getRole_name() + ",";
							}
							session.setAttribute("roleno7", roleno7); // 用户拥有的角色表的角色id
							String unitname = user_srv.serSysUnitUtil(sysuser.getDept_no()); // 用户拥有的部门范围
							if (sysuser.getUseing_key() != null&& !sysuser.getUseing_key().equals("0")) {
								Cert cert = cert_srv.getObjByName(sysuser.getKey_sn());
								if (cert != null) {
									request.setAttribute("userCurrCert", cert);
								}
							}
							session.setAttribute("unitname", unitname);
							session.setAttribute("userip", sysuser.getUser_ip());
							session.setAttribute("rolename", rolename);
							request.setAttribute("sysuser", sysuser);					
							return mapping.findForward("updatePassword");					
						}
					}
				}				
			}
		}catch(GeneralException e){
			 e.printStackTrace();
			 session = request.getSession();
			 user = user_srv.userLogin(loginForm, ip);
		}catch(Exception er){
			er.printStackTrace();
			return mapping.findForward("tologin");
		}
	
		// 登录成功，把登录用户放入session中
		if (session.getAttribute(Constants.SESSION_CURRENT_USER) == null) {
			session.setAttribute(Constants.SESSION_CURRENT_USER, user);
		} else {
			session.removeAttribute(Constants.SESSION_CURRENT_USER);
			session.setAttribute(Constants.SESSION_CURRENT_USER, user);
		}
		session.setAttribute("user_ip", request.getRemoteHost());
		// 根据用户名称得到用户具有的权限集合
		List<SysMenu> list_menu = func_srv.showMenusByUser_id(user.getUser_id());
		// 把权限集合封装到session中去
		session.setAttribute(Constants.SESSION_AUTH_TOKEN, list_menu);
		// 得到界面信息
		ViewInterface view_face = sys_srv.selectViewInterface();
		session.setAttribute("view_face", view_face);
		// 得到用户桌面模块信息
		List<ViewTableModule> left_modules = modu_srv.showLeftViewModulesByUser(user.getUser_id());
		session.setAttribute("left_modules", left_modules);
		List<ViewTableModule> right_modules = modu_srv.showRightViewModulesByUser(user.getUser_id());
		session.setAttribute("right_modules", right_modules);
		return mapping.findForward("success");
	}

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public ISysFuncService getFunc_srv() {
		return func_srv;
	}

	public void setFunc_srv(ISysFuncService func_srv) {
		this.func_srv = func_srv;
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	public ITableModuleService getModu_srv() {
		return modu_srv;
	}

	public void setModu_srv(ITableModuleService modu_srv) {
		this.modu_srv = modu_srv;
	}
}
