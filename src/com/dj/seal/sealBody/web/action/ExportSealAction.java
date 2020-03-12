package com.dj.seal.sealBody.web.action;

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

import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.sealBody.web.form.ImportSealForm;
import com.dj.seal.util.Constants;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.seal.util.struts.BaseAction;
/**
 *  跳转到导出印章数据页面
 * @author Hp
 *
 */
public class ExportSealAction extends BaseAction{

	static Logger logger = LogManager.getLogger(ExportSealAction.class.getName());

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
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type=request.getParameter("type");
		if(type.equals("0")){
			return mapping.findForward("success");
		}else{
			String bpath = bpath();
			ImportSealForm f = (ImportSealForm) form;	
			FormFile file = f.getSeal_path();
			byte[] content1 = file.getFileData();
			String filepath="";
			if (file.getFileSize() > 0) {
				filepath=bpath + "bakSeal/"+ file.getFileName();
				File file1 = new File(filepath);
				FileOutputStream out = new FileOutputStream(file1);
				out.write(content1);
				out.close();
				logger.info("写入文件成功");
				SealBodyServiceImpl ssObj = (SealBodyServiceImpl) getBean("ISealBodyService");
                String result=ssObj.importSeal(filepath);
                if(result.equals("1")){
                	request.setAttribute("result","导入印章数据成功");
                }else{
                	request.setAttribute("result","导入印章数据失败");
                }
			}
			return mapping.findForward("index");
		}
	}
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
 
}
