package com.dj.seal.sealBody.web.action;

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
import com.dj.seal.cert.web.form.CertForm;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;
/**
 * 得到证书列表所有
 * @author Hp
 *
 */
public class CerModeListAction extends BaseAction{

	static Logger logger = LogManager.getLogger(CerModeListAction.class.getName());

	
	private CertSrv cer_srv;
	private CertForm certForm;
	private ISealBodyService seal_srv;
	
		
	public ISealBodyService getSeal_srv() {
		return seal_srv;
	}

	public void setSeal_srv(ISealBodyService seal_srv) {
		this.seal_srv = seal_srv;
	}

	public CertForm getCertForm() {
		return certForm;
	}

	public void setCertForm(CertForm certForm) {
		this.certForm = certForm;
	}

	public CertSrv getCer_srv() {
		return cer_srv;
	}

	public void setCer_srv(CertSrv cer_srv) {
		this.cer_srv = cer_srv;
	}
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session =request.getSession();
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		// 获得当前页
		int pageIndex = 1;
		if (request.getParameter("pageIndex") != null) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		}
		// 获得每页显示数
		int pageSize = Constants.PAGESIZE;
		String seal_id = "";
		if (request.getParameter("seal_id") != null) {
				seal_id = request.getParameter("seal_id");
		}
		    // 封装备选证书列表
		PageSplit pageSplit = cer_srv.showAllCert(pageIndex, pageSize);
		request.setAttribute("pageSplit", pageSplit);
		// 封装已选证书
		List<CertForm> cert = cer_srv.showCersBySeal_id(seal_id);
		String cert_name = "";
		for (CertForm certForm : cert) {
			cert_name = certForm.getCert_name();
		}
		if (!cert_name.equals("")) {
			request.setAttribute("cert_name", cert_name);
		} else {
			request.setAttribute("cert_name", "无");
		}
		SealBody sealBody = seal_srv.getSealBodys(Integer.valueOf(seal_id));
		String seal_name = sealBody.getSeal_name();
		request.setAttribute("seal_name", seal_name);
		request.setAttribute("seal_id", seal_id);
		request.setAttribute("key_sn",sealBody.getKey_sn());
		return mapping.findForward("success");
	}
	
}
