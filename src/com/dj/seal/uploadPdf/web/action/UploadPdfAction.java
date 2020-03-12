package com.dj.seal.uploadPdf.web.action;

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

import serv.MyOper;
import srvSeal.SrvSealUtil;
import com.dj.seal.uploadPdf.web.form.PdfForm;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.seal.util.struts.BaseAction;
import com.dj.seal.util.Constants;
public class UploadPdfAction extends BaseAction {
	static Logger logger = LogManager.getLogger(UploadPdfAction.class.getName());
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
	private static SrvSealUtil srv_seal() {
		SrvSealUtil srv_seal = (SrvSealUtil) getBean("srvSeal");//
		if (srv_seal == null) {
			srv_seal = new SrvSealUtil();
		}
		return srv_seal;
	}
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PdfForm f = (PdfForm) form;	
		FormFile file = f.getPdf_path();
		String filepath="";
		String bpath = bpath();
		try {
			byte[] content1 = file.getFileData();
			if (file.getFileSize() > 0) {
				filepath=bpath + "doc\\"+ f.getPdf_name()+".pdf";
				File file1 = new File(filepath);
				FileOutputStream out = new FileOutputStream(file1);
				out.write(content1);
				out.close();
				logger.info("写入文件成功");
				SrvSealUtil srv_seal = srv_seal();
				logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
				int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
				logger.info("ocxfile:" + ocxfile);
				int nObjID = srv_seal.openObj(filepath, 0, 0);
				logger.info("MyOper-nObjID:" + nObjID);
				int l = srv_seal.login(nObjID, 4, "HWSEALDEMOXX", "DEMO");
				logger.info("MyOper-login:" + l);
				int pagesize = srv_seal.getPageCount(nObjID);
				String ruleno=f.getRule_no();
				logger.info("ruleno:" + ruleno);
				int a = MyOper.addSealDJ(srv_seal, ruleno, nObjID,pagesize);
				String save_path = bpath + "sealdoc\\"
				+ f.getPdf_name()+ ".pdf";
                int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
                logger.info("s:" + s);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return mapping.findForward("success");
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
