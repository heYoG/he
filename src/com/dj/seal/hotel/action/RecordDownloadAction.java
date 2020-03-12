package com.dj.seal.hotel.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.hotel.po.RecordPO;
import com.dj.seal.hotel.service.impl.RecordServiceImpl;
import com.dj.seal.util.Constants;
import com.dj.seal.util.file.FileZip;




public class RecordDownloadAction extends Action {
	
	static Logger logger = LogManager.getLogger(RecordDownloadAction.class.getName());
	
	private List<File> files ;
	private RecordServiceImpl recordServ ;
	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public RecordServiceImpl getRecordServ() {
		return recordServ;
	}

	public void setRecordServ(RecordServiceImpl recordServ) {
		this.recordServ = recordServ;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String str = request.getParameter("sel");//获得传入所有单据ID
		
		files = new ArrayList<File>();
		List<RecordPO> recordLists = recordServ.getRecordPos(str);
		logger.info("sel::"+str);
		logger.info("recordLists::"+recordLists.size());
		File file = null;
		RecordPO po = null;
		String filePath = djpath();
		String targetZipFilePath = bpath()+"doc/hotelZipDocs/";
		logger.info("targetZipFilePath:"+targetZipFilePath);
		//将要下载的文件全部add到list集合中
		for(int i=0;i<recordLists.size();i++){
			po = recordLists.get(i);
//			file = new File(filePath+po.getCfilefilename());
			logger.info("!!!!!---"+filePath+po.getCdata());
			file = new File(filePath+po.getCdata());
			
			files.add(file);
			
		}
		//下载并打包文件
		FileZip.ZipFiles(files, targetZipFilePath,request,response);
		return super.execute(mapping, form, request, response);
	}

	
	public static String bpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return bpath;
	}
	public static String djpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("hotelFileSavePath");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return bpath;
	}
	

}
