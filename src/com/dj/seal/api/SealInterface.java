package com.dj.seal.api;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.cert.util.CertType;
import com.dj.seal.doc.service.api.IDocService;
import com.dj.seal.docPrint.service.api.IDocPrintService;
import com.dj.seal.highcharts.form.ChartServiceForm;
import com.dj.seal.highcharts.service.aip.ChartService;
import com.dj.seal.log.service.impl.FeedLogSysServiceImpl;
import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.sealLog.service.api.ISealLogService;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.ChartReport;
import com.dj.seal.structure.dao.po.DocPrintRoleUser;
import com.dj.seal.structure.dao.po.DocmentBody;
import com.dj.seal.structure.dao.po.EvaluationPo;
import com.dj.seal.structure.dao.po.FeedLogSys;
import com.dj.seal.structure.dao.po.ServerSealLog;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.httpClient.IClient;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.sign.Base64;
import com.dj.sign.CertUtil;

/**
 * 完成客户端访问jsp时的功能
 * @author wj
 *
 */
public class SealInterface {
	
	static Logger logger = LogManager.getLogger(SealInterface.class.getName());
	
	private static Object getBean(String name){
		Object obj=MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}
	
	/**
	 * 获取用户
	 * @param key_sn 证书序列号
	 * @param key_dn 证书DN
	 * @param user_name 用户登录系统的用户名
	 * @return
	 */
	public static SysUser getUser(String key_sn,String key_dn,String user_name){
		IUserService userService=(IUserService)getBean("IUserService");
		SysUser user=null;
		if(key_dn!=null&&!key_dn.equals("")){
			user=userService.getUserBy_keyDN(key_dn);
		}else if(key_sn!=null&&!key_sn.equals("")){
			user=userService.getUserBy_keySN(key_sn);			
		}else if(user_name!=null&&!user_name.equals("")){
			user=userService.showSysUserByUser_id(user_name);
		}
		return user;
	}
	/**
	 * 获取证书
	 * @param key_sn 证书序列号
	 * @return
	 */
	public static Cert getCert(String key_sn){
		Cert cert=null;
		CertSrv cert_srv=(CertSrv)getBean("CertService");
		try {
			cert=cert_srv.getObjByName(key_sn);
		} catch (Exception e) {
//			logger.error(e.getMessage());
			logger.error(e.getMessage());
			return null;
		}
		return cert;
	}
	/**
	 * 获取keystore
	 * 
	 * @param pfx_path
	 *            证书路径（pfx格式）
	 * @param pfx_psw
	 *            证书密码（不可为空）
	 * @return 证书库keystore
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(byte[] pfx,String pfx_psw){
		KeyStore keyStore=null;
		//得到KeyStore实例 
        try {
			keyStore = KeyStore.getInstance("PKCS12");
			ByteArrayInputStream is = new ByteArrayInputStream(pfx); 
	        //从指定的输入流中加载此 KeyStore。 
	        keyStore.load(is, pfx_psw.toCharArray()); 
	        is.close(); 
		} catch (KeyStoreException e) {
			logger.error(e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (CertificateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} 
		return keyStore;
	}
	/**
	 * 签名
	 * @param 
	 */
	public static String signData(String key_sn,String psw,String oridata,String sign_mode){
		IUserService userService=(IUserService)getBean("IUserService");
		SysUser user=null;
		user=userService.getUserBy_keySN(key_sn);	
		if(user==null){
			return "X-此用户不存在";
		}
		if(!user.getUser_psd().equals(psw)){
			return "X-用户密码不正确";
		}
		if(!user.getUseing_key().equals("2")){
			return "X-用户不是pfx证书用户";
		}
		CertUtil util=new CertUtil();
		Cert cert=getCert(key_sn);
		String pfxBase64=null;
		if(cert.getCert_src().equals(CertType.server)){
			pfxBase64=cert.getFile_content();
		}else if(cert.getCert_src().equals(CertType.clientPFX)){
			pfxBase64=cert.getPfx_content();
		}else{
			return "X-证书不含私钥部分";
		}
		byte[] pfx=Base64.decode(pfxBase64);
		KeyStore keyStore=getKeyStore(pfx, cert.getCert_psd());
		if(keyStore==null){
			return "X-加载证书出错";
		}
		String res="";
		switch(sign_mode.charAt(0)){
		case '1':
			try {
					res=CertUtil.signByBase64Data(oridata, keyStore, cert.getCert_psd());
				} catch (Exception e) {
					logger.error(e.getMessage());
					return "X-p1签名出错";
				}
			break;
		case '2':
			try {
					res=CertUtil.signP7ByBase64Data(oridata, keyStore, cert.getCert_psd());
				} catch (Exception e) {
					logger.error(e.getMessage());
					return "X-attach签名出错";
				}
			break;
		case '3':
			try {
				res=CertUtil.signP7DettachByBase64Data(oridata, keyStore, cert.getCert_psd());
			} catch (Exception e) {
				logger.error(e.getMessage());
				return "X-detach签名出错";
			}
			break;
		}
		return res;
	}
	/**
	 * 签名
	 * @param 
	 */
	public static String signDatabyServerPFX(String key_sn,String remote_sn,String s_id,String oridata,String sign_mode){
		IUserService userService=(IUserService)getBean("IUserService");
		SysUser user=null;
		user=userService.getUserBy_keySN(key_sn);	
		if(user==null){
			return "X-此用户不存在";
		}
		CertUtil util=new CertUtil();
		CertSrv certS=(CertSrv)getBean("CertService");
		Cert cert=null;
		try {
			cert = certS.getObjByNo(remote_sn);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(e1.getMessage());
			return "X-获取证书出错";
		}
		ISealBodyService sealService=(ISealBodyService)getBean("ISealBodyService");
		int sid=Integer.parseInt(s_id);
		SealBody seal=null;
		try {
			seal=sealService.getSealBodys(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "X-获取印章出错";
		}
		if(seal!=null&&!seal.getKey_sn().equals(remote_sn)){
			return "X-此印章没有该证书权限";
		}
		String pfxBase64=null;
		if(cert.getCert_src().equals(CertType.server)){
			pfxBase64=cert.getFile_content();
		}else if(cert.getCert_src().equals(CertType.clientPFX)){
			pfxBase64=cert.getPfx_content();
		}else{
			return "X-证书不含私钥部分";
		}
		byte[] pfx=Base64.decode(pfxBase64);
		DesUtils des=null;
		try {
			des = new DesUtils();
		} catch (Exception e2) {
			e2.printStackTrace();
			logger.error(e2.getMessage());
			return "X-证书密码解密失败";
		}
		String pfx_psw="";
		try {
			pfx_psw = des.decrypt(cert.getCert_psd());
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
			return "X-证书密码解密失败";
		}
		KeyStore keyStore=getKeyStore(pfx,pfx_psw);
		if(keyStore==null){
			return "X-加载证书出错";
		}
		String res="";
		switch(sign_mode.charAt(0)){
		case '1':
			try {
					res=CertUtil.signByBase64Data(oridata, keyStore, pfx_psw);
				} catch (Exception e) {
					logger.error(e.getMessage());
					return "X-p1签名出错";
				}
			break;
		case '2':
			try {
					res=CertUtil.signP7ByBase64Data(oridata, keyStore, pfx_psw);
				} catch (Exception e) {
					logger.error(e.getMessage());
					return "X-attach签名出错";
				}
			break;
		case '3':
			try {
				res=CertUtil.signP7DettachByBase64Data(oridata, keyStore, pfx_psw);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return "X-detach签名出错";
			}
			break;
		}
		return res;
	}
	/**
	 * 根据印章id得到印章数据
	 * @param s_id 印章id
	 * @return  印章数据
	 */
	public static String sealDataById(String s_id){
		ISealBodyService sealService=(ISealBodyService)getBean("ISealBodyService");
		int sid=Integer.parseInt(s_id);
		SealBody seal=null;
		try {
			seal=sealService.getSealBodys(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "X-获取印章出错";
		}
		if(seal!=null&&seal.getSeal_types().equals("1")){//如果是公章，判断是否绑定证书
			String cert_no=seal.getKey_sn();
			if(cert_no==null||cert_no.equals("")){
				//return seal.getSeal_data();
				return "DataBegin::0::"+seal.getSeal_data()+"::DataEnd";
			}
			CertSrv certS=(CertSrv)getBean("CertService");
			Cert cert;
			try {
				cert = certS.getObjByNo(cert_no);
				if(cert==null){
					return "X-sealid为"+sid+"的印章尚未指定机构证书";
				}
				String pfxBase64=null;
				if(cert.getCert_src().equals(CertType.server)){
					pfxBase64=cert.getFile_content();
				}else{
					return "X-证书不含私钥部分";
				}
				byte[] pfx=Base64.decode(pfxBase64);
				DesUtils des=new DesUtils();
				String pfx_psw=des.decrypt(cert.getCert_psd());
				KeyStore keyStore=getKeyStore(pfx,pfx_psw);
				if(keyStore==null){
					return "X-加载证书出错";
				}
				String alias = keyStore.aliases().nextElement();// 证书别名
				X509Certificate cert2 = (X509Certificate) keyStore.getCertificate(alias);// 公钥
				byte[] certB=cert2.getEncoded();
//				FileOutputStream os=new FileOutputStream("c://test.cer");
//				os.write(certB);
//				os.close();
				String certBase64=Base64.encodeToString(certB);
				certBase64=certBase64.replace("\r\n", "");
				certBase64=certBase64.replace("\r", "");
				certBase64=certBase64.replace("\r", "");
				return "DataBegin::0::"+seal.getSeal_data()+"::DataEnd::BINDPFX-"+certBase64+"::"+cert_no;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				return "X-获取证书出错";
			}
		}
		if(seal!=null&&!seal.getSeal_types().equals("1")){
			return "DataBegin::0::"+seal.getSeal_data()+"::DataEnd";
		}
		return "X-未找到印章";
	}	
	/**
	 * 根据印章id返回印章
	 * @param s_id 印章id
	 * @return  印章数据
	 */
	public static SealBody sealBodyById(String s_id){
		ISealBodyService sealService=(ISealBodyService)getBean("ISealBodyService");
		int sid=Integer.parseInt(s_id);
		SealBody seal=null;
		try {
			seal=sealService.getSealBodys(sid);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		if(seal!=null){
			return seal;
		}
		return null;
	}
	
/**
 * 根据用户的证书dn获取印章列表
 * @param key_dn
 * @return  返回印章列表
 */
	public static String sealListbyDN(String SEAL_TYPE,String key_dn){
		IUserService userService=(IUserService)getBean("IUserService");
		SysUser user=userService.getUserBy_keyDN(key_dn);
		if(user==null){
			return "X-此用户不存在";
		}
		return getSealListByUser(SEAL_TYPE,user);
	}
	/**
	 * 根据用户的证书sn获取印章列表
	 * @param key_sn
	 * @return  前台控件可解析的印章列表格式
	 */
	public static String sealListbySN(String SEAL_TYPE,String key_sn){
		IUserService userService=(IUserService)getBean("IUserService");
		SysUser user=userService.getUserBy_keySN(key_sn);
		if(user==null){
			return "X-此用户不存在";
		}
		return getSealListByUser(SEAL_TYPE,user);
	}
	
	/**
	 * 根据用户获取印章列表
	 * @param user  用户
	 * @return 前台控件可解析的印章列表格式
	 */
	private static String getSealListByUser(String key_word,SysUser user){
		//key_word参数现在作为印章类型使用  1为公章，2为个人章
		ISealBodyService sealService=(ISealBodyService)getBean("ISealBodyService");
		ISysRoleService roleService=(ISysRoleService)getBean("ISysRoleService");
		List<SealBody> seals=sealService.getSealListByUser_id(key_word,user.getUser_id());
		List<SealBody> sealsbyroles=null;
		try {
			List<SysRole> rols=roleService.showSysRolesByUser_id(user.getUser_id());
			if(rols!=null){
				sealsbyroles=sealService.getSealListByRoles(key_word,rols);
			}
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		if(seals==null&&sealsbyroles==null){
			return "X-此用户没有印章权限";
		}else if(seals!=null&&sealsbyroles!=null){
			seals=DaoUtil.bingJi(seals, sealsbyroles);
		}else if(seals==null&&sealsbyroles!=null){
			seals=sealsbyroles;
		}
		
		return getSealListStr(seals);
	}
	/**
	 * 格式化印章列表成前台控件可解析数据
	 * @param seals
	 * @return
	 */
	private  static String getSealListStr(List<SealBody> seals){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<seals.size();i++){
			SealBody seal=seals.get(i);
			sb.append(seal.getSeal_id());
			sb.append("\r\n");
			sb.append(seal.getSeal_name());
			sb.append("V*+");
		}
		return sb.toString();
	}
	
	/**
	 * 新增文档
	 * @param 参数来自GetDocId.jsp
	 * @param form
	 * @throws GeneralException
	 */
	public static String addDoc(String key_sn,String key_dn,String doc_id,String doc_type,String doc_name,String doc_title,
			String doc_content,String doc_keys,String mac_add,String doc_ip) {
		IUserService userService=(IUserService)getBean("IUserService");
		IDocService docService=(IDocService)getBean("DocService");
		ISysDeptService deptService=(ISysDeptService)getBean("ISysDeptService");
		SysUser user;
		if(key_dn!=null&&!key_dn.equals("")){
			user=userService.getUserBy_keyDN(key_dn);
			if(user==null){
				return "X-此用户不存在";
			}
		}else{
			user=userService.getUserBy_keySN(key_sn);
			if(user==null){
				return "X-此用户不存在";
			}
		}
		try {
			SysDepartment dept=deptService.showDeptByNo(user.getDept_no());
			user.setDept_name(dept.getDept_name());
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		if(docService.isExitDoc(doc_id)){
			return "";
		}else{
			DocmentBody doc=new DocmentBody();
			doc.setCreate_ip(doc_ip);
			doc.setCreate_time(new Timestamp(new Date().getTime()));
			doc.setDept_name(user.getDept_name());
			doc.setDept_no(user.getDept_no());
			doc.setDoc_content(doc_content);
			doc.setDoc_creator(user.getUser_id());
			doc.setDoc_keys(doc_keys);
			doc.setDoc_name(doc_name);
			doc.setDoc_no(doc_id);
			doc.setDoc_title(doc_title);
			doc.setDoc_type(doc_type);
			docService.addDoc(doc);
		}
		return "";
		
	}
	/**
	 * 获取打印份数
	 * @param key_sn 证书序列号
	 * @param key_dn 证书dn
	 * @param doc_no 文档编号
	 * @return
	 */
	public static String getPrintInfo(String key_sn,String key_dn,String doc_no){
		IUserService userService=(IUserService)getBean("IUserService");
		IDocPrintService docPrintService=(IDocPrintService)getBean("docPrint_service");
		SysUser user;
		if(key_dn!=null&&!key_dn.equals("")){
			user=userService.getUserBy_keyDN(key_dn);
			if(user==null){
				return "X-此用户不存在";
			}
		}else{
			user=userService.getUserBy_keySN(key_sn);
			if(user==null){
				return "X-此用户不存在";
			}
		}
		DocPrintRoleUser printInfo;
		printInfo=docPrintService.getDocPrintRoleUserByUserid(doc_no, user.getUser_id());
		if(printInfo==null||printInfo.getCurrnum()>=printInfo.getPrintnum()){//根据用户未找到打印设置,或超出份数
			ISysRoleService roleService=(ISysRoleService)getBean("ISysRoleService");
			List<SysRole> rols;
			try {
				rols = roleService.showSysRolesByUser_id(user.getUser_id());
				if(rols!=null){
					printInfo=docPrintService.getDocPrintRoleUserByRoleid(doc_no, rols);
				}
			} catch (GeneralException e) {
				logger.error(e.getMessage());
				return "X-服务器出错";
			}
		}
		if(printInfo==null){//根据用户和角色都未找到打印设置
			return "X-没有打印权限";
		}else{
			int left=printInfo.getPrintnum()-printInfo.getCurrnum();
			//return printInfo.getCurrnum()+"::"+printInfo.getPrintnum();
			return left+"";
		}
	}
	/**
	 * 更新打印份数
	 * @param key_sn 证书序列号
	 * @param key_dn 证书dn
	 * @param doc_no 文档编号
	 * @param num 打印数量
	 * @return
	 */
	public static void setPrintInfo(String key_sn,String key_dn,String doc_no,String num){
		IUserService userService=(IUserService)getBean("IUserService");
		IDocPrintService docPrintService=(IDocPrintService)getBean("docPrint_service");
		SysUser user;
		if(key_dn!=null&&!key_dn.equals("")){
			user=userService.getUserBy_keyDN(key_dn);
			if(user==null){
				return ;
			}
		}else{
			user=userService.getUserBy_keySN(key_sn);
			if(user==null){
				return ;
			}
		}
		DocPrintRoleUser printInfo;
		printInfo=docPrintService.getDocPrintRoleUserByUserid(doc_no, user.getUser_id());
		if(printInfo==null){//根据用户未找到打印设置
			
			ISysRoleService roleService=(ISysRoleService)getBean("ISysRoleService");
			List<SysRole> rols;
			try {
				rols = roleService.showSysRolesByUser_id(user.getUser_id());
				if(rols!=null){
					printInfo=docPrintService.getDocPrintRoleUserByRoleid(doc_no, rols);
				}
			} catch (GeneralException e) {
				logger.error(e.getMessage());
				return ;
			}
		}
		if(printInfo==null){//根据用户和角色都未找到打印设置
			return ;
		}else{
			int new_num= Integer.parseInt(num);
			printInfo.setCurrnum(printInfo.getCurrnum()+new_num);
			docPrintService.updateDocPrintRoleUser(printInfo);
		}
	}
	/**
	 * 记录盖章、打印日志
	 * @param log
	 */
	public static void addSealLog(String user_id,String caseSeqID,String ip,String seal_status,String file_name,String sealName,
			String sealType,String deptNo,String deptName){
		ISealLogService log_service=(ISealLogService)getBean("SealLogService");
		ServerSealLog log=new ServerSealLog();
		log.setUser_id(user_id);
		log.setCaseseqid(caseSeqID);
		log.setServer_id("1");//无纸化交易盖章
		log.setOpr_ip(ip);
		log.setOpr_time(new Timestamp(new Date().getTime()));
		log.setSeal_status(seal_status);
		log.setFile_name(file_name);
		log.setOpr_msg("");
		log.setSeal_name(sealName);
		log.setSeal_type(sealType);
		log.setDept_no(deptNo);
		log.setDept_name(deptName);
		log_service.addSealLog(log);
	}
	
	public static void addHuizhiLog(String user_id,String caseSeqID,String file_name,String status,String sealtype,String dept_no,String dept_name,String sealname){
		ISealLogService log_service=(ISealLogService)getBean("SealLogService");
		ServerSealLog log=new ServerSealLog();
		log.setUser_id(user_id);
		log.setCaseseqid(caseSeqID);
		log.setServer_id("2");//最高院盖章
		log.setOpr_ip("127.0.0.0");
		log.setOpr_time(new Timestamp(new Date().getTime()));
		log.setSeal_status(status);
		log.setFile_name(file_name);
		log.setOpr_msg("");
		log.setSeal_name(sealname);
		log.setSeal_type(sealtype);
		log.setDept_no(dept_no);
		log.setDept_name(dept_name);
		log_service.addSealLog(log);
	}
	
	public static void addFeedSeal(FeedLogSys flogsys){
		FeedLogSysServiceImpl flog_srv = (FeedLogSysServiceImpl) getBean("feedLogSysService");
		try {
			flog_srv.logAdd(flogsys);
		} catch (GeneralException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String getReportDataByForm(ChartServiceForm from) throws IOException{
		ChartService c_reportdao = (ChartService) getBean("ReportService");
		List<ChartReport> reports = c_reportdao.showRecordDataByDept(from);
//		JSONWriter writer = new JSONWriter(new FileWriter("/general/highcharts/index.json"));
//		  writer.startArray();
//		  for (int i = 0; i < reports.size(); ++i) {
//		        writer.writeValue(reports.get(i));
//		  }
//		  writer.endArray();
//		  writer.close();
		String jsonStr =  JSON.toJSONString(reports);
		return jsonStr;
	}
	
	public static String getXMLString(EvaluationPo evalu) {
		// A StringBuffer Object
		  StringBuffer sb = new StringBuffer();
		  String xmlHead = "";
		try {
			xmlHead = new String(IClient.XML_HEADER.getBytes(),"gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		  sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
//		   sb.append("<ROOT>").append("\r\n");
//		   sb.append("  <SRCTRANCODE>"+evalu.getSrctrancode()+"</SRCTRANCODE>").append("\r\n");
//		   sb.append("  <SRCSYSID>"+evalu.getSrcsysid()+"</SRCSYSID>").append("\r\n");
//		   sb.append("  <SRCTRNDT>"+evalu.getSrctrndt()+"</SRCTRNDT>").append("\r\n");
//		   sb.append("  <SRCSEQNO>"+evalu.getSrcesqno()+"</SRCSEQNO>").append("\r\n");
//		   sb.append("  <TRANBRCHNO>"+evalu.getTranbrchno()+"</TRANBRCHNO>").append("\r\n");
//		   sb.append("  <TRANTLR>"+evalu.getTrantlr()+"</TRANTLR>").append("\r\n");
//		   sb.append("  <TRANTRX>"+evalu.getTrantrx()+"</TRANTRX>").append("\r\n");
//		   sb.append("  <TRANNM>"+evalu.getTrannm()+"</TRANNM>").append("\r\n");
//		   sb.append("  <JDGDTTM>"+evalu.getJdgdttm()+"</JDGDTTM>").append("\r\n");
//		   sb.append("  <JDGRSLT>"+evalu.getJdgrslt()+"</JDGRSLT>").append("\r\n");
//		   sb.append("  <TRANCODE>"+evalu.getTrancode()+"</TRANCODE>").append("\r\n");
//		   sb.append("</ROOT>").append("\r\n");
		  
		   sb.append("<msg>");
		   sb.append("<comm_head>");
		   sb.append("<ver_no>1.0</ver_no>");
		   sb.append("<bank_id>0681</bank_id>");
		   sb.append("<snd_chnl_no>17</snd_chnl_no>");
		   sb.append("<rcv_chnl_no>17</rcv_chnl_no>");
		   sb.append("<ext_txn_no></ext_txn_no>");
		   sb.append("<chnl_dt>20121018</chnl_dt>");
		   sb.append("<host_dt></host_dt>");
		   sb.append("<chnl_tm>092050</chnl_tm>");
		   sb.append("<host_tm></host_tm>");
		   sb.append("<chnl_seq>00000002002</chnl_seq>");		   
		   sb.append("<host_seq>00000002301</host_seq>");
		   sb.append("<rsp_no></rsp_no>");
		   sb.append("<rsp_msg></rsp_msg>");
		   sb.append("</comm_head>");
		   sb.append("<main_data>");
		   sb.append("<PIAOID>"+evalu.getSrctrancode()+"</PIAOID>");
		   sb.append("<JIEGUO>"+evalu.getJdgrslt()+"</JIEGUO>");
		   sb.append("<FKYYIN>"+evalu.getReason()+"</FKYYIN>");
		   sb.append("</main_data>");
		   sb.append("</msg>");
		  
		   logger.info(sb.toString());
		   String headmes = "g02412345678901234568888#";
		   int strlength = 0;
		   strlength = sb.toString().trim().getBytes().length;
		   int headlength = 0;
		   headlength = xmlHead.getBytes().length;
		   String newString = String.format("%06d",strlength+headmes.length()+6);  //格式化数据,自动补零到8位数字
		   newString = newString+"g02412345678901234568888#";//评价服务
		   String retmes = newString+sb.toString();
		   logger.info(newString);
		   return retmes;
		 }
	
	
}


