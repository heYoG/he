package com.dj.seal.sealBody.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.cert.web.form.CertForm;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.util.struts.BaseAction;

public class SealEditGuideAction extends BaseAction{

	static Logger logger = LogManager.getLogger(SealEditGuideAction.class.getName());

	private ISealBodyService seal_body;
	  private CertSrv certF; 
	    
	public CertSrv getCertF() {
			return certF;
	}

	public void setCertF(CertSrv certF) {
			this.certF = certF;
	}

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
		   int seal_id=0;
		    if(request.getParameter("seal_id")!=null&&request.getParameter("seal_id")!="")
		    {
		    	seal_id=Integer.parseInt(request.getParameter("seal_id"));
		    }
		    SealBody seal=seal_body.getSealBodys(seal_id);
		    String seal_name=request.getParameter("seal_name");//印章名称
			String seal_type=request.getParameter("seal_type");//印章类别
			String dept_no=request.getParameter("dept_no");//所属单位
			String is_junior=request.getParameter("is_junior");//是否允许下级
			if(seal.getSeal_type().indexOf("公章")!=-1){
				request.setAttribute("sealtype","1");
			}else{
				request.setAttribute("sealtype","0");
			}
			List<CertForm> c_list=certF.showCerts();
	    	logger.info("list_obj"+c_list.size());
	    	request.setAttribute("c_list", c_list);
	    	String cert_name=certF.CertName(seal.getKey_sn());
	    	request.setAttribute("cert_no", seal.getKey_sn());
	    	request.setAttribute("cert_name", cert_name);
		    request.setAttribute("is_junior", is_junior);// 封装数据
			request.setAttribute("seal_name", seal_name);
			request.setAttribute("seal_type", seal_type);
			request.setAttribute("dept_no", dept_no);
		    request.setAttribute("seal", seal);
		    request.setAttribute("type",request.getParameter("type"));
		    
			return mapping.findForward("success");
	}
}
