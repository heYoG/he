package com.dj.seal.organise.web.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.cert.util.CertType;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.UserCert;
import com.dj.seal.util.Constants;
import com.dj.seal.util.encrypt.MD5Util;
import com.dj.seal.util.struts.BaseAction;
import com.dj.sign.Base64;



@SuppressWarnings("serial")
public class UpdateUserAction extends BaseAction   {

	static Logger logger = LogManager.getLogger(UpdateUserAction.class.getName());

	private IUserService user_srv;
	private ISysUserDao su_dao;
	private UserForm userForm;
	private CertSrv cert_srv;
	
	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
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
		
	public ISysUserDao getSu_dao() {
		return su_dao;
	}

	public void setSu_dao(ISysUserDao su_dao) {
		this.su_dao = su_dao;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		BeanUtils.copyProperties(userForm, form);
		SysUser sysUser=su_dao.showSysUserByUser_id(userForm.getUser_id());//获取用户信息
		
		String type=request.getParameter("is_app");//修改类型:用户审批、用户管理和第一次登录密码修改
		String user_psd=null;
		String user_psd2=null;//针对用户管理密码修改
		if(!type.equals("updatePsd")){//非第一次登录更新密码须验证是否已登录
			if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
				return mapping.findForward("no_login");
			}
			/*用户管理修改或未修改密码,必有user_psd≠user_psd2,
			 * 如修改了则必有当前密码≠user_psd≠user_psd2,此时更新密码,同时logined+1;
			 * 未修改则必有当前密码=user_psd≠user_psd2,此时不更新密码,logined保持不变*/
			user_psd=userForm.getUser_psd();//直接获取的密码属性值
			user_psd2=MD5Util.MD5(userForm.getUser_psd());//MD5加密
		}else{//第一次登录更新密码
			user_psd=MD5Util.MD5(userForm.getUser_psd());//未经MD5加密密码
			user_psd2=user_psd;//必修改了密码
		}
	
		if(!sysUser.getUser_psd().equals(user_psd)&&!sysUser.getUser_psd().equals(user_psd2)){//更新密码
			if(sysUser.getLogined()==0){//第一次更新密码保存初始密码(当前使用)为旧密码
				userForm.setPassword1(sysUser.getCurrentpassword());
				userForm.setPassword1md5(sysUser.getUser_psd());
			}else if(sysUser.getLogined()==1){//第二次更新密码保存第一次更新密码(当前使用)为旧密码
				userForm.setPassword2(sysUser.getCurrentpassword());
				userForm.setPassword2md5(sysUser.getUser_psd());
			}else{//2次更新后覆盖更新保留最近三个密码
				userForm.setPassword1(sysUser.getPassword2());//第一个旧密码更新为第二个旧密码
				userForm.setPassword1md5(sysUser.getPassword2md5());
				userForm.setPassword2(sysUser.getCurrentpassword());//第二个旧密码更新为当前密码(更新前正在使用密码)
				userForm.setPassword2md5(sysUser.getUser_psd());
			}	
			userForm.setCurrentpassword(userForm.getUser_psd());//当前原始密码
			userForm.setUser_psd(MD5Util.MD5(userForm.getUser_psd()));//当前MD5加密密码
			userForm.setLogined(sysUser.getLogined()+1);//修改密码则+1
		}else{//针对不修改密码情况	
			userForm.setLogined(sysUser.getLogined());
		}
		if (userForm.getUseing_key() == null|| userForm.getUseing_key().equals("")) {
			userForm.setUseing_key("0");
		}
		String role_names = request.getParameter("role_names");
 		String role_nos = request.getParameter("role_nos");
		String dept_no = request.getParameter("dept_no");
		String user_remark2 = request.getParameter("user_remark2");
		String user_birth=request.getParameter("user_birth").substring(0,request.getParameter("user_birth").indexOf(" "));
		String user_theme=request.getParameter("user_theme");
		userForm.setDept_no(dept_no);
		userForm.setUser_remark(user_remark2.trim()); //备注信息
		userForm.setRole_nos(role_nos);
		userForm.setRole_names(role_names);
		userForm.setUser_birth(user_birth);    
		userForm.setUser_theme(user_theme);
		userForm.setCreate_data(sysUser.getCreate_data().toString());
		//userForm.setState("");//修改后审批意见变为空
		//userForm.setIs_approve("0");//待审批
		user_srv.updateSysUser(userForm); //更新用户表信息
		cert_srv.deleteCertInfo(userForm.getUser_id());//先删除后增加 
		if (userForm.getUseing_key() != null&& !userForm.getUseing_key().equals("0")) {
			if (cert_srv.getObjByName(userForm.getKey_sn()) != null) {//如果证书已经存在
				cert_srv.updateUserCert(userForm.getUser_id());
				cert_srv.updateUserCertToIsActive(userForm.getKey_sn());
			} else {		
				SysUser sysuser = (SysUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
				Cert cert = new Cert();
				DateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
				if (userForm.getBegin_time() != null&& !userForm.getBegin_time().equals("")) {
					Date begin = format.parse(userForm.getBegin_time());
					cert.setBegin_time(new Timestamp(begin.getTime()));
				}
				cert.setCert_dn(userForm.getKey_dn());
				cert.setCert_issue(userForm.getCert_issue());
				cert.setCert_name(userForm.getKey_sn());
				String cert_no = cert_srv.getCertMaxNo();
				cert.setCert_no(cert_no);
				cert.setCert_user(userForm.getCert_user());
				if (userForm.getUseing_key().equals("1")) {
					cert.setCert_src(CertType.clientCert);
				} else if (userForm.getUseing_key().equals("2")) {
					cert.setCert_src(CertType.clientPFX);
					FormFile file = userForm.getPfxContent();
					byte[] content1 = file.getFileData();
					if (content1 != null) {
						String pfxContent = Base64.encodeToString(content1);
						cert.setPfx_content(pfxContent);
						cert.setCert_psd(userForm.getPfxPsw());
					}
				} else if (userForm.getUseing_key().equals("3")) {
					cert.setCert_src(CertType.IEPFX);
				}
				cert.setReg_user(sysuser.getUser_id());
				cert.setReg_time(new Timestamp(new java.util.Date().getTime()));
				if (userForm.getEnd_time() != null
						&& !userForm.getEnd_time().equals("")) {
					Date end = format.parse(userForm.getEnd_time());
					cert.setEnd_time(new Timestamp(end.getTime()));
				}
				cert.setFile_content(userForm.getKey_cert());
				cert_srv.saveCert(cert);
				UserCert uc = new UserCert();

				uc.setCert_no(cert.getCert_no());
				uc.setUser_id(userForm.getUser_id());
				uc.setIs_active("1");
				cert_srv.updateUserCert(userForm.getUser_id());
				cert_srv.addUserCert(uc);
			}
		} else if (userForm.getUseing_key() != null
				&& userForm.getUseing_key().equals("0")) {
			cert_srv.updateUserCert(userForm.getUser_id());
		}
		if("true".equals(type)){
			return mapping.findForward("success_app");//审批模块的修改
		}else if("updatePsd".equals(type)){//修改密码后跳转到登录界面
			return mapping.findForward("tologin");
		}
		  return mapping.findForward("success");//管理模块的修改
	}

}
