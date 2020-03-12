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
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.UserCert;
import com.dj.seal.util.Constants;
import com.dj.seal.util.encrypt.MD5Util;
import com.dj.seal.util.struts.BaseAction;
import com.dj.sign.Base64;



public class UserAddAction extends BaseAction {

	static Logger logger = LogManager.getLogger(UserAddAction.class.getName());

	private IUserService user_srv;
	private UserForm userForm;
	private CertSrv cert_srv;
	
	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public IUserService getUser_srv() {
		return user_srv;
	}
	


	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session =request.getSession();
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		int usertype=Integer.parseInt(request.getParameter("type"));//获取页面类型（1：跳到新增页面2：跳到增加用户action）
		if(usertype==1){
	      return mapping.findForward("new");
		}else{
		  String is_active=request.getParameter("is_active");//是否禁止登陆
		  String user_remark=request.getParameter("user_remark");//备注信息
		  //String useing_key=request.getParameter("useing_key"); //是否使用证书1使用0不使用
		  SysUser sysuser=(SysUser)session.getAttribute(Constants.SESSION_CURRENT_USER);
		  BeanUtils.copyProperties(userForm, form);
		  userForm.setIs_active(is_active);
		  userForm.setCreate_name(sysuser.getUser_id());
		  userForm.setUser_remark(user_remark.trim()); 
		  userForm.setIs_active(is_active);
		  userForm.setInitial_password(userForm.getUser_psd());//初始密码	
		  userForm.setCurrentpassword(userForm.getUser_psd());//当前初始密码
		  userForm.setUser_psd(MD5Util.MD5(userForm.getUser_psd()));//md5加密密码
		  userForm.setIs_approve(Constants.IS_APPROVE_Y);//初始审批状态为审批通过(审批功能未使用)
		  	  
		  //修改状态。新增默认状态为注销状态
		  userForm.setUser_type(Constants.ZXUSER_STATUS);//默认为注销状态
		  if(userForm.getUseing_key()==null||userForm.getUseing_key().equals("")){
			  userForm.setUseing_key("0");
		  }
		  String str=user_srv.AddUser(userForm,sysuser.getUser_id());//保存用户信息
	      if (str.equals("existed")) {
		   return mapping.findForward("existed");
		  }	
	      if(userForm.getUseing_key()!=null&&!userForm.getUseing_key().equals("0")){
	    	  if(cert_srv.getObjByName(userForm.getKey_sn())!=null){//如果证书已经存在
	    		  cert_srv.updateUserCert(userForm.getUser_id());
	    		  cert_srv.updateUserCertToIsActive(userForm.getKey_sn());
	    	  }else{
		    	  Cert cert=new Cert();
		    	  DateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
		    	  if(userForm.getBegin_time()!=null&&!userForm.getBegin_time().equals("")){
		    		  Date begin=format.parse(userForm.getBegin_time());
				      cert.setBegin_time(new Timestamp(begin.getTime()));
		    	  }		    	  
			      cert.setCert_dn(userForm.getKey_dn());
			      cert.setCert_issue(userForm.getCert_issue());
			      cert.setCert_name(userForm.getKey_sn());			     
			      String cert_no=cert_srv.getCertMaxNo();
			      cert.setCert_no(cert_no);
			      cert.setCert_user(userForm.getCert_user());
			      if(userForm.getUseing_key().equals("1")||userForm.getUseing_key().equals("4")){
				      cert.setCert_src(CertType.clientCert);
				      if(userForm.getUseing_key().equals("4")){
				    	  FormFile file = userForm.getCertContent();
				    	  byte[] content1 = file.getFileData();
						  if(content1!=null){
							  String certContent=Base64.encodeToString(content1);
							  userForm.setKey_cert(certContent);
						  }
				      }
			      }else if(userForm.getUseing_key().equals("2")){
			    	  cert.setCert_src(CertType.clientPFX);
			    	  FormFile file = userForm.getPfxContent();
			    	  byte[] content1 = file.getFileData();
					  if(content1!=null){
						  String pfxContent=Base64.encodeToString(content1);
						  cert.setPfx_content(pfxContent);
						  cert.setCert_psd(userForm.getPfxPsw());
					  }
			      }else if(userForm.getUseing_key().equals("3")){
			    	  cert.setCert_src(CertType.IEPFX);
			      }
			      cert.setReg_user(sysuser.getUser_id());
			      cert.setReg_time(new Timestamp(new java.util.Date().getTime()));
			      if(userForm.getEnd_time()!=null&&!userForm.getEnd_time().equals("")){
				      Date end=format.parse(userForm.getEnd_time());
				      cert.setEnd_time(new Timestamp(end.getTime()));
		    	  }
			      cert.setFile_content(userForm.getKey_cert());
			      cert_srv.saveCert(cert);
			      UserCert uc=new UserCert();
			     // uc.setCert_no(cert_no);

			      uc.setCert_no(cert.getCert_no());
			      uc.setUser_id(userForm.getUser_id());
			      uc.setIs_active("1");
			      cert_srv.updateUserCert(userForm.getUser_id());
			      cert_srv.addUserCert(uc);
	    	  }
	      }	      
	      return mapping.findForward("success");
		}
	}
}
