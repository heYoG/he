package com.dj.seal.cert.web.action;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.cert.web.form.CertForm;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;

public class EditCertAction extends BaseAction {
	static Logger logger = LogManager.getLogger(EditCertAction.class.getName());
	private CertSrv cert_srv;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CertForm f = (CertForm) form;
		cert_srv.updCert(f);
		FormFile file = f.getCert_path();
		try {
			byte[] content1 = file.getFileData();
			if (file.getFileSize() > 0) {
				"application/x-pkcs12".equals(file.getContentType());// 说明是pfx文件
				String path = request.getSession().getServletContext()
						.getRealPath("");// 获取web服务器地址
				if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
				   File file1 = new File(path + "/certs/" + f.getCert_name()
						+ ".pfx");
				   FileOutputStream out = new FileOutputStream(file1);
				   out.write(content1);
				   out.close();
				}else{
					File file1 = new File(Constants.getProperty("savecert")+"\\" + f.getCert_name()
							+ ".pfx");
					FileOutputStream out = new FileOutputStream(file1);
					out.write(content1);
					out.close();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
//			e.getStackTrace();
		}
		return mapping.findForward("success");
	}

	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}
}
