package com.dj.websignServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import serv.MyOper;
import serv.MyXml;
import srvSeal.SrvSealUtil;
import xmlUtil.xml.XMLNote;

import com.dj.seal.api.SealInterface;
import com.dj.seal.appSystem.service.impl.AppSysServiceImpl;
import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.cert.util.CertType;
import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.encrypt.FileUtil;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.spring.MyApplicationContextUtil;

import decSeal.DecSealUtil;

public class WebSignSerivce {
	static Logger logger = LogManager.getLogger(WebSignSerivce.class.getName());
	private String errorMessge;
	private String messge;
	//获取spring里的bean
	private static Object getBean(String name){
		Object obj=MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	} 
	// 得到客户端ip
	private String getClientIp() {
		MessageContext mc = MessageContext.getCurrentMessageContext();
		if (mc == null) {
			return null;
		}
		HttpServletRequest request = (HttpServletRequest) mc
				.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//logger.info("ip:" + ip);
		return ip;
	}
	//根据xml字符串得到根节点
	private Element getRootElement(String xmlStr){
		SAXBuilder saxBuilder=new SAXBuilder();
		StringReader reader=new StringReader(xmlStr);
		try {
			Document doc=saxBuilder.build(reader);
			return doc.getRootElement();
		} catch (JDOMException e) {
			this.errorMessge="xml格式不合法,无法解析";
			logger.error(e.getMessage());
			return null;
		} catch (IOException e) {
			this.errorMessge="xml格式不合法,无法解析";
			logger.error(e.getMessage());
			return null;
		}
		
	}
	/**
	 * 功能：将xml转为xml字符串输出，
	 * 参数 xml文档部分
	 * 返回值 xml字符串
	 */
	private String getXMLString(Document doc){
		Format format = Format.getCompactFormat(); 
		format.setEncoding("utf-8");
		format.setIndent("  ");
		XMLOutputter outputter=new XMLOutputter(format);
		return outputter.outputString(doc);
	}
	//当发生错误时返回的xml字符串
	private String errorReturn(){
		Document doc=new Document();
		Element rootE=new Element("root");
		doc.setRootElement(rootE);
		Element infoE=new Element("info");
		rootE.addContent(infoE);
		Element hasErrorE=new Element("hasError");
		infoE.addContent(hasErrorE);
		Element errorMessageE=new Element("errorMessage");
		infoE.addContent(errorMessageE);
		hasErrorE.setText("1");
		errorMessageE.setText(this.errorMessge);
		return getXMLString(doc);
	}
	//构造获取印章列表的xml返回
	private String sealListReturnXml(List<SealBody> seals){
		Document doc=new Document();
		Element rootE=new Element("root");
		doc.setRootElement(rootE);
		Element infoE=new Element("info");
		rootE.addContent(infoE);
		Element hasErrorE=new Element("hasError");
		infoE.addContent(hasErrorE);
		Element errorMessageE=new Element("errorMessage");
		infoE.addContent(errorMessageE);
		if(seals!=null){
			hasErrorE.setText("0");
			errorMessageE.setText("");
			Element sealListE=new Element("sealList");
			infoE.addContent(sealListE);
			for(int i=0;i<seals.size();i++){
				Element sealE=new Element("seal");
				sealListE.addContent(sealE);
				Element sealIdE=new Element("sealId");
				sealIdE.setText(seals.get(i).getSeal_id()+"");
				sealE.addContent(sealIdE);
				Element sealNameE=new Element("sealName");
				sealNameE.setText(seals.get(i).getSeal_name());
				sealE.addContent(sealNameE);
			}
		}
		return getXMLString(doc);
	}
	//构造盖章接口的xml返回
	private String addSealReturnXml(String sealRes){
		Document doc=new Document();
		Element rootE=new Element("root");
		doc.setRootElement(rootE);
		Element infoE=new Element("info");
		rootE.addContent(infoE);
		Element hasErrorE=new Element("hasError");
		infoE.addContent(hasErrorE);
		Element errorMessageE=new Element("errorMessage");
		infoE.addContent(errorMessageE);
		if(sealRes!=null){
			hasErrorE.setText("0");
			errorMessageE.setText("");
			Element sealResE=new Element("sealRes");
			sealResE.setText(sealRes);
			infoE.addContent(sealResE);
		}
		return getXMLString(doc);
	}
	//根据证书得到List
	private List<SealBody> getSealList(String userFlag,String certInfo,String user_id,String user_psw){
		IUserService userService=(IUserService)getBean("IUserService");
		SysUser user=null;
		if(userFlag.equals("0")){
			user=userService.getUserBy_keyDN(certInfo);			
		}else if(userFlag.equals("1")){
			user=userService.getUserBy_keySN(certInfo);
		}else if(userFlag.equals("2")){
			if(user_id!=null&&!user_id.equals("")){
				user=userService.showSysUserByUser_id(user_id);
			}else{
				this.errorMessge="未指定用户名";
				return null;
			}
			if(user!=null){
				if(!user.getUser_psd().equals(user_psw)){
					this.errorMessge="用户密码不正确";
					return null;
				}
			}
		}
		if(user==null){
			this.errorMessge="用户不存在";
			return null;
		}
		ISealBodyService sealService=(ISealBodyService)getBean("ISealBodyService");
		ISysRoleService roleService=(ISysRoleService)getBean("ISysRoleService");
		List<SealBody> seals=sealService.getSealListByUser_id("",user.getUser_id());
		List<SealBody> sealsbyroles=null;
		try {
			List<SysRole> rols=roleService.showSysRolesByUser_id(user.getUser_id());
			if(rols!=null){
				sealsbyroles=sealService.getSealListByRoles(rols);
			}
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		if((seals==null||seals.size()==0)&&(sealsbyroles==null||sealsbyroles.size()==0)){
			this.errorMessge="用户没有印章权限";
			return null;
		}else if(seals!=null&&sealsbyroles!=null){
			seals=DaoUtil.bingJi(seals, sealsbyroles);
		}else if(seals==null&&sealsbyroles!=null){
			seals=sealsbyroles;
		}
		return seals;
	}
	//返回缺少节点错误时的xml
	private String notFoundNode(String nodeName){
		boolean hasError=true;
		this.errorMessge="缺少"+nodeName+"节点";
		return this.errorReturn();
	}
	//节点值是否不合法，返回false  合法，返回true  不合法
	private boolean nodeValueIllegal(String nodeName,String nodeValue){
		boolean hasError=false;
		if(nodeName.equals("userFlag")){
			if(!nodeValue.equals("1")&&!nodeValue.equals("0")&&!nodeValue.equals("2")){
				hasError=true;
				this.errorMessge=nodeName+"的值不符合要求";
			}
		}
		return hasError;
	}
	//获取证书
	private Cert getCert(String flag,String certInfo,String user_id,String user_psw){
		Cert cert=null;
		CertSrv srv=(CertSrv)getBean("CertService");
		if(flag.equals("0")){	//dn	
			try {
				cert=srv.getObjByDN(certInfo);
			} catch (Exception e) {
				logger.error(e.getMessage());
				this.errorMessge="获取用户证书出错";
				return null;
			}
		}else if(flag.equals("1")){//sn
			try {
				cert=srv.getObjByName(certInfo);
			} catch (Exception e) {
				logger.error(e.getMessage());
				this.errorMessge="获取用户证书出错";
				return null;
			}
		}else if(flag.equals("2")){
			IUserService userService=(IUserService)getBean("IUserService");
			SysUser user=null;
			if(user_id!=null&&!user_id.equals("")){
				user=userService.showSysUserByUser_id(user_id);
			}else{
				this.errorMessge="未指定用户名";
				return null;
			}
			if(user==null){
				this.errorMessge="用户不存在";
				return null;
			}
			if(user!=null){
				if(!user.getUser_psd().equals(user_psw)){
					this.errorMessge="用户密码不正确";
					return null;
				}
			}
			try {
				cert=srv.getObjByName(user.getKey_sn());
			} catch (Exception e) {
				logger.error(e.getMessage());
				this.errorMessge="获取用户证书出错";
				return null;
			};
		}
		return cert;
	}
	//判断是否是合法用户
	private boolean isLegalUser(String xmlStr){
		String ip=getClientIp();
		int p;
		XMLNote xml=null;
		try {
			xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		} catch (Exception e1) {
			e1.printStackTrace();
			this.errorMessge="XML格式错误";
			return false;
		}
		try {
			p = MyXml.isSysIP(xml, ip);
			if (p == 1) {
				this.errorMessge="ip:"+ip+"不在应用系统中";
				return false;
			}
			String u = MyXml.isSysAllow(xml);// 检查基础信息是否正确
			if (u != "信息完整") {
				this.errorMessge=u;
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.errorMessge="判断用户是否合法出错";
			return false;
		}// 客户端请求ip是否在应用系统内		
		return true;
	}
	/**
	 * webservice接口，获取印章列表
	 * @param xmlStr
	 * @return
	 */
	public String getSealList(String xmlStr){
		String userFlag="";
		String certInfo="";
		String user_id="";
		String user_psw="";
		Element root=getRootElement(xmlStr);//得到xml参数的跟节点
		boolean hasError=false;
		if(!isLegalUser(xmlStr)){//如果不是合法用户
			hasError=true;
			return this.errorReturn();
		}
		if(root==null){	
			hasError=true;
			return this.errorReturn();
		}
		Element userE=root.getChild("user");//得到user节点
		if(userE==null){
			return notFoundNode("user");
		}
		Element userFlagE=userE.getChild("userFlag");//得到userFlage节点
		if(userFlagE==null){
			return notFoundNode("userFlag");
		}
		userFlag=userFlagE.getText();
		if(nodeValueIllegal("userFlag",userFlag)){
			return this.errorReturn();
		}
		Element certInfoE=userE.getChild("certInfo");
		if(certInfoE==null){
			return notFoundNode("certInfo");
		}
		certInfo=certInfoE.getText();
		if(userFlag.equals("2")){
			Element userIDE=userE.getChild("userID");
			if(userIDE==null){
				return notFoundNode("userID");
			}
			user_id=userIDE.getText();
			Element userPSWE=userE.getChild("userPSW");
			if(userPSWE==null){
				return notFoundNode("userPSW");
			}
			user_psw=userPSWE.getText();
		}
		List<SealBody> seals=getSealList(userFlag, certInfo,user_id,user_psw);
		if(seals==null||seals.size()==0){
			//this.errorMessge="此用户没有任何印章权限";
			hasError=true;
			return this.errorReturn();
		}
		return sealListReturnXml(seals);
	}
	/**
	 * webservice接口，盖章接口
	 * @param xmlStr
	 * @return
	 */
	public String addSeal(String xmlStr){
		String userFlag;
		String certInfo;
		String sealId;
		String sealName;
		String seal_position;
		String seal_x;
		String seal_y;
		String oriData;
		String seal_data;
		String user_id="";
		String user_psw="";
		Element root=getRootElement(xmlStr);//得到xml参数的跟节点
		boolean hasError=false;
		if(!isLegalUser(xmlStr)){//如果不是合法用户
			hasError=true;
			return this.errorReturn();
		}
		if(root==null){	
			hasError=true;
			return this.errorReturn();
		}
		Element infoE=root.getChild("info");//得到info节点
		if(infoE==null){
			return notFoundNode("info");
		}
		Element userE=infoE.getChild("user");//得到user节点
		if(userE==null){
			return notFoundNode("user");
		}
		Element userFlagE=userE.getChild("userFlag");//得到userFlage节点
		if(userFlagE==null){
			return notFoundNode("userFlag");
		}
		userFlag=userFlagE.getText();
		if(nodeValueIllegal("userFlag",userFlag)){
			return this.errorReturn();
		}
		Element certInfoE=userE.getChild("certInfo");
		if(certInfoE==null){
			return notFoundNode("certInfo");
		}
		certInfo=certInfoE.getText();
		Element sealE=infoE.getChild("seal");//得到seal节点
		if(sealE==null){
			return notFoundNode("seal");
		}
		Element sealIdE=sealE.getChild("sealId");
		if(sealIdE==null){
			return notFoundNode("sealId");
		}
		sealId=sealIdE.getText();
		Element sealNameE=sealE.getChild("sealName");
		if(sealNameE==null){
			return notFoundNode("sealName");
		}
		sealName=sealNameE.getText();
		Element seal_positionE=sealE.getChild("seal_position");
		if(seal_positionE==null){
			return notFoundNode("seal_position");
		}
		seal_position=seal_positionE.getText();
		Element seal_xE=sealE.getChild("seal_x");
		if(seal_xE==null){
			return notFoundNode("seal_x");
		}
		seal_x=seal_xE.getText();
		Element seal_yE=sealE.getChild("seal_y");
		if(seal_yE==null){
			return notFoundNode("seal_y");
		}
		seal_y=seal_yE.getText();
		Element oriDataE=sealE.getChild("oriData");
		if(oriDataE==null){
			return notFoundNode("oriData");
		}
		oriData=oriDataE.getText();
		DSign util=new DSign();
		seal_data=SealInterface.sealDataById(sealId);
		if(seal_data==null||seal_data.equals("")){
			hasError=true;
			this.errorMessge="未找到id为"+sealId+"的印章";
			return this.errorReturn();
		}
		if(userFlag.equals("2")){
			Element userIDE=userE.getChild("userID");
			if(userIDE==null){
				return notFoundNode("userID");
			}
			user_id=userIDE.getText();
			Element userPSWE=userE.getChild("userPSW");
			if(userPSWE==null){
				return notFoundNode("userPSW");
			}
			user_psw=userPSWE.getText();
		}
		Cert cert=this.getCert(userFlag, certInfo,user_id,user_psw);
		if(cert==null){
			hasError=true;
			this.errorMessge="未找到用户对应证书";
			return this.errorReturn();
		}
		if(!cert.getCert_src().equals(CertType.clientPFX)||cert.getPfx_content()==null){
			hasError=true;
			this.errorMessge="该用户未和pfx证书绑定";
			return this.errorReturn();
		}
		String pfxBase64=cert.getPfx_content();
		String pfx_psw=cert.getCert_psd();
		String sealRes=util.addSealPfxBase64(pfxBase64, pfx_psw, seal_data, sealId, sealName, seal_position, seal_x, seal_y, oriData);
		//String sealRes=util.addSeal("c:/test.pfx", "1111", seal_data, sealId, sealName, seal_position, seal_x, seal_y, oriData);
		if(sealRes==null||sealRes.equals("")){
			hasError=true;
			this.errorMessge=util.getErrormessge();
			return this.errorReturn();
		}
		return this.addSealReturnXml(sealRes);
	}
	/**
	 * 功能：将旧的印章格式转成新的印章格式
	 * @param wbData
	 * 				旧盖章结果
	 * @return  新的格式的盖章结果
	 */
	public String convertWebsignData(String oldWebsignData){
		DecSealUtil dec_seal=DecSealUtil.getDec_seal();
		String newWebsignData=dec_seal.convertWebsignData(oldWebsignData);
		return newWebsignData;		
	}
	/**
	 * webservice接口，验证接口
	 * @param xmlStr
	 * @return
	 */
	public String verifyDoc(String xmlStr){
		DSign util=new DSign();
		Element root=getRootElement(xmlStr);//得到xml参数的跟节点
		boolean hasError=false;
		boolean hasVerify=true;//是否需要校验数据
		if(!isLegalUser(xmlStr)){//如果不是合法用户
			hasError=true;
			return this.errorReturn();
		}
		if(root==null){	
			hasError=true;
			return this.errorReturn();
		}
		Element infoE=root.getChild("info");//得到info节点
		if(infoE==null){
			return notFoundNode("info");
		}
		Element sealResE=infoE.getChild("sealRes");//得到sealRes节点
		if(sealResE==null){
			return notFoundNode("sealRes");
		}
		util.setStoreData(sealResE.getText());
		List<Element> verifyEs=infoE.getChildren("verify");
		if(verifyEs==null||verifyEs.size()==0){
			//return notFoundNode("verify");
			hasVerify=false;
		}
		for(int i=0;i<verifyEs.size();i++){
			Element verifyE=verifyEs.get(i);
			Element sealNameE=verifyE.getChild("sealName");
			if(sealNameE!=null){
				Element oriDataE=verifyE.getChild("oriData");
				if(oriDataE!=null){
					util.SetSealSignData(sealNameE.getText(), oriDataE.getText());
				}
			}
		}
		return util.parseAllSeal(hasVerify);
		
	}
	
	/**
	 * Html5上传文档接口
	 * 
	 * @param xmlStr
	 * @return
	 * */
	public String UploadAndSynFile(String xmlStr){
		
		boolean flag = false;
		int retGetCount = 0;
		boolean hasError;
		
		//得到xml参数的跟节点
		Element root=getRootElement(xmlStr);
		
		if(!isLegalUser(xmlStr)){//如果不是合法用户
			hasError=true;
			return this.errorReturn();
		}else{
		
			Element FILE_LISTE = root.getChild("FILE_LIST");	//得到FILE_LIST节点
			
			if(FILE_LISTE == null){
				return notFoundNode("FILE_LIST");
			}
			
			List<Element> list = FILE_LISTE.getChildren("TREE_NODE");
			
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Element FILE_NOE = list.get(i).getChild("FILE_NO");
					Element FILE_PATHE = list.get(i).getChild("FILE_PATH");
					
					String FILE_NO = FILE_NOE.getValue();
					String FILE_PATH = FILE_PATHE.getValue();
					
					String SAVE_PATH = MyOper.bpath()+"\\doc\\"+FILE_NO;
					
					DownloadFileAndSave(FILE_PATH,SAVE_PATH);
					
					try{
						SrvSealUtil srv_seal = new SrvSealUtil();
						int nObjID = srv_seal.openObj(SAVE_PATH, 0, 0);
						int retLogin = srv_seal.login(nObjID,2, "test", "");
						retGetCount = srv_seal.getPageCount(nObjID);
						
						for(int j = 0; j<retGetCount; j++){
							String img_path = MyOper.bpath() + "\\pic\\"+FILE_NO+"_"+j+".gif";
							File file = new File(img_path);
							if(!file.exists()){
								srv_seal.getPageImg(nObjID, j, 800, img_path, "gif");
							}
						}
					}catch (Exception e) {
						
						this.messge = "转换文件失败！";
						
						return this.returnMsg("1","");
					}
				}
			}else{
				
				this.messge = "未找到TREE_NODE节点";
				
				return this.returnMsg("1","");
			}
		}
		
		this.messge = "操作成功!";
		
		return this.returnMsg("0",String.valueOf(retGetCount));
	}
	//下载合成文件
	private String DownloadFileAndSave(String FILE_PATH,String SAVE_PATH) {
		try {
			URL url = new URL(FILE_PATH);
			FileUtil fu = new FileUtil();
			InputStream is = url.openStream();
			fu.save(fu.getFileByte(is), SAVE_PATH);
			is = null;
		} catch (Exception e) {
			return "FILE_PATH错误，找不到指定文档,请检查！";
		}
		return SAVE_PATH;
	}
	//返回XML格式字符串
	private String returnMsg(String ret,String pageNo){
		Document doc=new Document();
		Element rootE=new Element("ROOT");
		doc.setRootElement(rootE);
		Element infoE=new Element("INFO");
		rootE.addContent(infoE);
		Element hasErrorE=new Element("RETCODE");
		infoE.addContent(hasErrorE);
		Element pageNoE=new Element("PAGENO");
		infoE.addContent(pageNoE);
		Element errorMessageE=new Element("MESSAGE");
		infoE.addContent(errorMessageE);
		pageNoE.setText(pageNo);
		hasErrorE.setText(ret);
		errorMessageE.setText(this.messge);
		return getXMLString(doc);
	}
	
	/**
	 * Html5保存文档接口
	 * 
	 * @param xmlStr
	 * @return
	 * */
	public String SaveFile(String xmlStr){
		boolean flag = false;
		String file_path = "";
		//得到xml参数的跟节点
		Element root=getRootElement(xmlStr);
		
		if(root == null){
			this.messge = "解析XML失败!";
			return this.returnMsgForSaveFile("1","");
		}
		
		Element BASE_DATAE = root.getChild("BASE_DATA");		//得到BASE_DATA节点
		
		if(BASE_DATAE == null){
			return notFoundNode("BASE_DATA");
		}
		
		Element SYS_IDE = BASE_DATAE.getChild("SYS_ID");		//获取应用系统ID节点
		Element USER_IDE = BASE_DATAE.getChild("USER_ID");		//获取应用系统用户名节点
		Element USER_PSDE = BASE_DATAE.getChild("USER_PSD");	//获取应用系统密码节点
		
		if(SYS_IDE == null){
			return notFoundNode("SYS_ID");
		}
		
		if(USER_IDE == null){
			return notFoundNode("USER_ID");
		}
		
		if(USER_PSDE == null){
			return notFoundNode("USER_PSD");
		}
		
		String SYS_ID = SYS_IDE.getValue();					//获取应用系统ID
		String USER_ID = USER_IDE.getValue();				//获取用户ID
		String USER_PSD = USER_PSDE.getValue();				//获取用户密码
		String FILE_NO = "";
		String FILE_DATA = "";
		try {
			AppSysServiceImpl appobj = (AppSysServiceImpl) getBean("appSysService");
			flag = appobj.getAppIp(SYS_ID, getClientIp());
		} catch (Exception e1) {
			this.messge = "校验应用系统失败!";
			return this.returnMsgForSaveFile("1","");
		}
		
		if(flag){
			
			Element FILE_LISTE = root.getChild("FILE_LIST");	//得到FILE_LIST节点
			
			if(FILE_LISTE == null){
				return notFoundNode("FILE_LIST");
			}
			
			List<Element> list = FILE_LISTE.getChildren("TREE_NODE");
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Element FILE_NOE = list.get(i).getChild("FILE_NO");
					Element FILE_DATAE = list.get(i).getChild("FILE_DATA");
					
					FILE_NO = FILE_NOE.getValue();
					FILE_DATA = FILE_DATAE.getValue();
					logger.info(FILE_DATA+"FILE_DATA");
					SrvSealUtil srv_seal = new SrvSealUtil();
					file_path = MyOper.bpath() + "\\doc\\"+FILE_NO;
					int nObjID = srv_seal.openObj(file_path, 0, 0);
					int n = srv_seal.login(nObjID, 2, "admin", "");
					int j = srv_seal.getPageCount(nObjID);
					int k = srv_seal.setValue(nObjID, "SET_PEN_DATA_DIRECTLY", FILE_DATA);
					int w = srv_seal.saveFile(nObjID, file_path, "pdf",0);
					logger.info("login:"+n+" getPageCount:"+j+" setValue"+k+" saveFile"+w);
				}
			}else{
					
				this.messge = "未找到TREE_NODE节点";
				
				return this.returnMsgForSaveFile("1","");
			}
		}
		file_path = "http://"+Constants.getProperty("server_ip")+":"+Constants.getProperty("server_port")+ "/Seal/doc/"+FILE_NO;
		this.messge="操作成功!";
		return returnMsgForSaveFile("0",file_path);
	}
	//返回XML格式字符串
	private String returnMsgForSaveFile(String ret,String fileUrl){
		Document doc=new Document();
		Element rootE=new Element("ROOT");
		doc.setRootElement(rootE);
		Element infoE=new Element("INFO");
		rootE.addContent(infoE);
		Element hasErrorE=new Element("RETCODE");
		infoE.addContent(hasErrorE);
		Element FILEURLE=new Element("FILEURL");
		infoE.addContent(FILEURLE);
		Element errorMessageE=new Element("MESSAGE");
		infoE.addContent(errorMessageE);
		FILEURLE.setText(fileUrl);
		hasErrorE.setText(ret);
		errorMessageE.setText(this.messge);
		return getXMLString(doc);
	}
	
}
