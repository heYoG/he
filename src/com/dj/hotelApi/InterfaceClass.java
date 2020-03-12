package com.dj.hotelApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import srvSeal.SrvSealUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.dj.hotelApi.util.HotelFileUtil;
import com.dj.seal.hotel.dao.api.RecordDao;
import com.dj.seal.hotel.po.AdvertImagePO;
import com.dj.seal.hotel.po.HotelAdvertPO;
import com.dj.seal.hotel.po.HotelTClientOperLogPO;
import com.dj.seal.hotel.po.Masterplatejudgep;
import com.dj.seal.hotel.po.RecordAffiliatePO;
import com.dj.seal.hotel.po.RecordContentPO;
import com.dj.seal.hotel.po.RecordPO;
import com.dj.seal.hotel.po.ScannerRecordPO;
import com.dj.seal.hotel.po.VersionPO;
import com.dj.seal.hotel.service.api.IHotelAdvertService;
import com.dj.seal.hotel.service.impl.HotelAdvertServiceImpl;
import com.dj.seal.hotel.service.impl.HotelClientoperLogServiceImpl;
import com.dj.seal.hotel.service.impl.MasterplatejudgeServiceImpl;
import com.dj.seal.hotel.service.impl.RecordServiceImpl;
import com.dj.seal.hotel.service.impl.ScannerRecordServiceImpl;
import com.dj.seal.hotel.service.impl.VersionServiceImpl;
import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.modelFile.service.impl.ModelFileServiceImpl;
import com.dj.seal.modelFile.vo.ModelFileVo;
import com.dj.seal.modelFile.vo.ModelXieyiVo;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.organise.service.impl.UserService;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.neusoft.service.impl.RecordRoomMergeServiceImpl;

public class InterfaceClass {
	static Logger log = LogManager.getLogger(InterfaceClass.class.getName());
	
	/**
	 * 验证用户方式，无纸化柜面。根据柜面传送的用户ID获取该用户角色下的模板和广告。
	 * @param userName 用户的ID
	 * @param ip
	 * @param mac
	 * @return
	 */
//	public static String adLoginVerfy(String userName,String ip,String mac){
//		UserService userServ = (UserService) getBean("IUserService");
//		HotelClientoperLogServiceImpl hotellogServ = (HotelClientoperLogServiceImpl) getBean("clientOperLogService");
//		HotelTClientOperLogPO logpo = new HotelTClientOperLogPO();
//		logpo.setOperuserid(userName);
//		logpo.setCip(ip);
//		logpo.setCmac(mac);
//		logpo.setCopertime(new Timestamp(new Date().getTime()));
//		logpo.setOpertype("1");
//		SysUser user = null;
//		try {
//			user = userServ.showSysUserByUser_id(userName);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			logpo.setResult("1");
//			logpo.setObjname("error:查找用户出错");
//			hotellogServ.addClientOperLogNew(logpo);
//			return "error:查找用户出错！";
//		}
//		if(user==null){
//			logpo.setResult("1");
//			logpo.setObjname("error:无此用户");
//			hotellogServ.addClientOperLogNew(logpo);
//			return "error:无此用户！";
//		}
//		//查找用户角色
//		String role_nos = null;
//		try {
//			role_nos = userServ.serSysUserRole(userName);
//		} catch (GeneralException e) {
//			log.error(e.getMessage());
//			logpo.setResult("1");
//			logpo.setObjname("error:查找用户角色出错");
//			hotellogServ.addClientOperLogNew(logpo);
//			return "error:查找用户角色出错！";
//		}
//		//查找用户所拥有的角色的模板
//		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
//				String masterplateIds = null;
//				try {
//					masterplateIds = modelFileService.getModelFileIdsByRoleIds(role_nos);
//				} catch (Exception e) {
//					log.error(e.getMessage());
//					logpo.setResult("1");
//					logpo.setObjname("error:查找模板出错");
//					hotellogServ.addClientOperLogNew(logpo);
//					return "error:查找模板出错！";
//				}
//				if(masterplateIds==null||masterplateIds.equals("")){
//					logpo.setResult("1");
//					logpo.setObjname("error:没有可用模板");
//					hotellogServ.addClientOperLogNew(logpo);
//					return "error:您没有可用模板！请联系管理员配置模板！";
//				}
//				String xieyiIDs=null;
//				try {
//					xieyiIDs=modelFileService.getXieyiIdsByModelIDs(masterplateIds,user.getDept_no());
//				} catch (Exception e) {
//					log.error(e.getMessage());
//					logpo.setResult("1");
//					logpo.setObjname("error:没有可用协议");
//					hotellogServ.addClientOperLogNew(logpo);
//					return "error:您没有可用协议！请联系管理员配置模板！";
//				}
//				//得到最新的模版协议对应关系
//				List<ModelXieyiVo> modelXieyis=modelFileService.getModelXies(masterplateIds, user.getDept_no());
//				String modelandxieyi=masterplateIds;
//				if(xieyiIDs!=null&&!xieyiIDs.equals("")){
//					modelandxieyi=masterplateIds+","+xieyiIDs;
//				}
//				//得到模板协议版本号字符串
//				String filelist = getVersionNo(modelandxieyi);
//				//得到协议与模版对应关系版本号
//				//String versionStr=getModelXieVersion(modelXieyis,filelist);
//		
//		//得到该用户部门下广告版本信息
//		String adVersions = "FILELIST="+filelist+getDeptAdsVersion(userName);
//		//获取用户部门名称 
//		SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
//		SysDepartment dept = null;
//		try {
//			dept = deptService.showDeptByNo(user.getDept_no());
//		} catch (GeneralException e) {
//			log.error(e.getMessage());
//			logpo.setResult("1");
//			logpo.setObjname("error:查找部门出错");
//			hotellogServ.addClientOperLogNew(logpo);
//			return "error:查找部门出错！";
//		}
//		String dept_name = "";
//		if(dept==null){
//		}else{
//			dept_name = dept.getDept_name();
//		}
//		logpo.setResult("0");
//		logpo.setObjname("成功");
//		logpo.setDeptno(user.getDept_no());
//		hotellogServ.addClientOperLogNew(logpo);
//		//返回客户端版本号，用户真实姓名，部门名称
//		return adVersions+"Real="+user.getUser_name()+";Dept="+dept_name+";";
//	}
	
	/**
	 * 根据用户传递的部门序列号（和银行的组织机构同步，使用的为银行的组织ID）
	 * @param deptNo 部门序列号（银行）
	 * @param ip
	 * @param mac
	 * @return
	 */
	public static String adLoginVerfy(String deptid,String ip,String mac){

		//全部模板      <---dlvidio.jsp
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
				String masterplateIds = null;
				try {
					masterplateIds = modelFileService.getAllModels(deptid);
				} catch (Exception e) {
					log.error(e.getMessage());
					return "error:查找模板出错！";
				}
				if(masterplateIds==null||masterplateIds.equals("")){//走到此处即停止方法继续并返回提示，不会再获取广告版本
					return "error:您没有可用模板！请联系管理员配置模板！";
				}
				//得到模板协议版本号字符串
				//String filelist = getVersionNo(masterplateIds);
				String filelist = getVersionNoRW(masterplateIds);
		
		//得到该用户部门下模板版本和广告版本信息
		String adVersions = "FILELIST="+filelist+getDeptAdsVersionByBank(deptid);
		//返回客户端版本号，用户真实姓名，部门名称
		return adVersions+"Real=bank;Dept=userName;";
	}

	public static String loginVerify(String userName, String password,String ip,String mac,String version) {
		//验证用户  记录登录日志         <---login.jsp
		UserService userServ = (UserService) getBean("IUserService");
		HotelClientoperLogServiceImpl hotellogServ = (HotelClientoperLogServiceImpl) getBean("clientOperLogService");
		HotelTClientOperLogPO logpo = new HotelTClientOperLogPO();
		logpo.setOperuserid(userName);
		logpo.setCip(ip);
		logpo.setCmac(mac);
		logpo.setCopertime(new Timestamp(new Date().getTime()));
		logpo.setOpertype("1");
		SysUser user = null;
		try {
			user = userServ.showSysUserByUser_id(userName);
		} catch (Exception e) {
			log.error(e.getMessage());
			logpo.setResult("1");
			logpo.setObjname("error:查找用户出错");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:查找用户出错！";
		}
		if(user==null){
			logpo.setResult("1");
			logpo.setObjname("error:无此用户");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:无此用户！";
		}else if(!user.getUser_psd().equals(password)){
			logpo.setResult("1");
			logpo.setObjname("error:密码错误");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:密码错误！";
		}
		//查找用户角色
		String role_nos = null;
		try {
			role_nos = userServ.serSysUserRole(userName);
		} catch (GeneralException e) {
			log.error(e.getMessage());
			logpo.setResult("1");
			logpo.setObjname("error:查找用户角色出错");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:查找用户角色出错！";
		}
		if(role_nos==null||role_nos.equals("")){
			logpo.setResult("1");
			logpo.setObjname("error:用户角色不存在");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:用户角色不存在！";
		}
		//获取用户部门名称
		SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
		SysDepartment dept = null;
		try {
			dept = deptService.showDeptByNo(user.getDept_no());
		} catch (GeneralException e) {
			log.error(e.getMessage());
			logpo.setResult("1");
			logpo.setObjname("error:查找部门出错");
			hotellogServ.addClientOperLog(logpo);
			return "error:查找部门出错！";
		}
		String dept_name = "";
		if(dept==null){
		}else{
			dept_name = dept.getDept_name();
		}
		//查找用户所拥有的角色的模板
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		String masterplateIds = null;
		try {
			masterplateIds = modelFileService.getModelFileIdsByRoleIds(role_nos);
		} catch (Exception e) {
			log.error(e.getMessage());
			logpo.setResult("1");
			logpo.setObjname("error:查找模板出错");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:查找模板出错！";
		}
		if(masterplateIds==null||masterplateIds.equals("")){
			logpo.setResult("1");
			logpo.setObjname("error:没有可用模板");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:您没有可用模板！请联系管理员配置模板！";
		}
		String xieyiIDs=null;
		try {
			xieyiIDs=modelFileService.getXieyiIdsByModelIDs(masterplateIds,user.getDept_no());
		} catch (Exception e) {
			log.error(e.getMessage());
			logpo.setResult("1");
			logpo.setObjname("error:没有可用协议");
			hotellogServ.addClientOperLogNew(logpo);
			return "error:您没有可用协议！请联系管理员配置模板！";
		}
		//得到最新的模版协议对应关系
		List<ModelXieyiVo> modelXieyis=modelFileService.getModelXies(masterplateIds, user.getDept_no());
		String modelandxieyi=masterplateIds;
		if(xieyiIDs!=null&&!xieyiIDs.equals("")){
			modelandxieyi=masterplateIds+","+xieyiIDs;
		}
		//得到模板协议版本号字符串
		String filelist = getVersionNo(modelandxieyi);
		//得到协议与模版对应关系版本号
		String versionStr=getModelXieVersion(modelXieyis,filelist);
		String edithtml="";
		String printhtml="";
		//得到编辑页面的下载字符串		
		log.info("filelist:"+filelist);
		log.info(versionStr);
		log.info(version);
		String imageCssList=getImageCSS();
		if(!version.equals(versionStr)){
			
			String topcompanyname = "";
			String yytName="";
			String userdeptno = user.getDept_no();
			//获取分公司部门名称
			if(userdeptno.length()>7){
				SysDepartment topdept = null;
				SysDepartment topdept2 = null;//营业厅名称
				try {
					topdept = deptService.showDeptByNo(userdeptno.substring(0, 8));//截取部门号前8位为分公司的部门编号
					topdept2 = deptService.showDeptByNo(userdeptno);//截取部门号前8位为分公司的部门编号
				} catch (GeneralException e) {
					log.error(e.getMessage());
					return "error:查找部门出错！";
				}
			
				if(topdept==null){
					topcompanyname="云南省分公司";
				}else{
					topcompanyname = topdept.getDept_name();
				}
				if(topdept2==null){
					yytName="云南省联通营业厅";
				}else{
					yytName = topdept2.getDept_name();
				}
			}else{
				topcompanyname="云南省分公司";
				yytName="云南省联通营业厅";
			}
			log.info("营业厅名称："+yytName);
			edithtml=getEditHtmlCode(modelXieyis,masterplateIds,topcompanyname,yytName);
//			try {
//				FileOutputStream os=new FileOutputStream("d:/test/edit.html");
//				os.write(edithtml.getBytes());
//				os.close();
//			} catch (FileNotFoundException e) {
//				log.error(e.getMessage());
//			} catch (IOException e) {
//				log.error(e.getMessage());
//			}
			printhtml=getPrintHtmlCode(modelXieyis,masterplateIds,topcompanyname,yytName);
//			try {
//				FileOutputStream os=new FileOutputStream("d:/test/print.html");
//				os.write(printhtml.getBytes());
//				os.close();
//			} catch (FileNotFoundException e) {
//				log.error(e.getMessage());
//			} catch (IOException e) {
//				log.error(e.getMessage());
//			}
		}		
		logpo.setResult("0");
		logpo.setObjname("成功");
		logpo.setDeptno(user.getDept_no());
		hotellogServ.addClientOperLogNew(logpo);
		
		//返回客户端版本号，用户真实姓名，部门名称
		//return versionStr+";Real="+user.getUser_name()+";Dept="+dept_name+";";
		filelist=filelist+imageCssList;
		log.info(filelist);
		if(!version.equals(versionStr)){
			return versionStr+";Real="+user.getUser_name()+";Dept="+dept_name+";FILELIST="+filelist+";EDITDATABEGIN:"+edithtml+":EDITDATAEND;PRINTDATABEGIN:"+printhtml+":PRINTDATAEND;";
		}else{
			return versionStr+";Real="+user.getUser_name()+";Dept="+dept_name+";FILELIST="+filelist+";";
		}
		}
	//获取模版协议版本号，用模版协议对应关系拼接
	public static String getModelXieVersion(List<ModelXieyiVo> modelXieyis,String filelist){
		String version="0";		
		StringBuffer sb=new StringBuffer("");
		for(ModelXieyiVo modelXieyi:modelXieyis){
			sb.append(modelXieyi.getModel_id()+","+modelXieyi.getXieyi_id()+";");
		}
		sb.append(filelist);
		try {
			MessageDigest digest=MessageDigest.getInstance("sha1");
			byte[] sha1=digest.digest(sb.toString().getBytes("gbk"));
			version=new BASE64Encoder().encode(sha1);
			version=version.replaceAll("\r\n", "");
			version=version.replaceAll("\n", "");
			version=version.replaceAll("\r", "");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		
		return version;
	}
	
	public static String getVersionNo(String  masterplateIds){
		String modelfolder="modelfolder";
		String versionStr = "";
		VersionServiceImpl versionService = (VersionServiceImpl) getBean("versionService");
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		String[] sss = masterplateIds.split(",");
		ModelFileVo  modelFile = null;
		for(String masterplateid : sss){
			VersionPO vp = versionService.getVersion(masterplateid);
			try {
				modelFile =  modelFileService.getModelFile(Integer.parseInt(masterplateid));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			String versionno = "0";
			if(vp!=null){
				versionno = vp.getCversionno();
			}
			versionStr += "<"+versionno + "||" + modelFile.getModel_name() + ".aip/>";
		}
		return versionStr;
	}
	/**
	 * 重写 getVersionNo 方法
	 * add by liuph
	 * 20171220
	 * @param masterplateIds 模板ID
	 * @return
	 */
	public static String getVersionNoRW(String masterplateIds){
		String versionStr = "";
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		try {
			List<ModelFile> mfs = modelFileService.getModelFileRW(masterplateIds);
			for(ModelFile mf:mfs){
				versionStr += "<"+mf.getModel_version() + "||" + mf.getModel_name() + ".aip/>";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionStr;
	}
	public static String getImageCSS(){
		String modelfolder="images";
		String versionStr = "";
		String bpath = "";
		bpath = Constants.basePath;
		String is_type=Constants.getProperty("is_type");
		if(is_type.equals("1")){//从配置文件读取路径
			bpath=Constants.getProperty("save_path");
		}
		String dir=bpath+"/images/clientImages/";
		File file=new File(dir);
		File[] files=null;
		if(file.isDirectory()){
			files=file.listFiles();
		}
		if(files==null||files.length==0){
			return "";
		}
		String versionno=DJPropertyUtil.getPropertyValue("cssVersion", "0");
		for(File f : files){
			versionStr += "<"+versionno + "||" +modelfolder+"/"+ f.getName() + "/>";
		}
		return versionStr;
	}
	public static byte[] downFile(String userName,String model_name){
		byte[] bytes=null;
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		try {
			ModelFileVo file=modelFileService.getModelFileByName(userName,model_name);
			if(file!=null){
				String file_base64=file.getContent_data();
				bytes=new BASE64Decoder().decodeBuffer(file_base64);
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return bytes;
	}
	
	public static String getEditHtmlCode(List<ModelXieyiVo> modelXieyis,String masterplateIds,String topcompanyname,String yytName) {
		List<ModelFileVo> masterplates=new ArrayList<ModelFileVo>();
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		String[] modelids=masterplateIds.split(",");
		for(String id:modelids){
			ModelFileVo file;
			try {
				file = modelFileService.getModelFile(Integer.valueOf(id));
				masterplates.add(file);
			} catch (NumberFormatException e) {
				log.error(e.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
			}	
		}
		String htmlCode = EditHtmlCode.getAllCode(masterplates,modelXieyis,topcompanyname,yytName);
		return htmlCode;
	}
	
	
	public static String getEditHtmlCode(String userid) {
		UserService userServ = (UserService) getBean("IUserService");
		SysUser user = null;
		try {
			user = userServ.showSysUserByUser_id(userid);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找用户出错！";
		}
		if(user==null){
			return "error:无此用户！";
		}
		//查找用户角色
		String role_nos = null;
		try {
			role_nos = userServ.serSysUserRole(userid);
		} catch (GeneralException e) {
			log.error(e.getMessage());
			return "error:查找用户角色出错！";
		}
		if(role_nos==null){
			return "error:用户角色不存在！";
		}
		//查找用户所拥有的角色的编辑模板
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		List<ModelFileVo> mfList;
		try {
			mfList = modelFileService.getEditModelFileByRoleIds(role_nos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找模板出错！";
		}
		//String htmlCode = EditHtmlCode.getAllCode(mfList,modelXieyis);
		return "";
	}
	public static String getPrintHtmlCode(List<ModelXieyiVo> modelXieyis,String masterplateIds,String topcompanyname,String yytName) {
		List<ModelFileVo> masterplates=new ArrayList<ModelFileVo>();
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		String[] modelids=masterplateIds.split(",");
		for(String id:modelids){
			ModelFileVo file;
			try {
				file = modelFileService.getModelFile(Integer.valueOf(id));
				masterplates.add(file);
			} catch (NumberFormatException e) {
				log.error(e.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
			}	
		}
		MasterplatejudgeServiceImpl judgeService = (MasterplatejudgeServiceImpl) getBean("masterplatejudgeService");
		List<Masterplatejudgep> judgeList = null;
		try {
			judgeList = judgeService.getJudgesByMasterplateList(masterplates);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找模板判定条件出错！";
		}
		String htmlCode = PrintHtmlCode.getAllCode(masterplates,judgeList, modelXieyis, topcompanyname,yytName);
		return htmlCode;
	}
	public static String getPrintHtmlCode(String userid) {
		UserService userServ = (UserService) getBean("IUserService");
		SysUser user = null;
		try {
			user = userServ.showSysUserByUser_id(userid);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找用户出错！";
		}
		if(user==null){
			return "error:无此用户！";
		}
		//查找用户角色
		String role_nos = null;
		try {
			role_nos = userServ.serSysUserRole(userid);
		} catch (GeneralException e) {
			log.error(e.getMessage());
			return "error:查找用户角色出错！";
		}
		if(role_nos==null){
			return "error:用户角色不存在！";
		}
		//查找用户所拥有的角色的编辑模板
		ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
		List<ModelFileVo> mfList;
		try {
			mfList = modelFileService.getPrintModelFileByRoleIds(role_nos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找模板出错！";
		}
		MasterplatejudgeServiceImpl judgeService = (MasterplatejudgeServiceImpl) getBean("masterplatejudgeService");
		List<Masterplatejudgep> judgeList = null;
		try {
			judgeList = judgeService.getJudgesByMasterplateList(mfList);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找模板判定条件出错！";
		}
		String topcompanyname = "";
		String yytname="";
		String userdeptno = user.getDept_no();
		//获取分公司部门名称
		if(userdeptno.length()>7){
			SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
			SysDepartment dept = null;
			SysDepartment dept2 = null;//营业厅部门
			try {
				dept = deptService.showDeptByNo(userdeptno.substring(0, 8));//截取部门号前8位为分公司的部门编号
				dept2 = deptService.showDeptByNo(userdeptno);
			} catch (GeneralException e) {
				log.error(e.getMessage());
				return "error:查找部门出错！";
			}
		
			if(dept==null){
			}else{
				topcompanyname = dept.getDept_name();
			}
			if(dept2==null){
			}else{
				yytname = dept2.getDept_name();
			}
		}
		String htmlCode = PrintHtmlCode.getAllCode(mfList,judgeList,null,topcompanyname,yytname);
		return htmlCode;
	}
	
	
	public static String addRecord(String user_id,String filename,String pdfinfo,String ip,String fileData) throws Exception{
		UserService userServ = (UserService) getBean("IUserService");
		SysUser user = null;
		try {
			user = userServ.showSysUserByUser_id(user_id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找用户出错！";
		}
		if(user==null){
			return "error:无此用户！";
		}else if(user.getDept_no()==null||user.getDept_no().equals("")){
			return "error:用户无部门信息！";
		}
		
		RecordPO record = new RecordPO();
		RecordContentPO rcontect = new RecordContentPO();
		List<RecordAffiliatePO> recordAffiliatedList = new ArrayList<RecordAffiliatePO>();
		record.setCfilefilename(filename);
		record.setCip(ip);
		record.setCreateuserid(user_id);
		record.setUploaduserid(user_id);
		record.setDeptno(user.getDept_no());
		Timestamp create_time = new Timestamp(new Date().getTime());
		record.setCuploadtime(create_time);
		record.setJihetime(create_time);//mysql时间类型值不能为空
		record.setCdata(fileData);//文件保存路径
		record.setCstatus("1");//状态设为正常
		record.setCheckstatus("1");//稽核状态设置为待一级稽核
		RecordAndAffiliated raa = new RecordAndAffiliated();
		raa.setRecord(record);
		raa.setRcontent(rcontect);
		raa.setRecordAffiliatedList(recordAffiliatedList);
		
		HotelClientoperLogServiceImpl hotellogServ = (HotelClientoperLogServiceImpl) getBean("clientOperLogService");
		HotelTClientOperLogPO logpo = new HotelTClientOperLogPO();
		logpo.setOperuserid(user_id);
		logpo.setCip(ip);
		logpo.setCopertime(create_time);
		logpo.setOpertype("2");
		logpo.setObjname(filename);
		logpo.setDeptno(user.getDept_no());
		
		try{
			raa = changeRecordByInfo(pdfinfo,raa);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "";
		}
		log.info("转换信息完成");
		RecordServiceImpl recordService = (RecordServiceImpl) getBean("recordService");
		String recordId;
		try{
			log.info("开始插入单据");
			ModelFileServiceImpl modelFileService = (ModelFileServiceImpl) getBean("modelFileService");
			RecordPO rrrpo = raa.getRecord();
			if(rrrpo.getCcreatetime()==null||rrrpo.getCcreatetime().equals("")){
				rrrpo.setCcreatetime(create_time);
			}
			if(rrrpo.getMtplid()!=null&&!rrrpo.getMtplid().equals("0")){
				if(modelFileService.isFlowModeFile(rrrpo.getMtplid())){
					rrrpo.setHasdone("0");//设置为未处理
				}
			}
			recordId = recordService.addRecordNew(rrrpo);
			
			log.info("插入单据成功");
		}catch (Exception e) {
			log.info("插入单据失败：");
			log.error(e.getMessage());
			logpo.setResult("1");
			hotellogServ.addClientOperLogNew(logpo);
			return "";
		}
		for(RecordAffiliatePO recordAff : raa.getRecordAffiliatedList()){
			recordAff.setRecordid(recordId);
			try{
				log.info("开始插入单据属性");
				recordService.addRecordAffiliatePoNew(recordAff);
				log.info("插入单据属性成功");
			}catch (Exception e) {
				log.info("插入单据属性失败：");
				log.error(e.getMessage());
				logpo.setResult("1");
				hotellogServ.addClientOperLogNew(logpo);
				return "";
			}
		}
		log.info("开始插入单据内容表");
		rcontect.setRid(recordId);
		int iRet =recordService.addRecordContent(rcontect);
		if(iRet == 1){
			log.info("单据内容插入成功");
		}else{
			log.info("单据内容插入失败");
		}
		logpo.setResult("0");
		hotellogServ.addClientOperLogNew(logpo);
		
		// 如果是离店单据就要合并房间单据
		String checkOutModelId = Constants.getProperty("check_out_model_id");
		if (checkOutModelId.equals(raa.getRecord().getMtplid())) {
			RecordRoomMergeServiceImpl recordRoomMergeService = (RecordRoomMergeServiceImpl)getBean("recordRoomMergeService");
			recordRoomMergeService.handleRoomCheckOut(raa.getRecord().getCid());
		}
		
		return recordId.toString();
	}
	
	public static String addScannerRecord(String user_id,String filename,String ip,String fileData,String content) throws Exception{
		UserService userServ = (UserService) getBean("IUserService");
		ScannerRecordServiceImpl scannerRecordService = (ScannerRecordServiceImpl) getBean("scannerRecordService");
		SysUser user = null;
		String recordId = "";
		try {
			user = userServ.showSysUserByUser_id(user_id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "error:查找用户出错！";
		}
		if(user==null){
			return "error:无此用户！";
		}else if(user.getDept_no()==null||user.getDept_no().equals("")){
			return "error:用户无部门信息！";
		}
		
		ScannerRecordPO scannerRecord = new ScannerRecordPO();
		scannerRecord.setCreateuserid(user_id);
		scannerRecord.setIp(ip);
		scannerRecord.setDeptno(user.getDept_no());
		Timestamp create_time = new Timestamp(new Date().getTime());
		scannerRecord.setFilename(filename);
		scannerRecord.setCreatetime(create_time);
		scannerRecord.setFilepath(fileData);
		scannerRecord.setContext(content);//ocr识别内容
		
		HotelClientoperLogServiceImpl hotellogServ = (HotelClientoperLogServiceImpl) getBean("clientOperLogService");
		HotelTClientOperLogPO logpo = new HotelTClientOperLogPO();
		logpo.setOperuserid(user_id);
		logpo.setCip(ip);
		logpo.setCopertime(create_time);
		logpo.setOpertype("2");
		logpo.setObjname(filename);
		logpo.setDeptno(user.getDept_no());
		recordId = scannerRecordService.addScannerRecord(scannerRecord);
		
		logpo.setResult("0");
		hotellogServ.addClientOperLogNew(logpo);
		return recordId.toString();
	}
	
	private static RecordAndAffiliated changeRecordByInfo(String pdfinfo,RecordAndAffiliated raa){
		RecordPO record = raa.getRecord();
		RecordContentPO rcontent = raa.getRcontent();
		List<RecordAffiliatePO> recordAffiliatedList = raa
				.getRecordAffiliatedList();
		if (pdfinfo != null && !pdfinfo.equals("")) {
			pdfinfo = replaceBlank(pdfinfo);
			if (pdfinfo != null && !pdfinfo.equals("")) {
				String[] sss = pdfinfo.split("</>");
				for (String ss : sss) {
					if (ss != null && !ss.equals("")) {
						String[] s = ss.split(">");
						if (s[0].substring(1, s[0].length()).equals("DJ_IN_CRETIME")) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							if (s[1] == null || s[1].equals("")) {
								Timestamp create_time = new Timestamp(new Date().getTime());
								record.setCcreatetime(create_time);
							} else {
								try {
									record.setCcreatetime(new Timestamp(sdf.parse(s[1]).getTime()));
								} catch (ParseException e) {
									log.error(e.getMessage());
								}
							}
						} else if (s[0].substring(1, s[0].length()).equals("DJ_TEMP_ID")) {
							try {
								if (s[1] == null || s[1].equals("")) {
									record.setMtplid("0");
								} else {
									record.setMtplid(s[1]);
								}
							} catch (Exception e) {
								log.error(e.getMessage());
								record.setMtplid("0");
							}
						}else if(s[0].substring(1, s[0].length()).equals("DJ_CONTENT")){
							if (s.length < 2) {
								rcontent.setContent("");
							} else {
								rcontent.setContent(s[1]);
							}
						}else {
							RecordAffiliatePO recordAff = new RecordAffiliatePO();
							log.info(s[0].substring(1, s[0].length()));
							recordAff.setCname(s[0].substring(1, s[0].length()));
							if(s.length<2){
								recordAff.setCvalue(" ");
							}else{
								recordAff.setCvalue(s[1]);
							}							
							recordAffiliatedList.add(recordAff);
						}
					}
				}
			}
		}
		raa.setRecord(record);
		raa.setRecordAffiliatedList(recordAffiliatedList);
		return raa;
	}
	
	//去除字符串前后的空格
	public static String replaceBlank(String str){
		  Pattern pt=Pattern.compile("^\\s*|\\s*$");
		  Matcher mt=pt.matcher(str);
		  str=mt.replaceAll("");
		  return str;
		 }

	
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}

	//返回带有年月日的路径，不是全部路径
	public static String saveHotelFile(String filename,byte[] filebyte){
		String filePath = "";
		try {
			filePath = HotelFileUtil.saveHotelFile(filename, filebyte);
		} catch (IOException e) {
			log.error(e.getMessage());
//			log.error(e.getMessage());
		}
		return filePath;
	}
	
	//返回带有年月日的路径，不是全部路径
	public static String saveScannerFile(String filename,byte[] filebyte){
		String filePath = "";
		try {
			filePath = HotelFileUtil.saveScannerFile(filename, filebyte);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return filePath;
	}
	
	public static int checkRecordNo(String fName){
		
		RecordDao record_dao = (RecordDao) getBean("recordDao");
		return record_dao.isRecordExist(fName);
	}
	
	public static String getDeptAdsVersion(String UID){
		String adHtmlCode = null;
		String version="";
		try {
			version = "";
			IUserService user_dao = (IUserService) getBean("IUserService");
			IHotelAdvertService advert_dao = (IHotelAdvertService) getBean("hotelAdverService");
			List<HotelAdvertPO> adverts = new ArrayList<HotelAdvertPO>();
			SysUser user = user_dao.showSysUserByUser_id(UID);
			String dept_no = user.getDept_no();
			adverts = advert_dao.getDeptAdverts(dept_no);
			adHtmlCode = AdvertCan.getAllAdHtmlCode(adverts);
			for (HotelAdvertPO hotelAdvertPO : adverts) {
				String filearray[] = hotelAdvertPO.getAd_filename().split(",");
				for(int i=0;i<filearray.length;i++){
					version += "<" + hotelAdvertPO.getAd_version() + "||"
							+ filearray[i] + "/>";
				}
			}
			version += ";PLAYHTMLBEGIN:" +adHtmlCode+":PLAYHTMLEND;";
		} catch (Exception e) {
			log.error(e.getMessage());
//			log.error(e.getMessage());
		}
		return version;
	}
	
	public static String getDeptAdsVersionByBank(String bankId){
		String adHtmlCode = null;
		String version="";
		int count=0;	
		Timestamp now_time = new Timestamp(System.currentTimeMillis());
		try {
			version = "";
			IHotelAdvertService advert_dao = (IHotelAdvertService) getBean("hotelAdverService");
			List<HotelAdvertPO> adverts = new ArrayList<HotelAdvertPO>();
			adverts = advert_dao.getBankDeptAdverts(bankId);
			for (HotelAdvertPO hotelAdvertPO : adverts) {//广告未过期
				if(now_time.before(hotelAdvertPO.getAd_starttime())||now_time.after(hotelAdvertPO.getAd_endtime())){//不在广告有效期内
					continue;
				}
				count++;
				String filearray[] = hotelAdvertPO.getAd_filename().split(",");
				for(int i=0;i<filearray.length;i++){
					version += "<" + hotelAdvertPO.getAd_version() + "||"
							+ filearray[i] + "/>";
				}
			}		
			if(count==0){//广告已过期(未设置滚动时间，后台处理会提示空指针异常—AdvertCan.java:49)
				adverts =advert_dao.getDefaultAdvert();
				for (HotelAdvertPO hotelAdvertPO : adverts) {
					String filearrays[] = hotelAdvertPO.getAd_filename().split(",");
					for(int i=0;i<filearrays.length;i++){
						version += "<" + hotelAdvertPO.getAd_version() + "||"
								+ filearrays[i] + "/>";
					}
				}
			}
			adHtmlCode = AdvertCan.getAllAdHtmlCode(adverts);
			version += ";PLAYHTMLBEGIN:" +adHtmlCode+":PLAYHTMLEND;";
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		//log.info(version);
		return version;
	}
	
	public static String ocrPdfImg(byte pdfByte[],String savePath){
		SrvSealUtil srv_seal = srv_seal();
		String userType=Constants.getProperty("userType");
		String userAccess=Constants.getProperty("userAccess");
		String userPwd=Constants.getProperty("userPwd");
		String ctrlPath=Constants.getProperty("ocxPath");
		int r = srv_seal.setCtrlPath(ctrlPath);
		log.info("load ocx:"+r);
		int nObjID = srv_seal.openData(pdfByte);
		srv_seal.login(nObjID, Integer.parseInt(userType), userAccess, userPwd);
		Long startTime = System.currentTimeMillis();
		log.info("startTime:"+startTime);
		int ret =  srv_seal.setValue(nObjID, "START_OCR", "");
		log.info("endTime:"+(System.currentTimeMillis()-startTime));
		log.info("orc:"+ret);
		String pdfContent = srv_seal.getValue(nObjID,"DOC_CONTENT_GET_DIRECTLY");
		pdfContent = pdfContent.replaceAll("[\\t\\n\\r]", " ");//替换回车换行
		if(pdfContent == null || pdfContent.equals("")){
			pdfContent = "no message";
		}
		log.info(pdfContent);
		int s = srv_seal.saveFile(nObjID, savePath, "pdf", 0);
		if (s == 0) {
			return "失败,saveFIle保存失败，返回值是0";
		}
		return pdfContent;
	}
	
	private static SrvSealUtil srv_seal() {
		SrvSealUtil srv_seal = (SrvSealUtil) getBean("srvSeal");//
		if (srv_seal == null) {
			srv_seal = new SrvSealUtil();
		}
		return srv_seal;
	}
	
	public static AdvertImagePO getAdvertImage(String name) throws Exception{
		HotelAdvertServiceImpl advert_srv = (HotelAdvertServiceImpl) getBean("hotelAdverService");
		return advert_srv.getAdvertImage(name);
	}
	
	public static void main(String[] args) {
		File file = new File("G://prn1.pdf");
		String savePath = "G://prn2.pdf";
		byte[] pdfData = new byte[(int) file.length()];
		try {
			InputStream in =new FileInputStream(file);
			in.read(pdfData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//log.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//log.error(e.getMessage());
		}
		
		ocrPdfImg(pdfData,savePath);
		
	}
	
}
