package com.dj.seal.organise.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.IUserService;

import jxl.Sheet;
import jxl.Workbook;

public class UploadDeptAndUserService {
	static Logger logger = LogManager.getLogger(UploadDeptAndUserService.class.getName());
	private IUserService user_srv;
	private ISysDeptService dept_srv;
	
	
	public String uploadDeptExcel(String filePath){
		String result="";
		  Workbook rwb = null;
		  try{
		   InputStream is = new FileInputStream(filePath);
		   rwb = Workbook.getWorkbook(is);
		   Sheet rs = rwb.getSheet(0); // 获取第一张Sheet表
		   int lll = rs.getRows();
		   if(lll>2000){
			   return "失败：行数大于2000，请检查文件是否正确";
		   }
		   logger.info("总行数："+lll);
		   for (int k = 1; k < lll; k++){
		       String shengfen=rs.getCell(0, k).getContents();//省份
		       String dishi=rs.getCell(1, k).getContents();//地市
		       String yingyeting=rs.getCell(2, k).getContents();//营业厅
		       dishi=dishi.replace(" ", "");//去除空格
		       yingyeting=yingyeting.replace(" ", "");//去除空格
		       logger.info(shengfen+"  "+dishi+"  "+yingyeting);
		       if(dishi==null||dishi.equals("")||yingyeting==null||yingyeting.equals("")){
		    	   result+="第"+(k+1)+"行:"+"内容不能为空<br>";
		    	   continue;
		       }
		       String ret = dept_srv.piLiangImport(dishi, yingyeting);
		       result+="第"+(k+1)+"行:"+ret+"<br>";
		   }
		   rwb.close();
		   logger.info("总行数："+lll);
		  }catch (Exception e){
			  logger.error(e.getMessage());
			  result+="解析文件失败或处理失败，请确认文件是否正确<br>";
		  }
		  return result;
	}

	public String uploadUserExcel(String filePath,String create_userid){
		String result="";
		  Workbook rwb = null;
		  try{
		   InputStream is = new FileInputStream(filePath);
		   rwb = Workbook.getWorkbook(is);
		   Sheet rs = rwb.getSheet(0); // 获取第一张Sheet表
		   int lll = rs.getRows();
		   if(lll>2000){
			   return "失败：行数大于2000，请检查文件是否正确";
		   }
		   logger.info("总行数："+lll);
		   if(create_userid==null||create_userid.equals("")){
			   return "失败：未获取到创建人信息";
	       }
		   for (int k = 1; k < lll; k++){
		       String gonghao=rs.getCell(0, k).getContents();//工号
		       String xingming=rs.getCell(1, k).getContents();//姓名
		       String yingyeting=rs.getCell(2, k).getContents();//营业厅
		       String juese=rs.getCell(3, k).getContents();//角色
		       gonghao=gonghao.replace(" ", "");//去除空格
		       xingming=xingming.replace(" ", "");//去除空格
		       yingyeting=yingyeting.replace(" ", "");//去除空格
		       juese=juese.replace(" ", "");//去除空格
		       logger.info(gonghao+"  "+xingming+"  "+yingyeting+"  "+juese+"  "+create_userid);
		       if(gonghao==null||gonghao.equals("")||xingming==null||xingming.equals("")||yingyeting==null||yingyeting.equals("")||juese==null||juese.equals("")){
		    	   result+="第"+(k+1)+"行:"+"内容不能为空<br>";
		    	   continue;
		       }
		       String ret = user_srv.piLiangImportUser(gonghao, xingming, yingyeting, juese, create_userid);
		       result+="第"+(k+1)+"行:"+ret+"<br>";
		   }
		   rwb.close();
		  }catch (Exception e){
			  logger.error(e.getMessage());
			  result+="解析文件失败或处理失败，请确认文件是否正确<br>";
		  }
		  return result;
	}

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	public ISysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(ISysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}

	
}
