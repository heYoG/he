package com.dj.seal.cert.web.action;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.cert.web.form.CertForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.struts.BaseAction;
import com.dj.seal.util.Constants;
import com.dj.sign.Base64;
public class NewCertAction extends BaseAction {
	static Logger logger = LogManager.getLogger(NewCertAction.class.getName());
	private CertSrv cert_srv;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		CertForm f = (CertForm) form;	
		FormFile file = f.getCert_path();
		String filepath="";
		String bpath = bpath();
		try {
			byte[] content1 = file.getFileData();
			logger.info("cert-length:"+new String(content1).length());
			if (file.getFileSize() > 0) {
				"application/x-pkcs12".equals(file.getContentType());// 说明是pfx文件
				String path = request.getSession().getServletContext()
						.getRealPath("");// 获取web服务器地址
				if (System.getProperty("os.name").toUpperCase().indexOf(
				"WINDOWS") != -1) {
				filepath=bpath + "/doc/certs/"+ f.getCert_name()
				+ ".pfx";
				File file1 = new File(filepath);
				FileOutputStream out = new FileOutputStream(file1);
				out.write(content1);
				out.close();
				}else{
				filepath=Constants.getProperty("savecert")+"\\"+ f.getCert_name()
				+ ".pfx";
				File file1 = new File(Constants.getProperty("savecert")+"\\"+ f.getCert_name()
							+ ".pfx");
				FileOutputStream out = new FileOutputStream(file1);
				out.write(content1);
				out.close();
				}
				String pfxContent=Base64.encodeToString(content1);
				f.setFile_content(pfxContent);
				f.setCert_detail(filepath);
		    	}
				f.setReg_user(user.getUser_id());
				DesUtils des = new DesUtils();
				f.setCert_psd(des.encrypt(f.getCert_psd()));
				cert_srv.addCertData(f);	
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return mapping.findForward("success");
	}

	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}
	private static String bpath() throws Exception {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return bpath;
	}

}
