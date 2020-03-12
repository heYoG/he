package serv;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.tempuri.FaxServiceStub;

import com.dj.seal.api.SealInterface;
import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.gaizhangRule.po.GaiZhangRule;
import com.dj.seal.gaizhangRule.service.impl.GaiZhangRuleSrvImpl;
import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.hotel.service.api.NSHRecordService;
import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.modelFile.service.impl.ModelFileServiceImpl;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.unitAndLevel.impl.UnitAndLevelDaoImpl;
import com.dj.seal.unitAndLevel.vo.UnitAndLevelVo;
import com.dj.seal.util.Constants;
import com.dj.seal.util.ceUtil.CEUploadUtil;
import com.dj.seal.util.ceUtil.TransCePo;
import com.dj.seal.util.encrypt.Base64;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.encrypt.FileUtil;
import com.dj.seal.util.encrypt.MD5Helper;
import com.dj.seal.util.encrypt.ThreeDesHelper;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.seal.util.table.ModelFileUtil;

import serv.rules.GetRuleList;
import serv.rules.GetRules;
import srvSeal.SrvSealUtil;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;
import xmlUtil.aipData.AipKV;
import xmlUtil.aipData.AipTplData;
import xmlUtil.xml.XMLNote;

import com.dj.seal.util.file.FtpClientUtil;
import com.dj.sign.SignUtil;
import com.sun.mirror.apt.Filer;


public class MyOper {

	static Logger logger = LogManager.getLogger(MyOper.class.getName());
	private static String username = Constants.getProperty("username");
	private static String password = Constants.getProperty("password");
	private static String saveBasePath = Constants.getProperty("savepdf");
	private static String svaeBasePthWindow=Constants.getProperty("save_path");
	private static UnitAndLevelDaoImpl ud=(UnitAndLevelDaoImpl)getBean("UnitAndLevelDaoImpl");
	
	private static SrvSealUtil srv_seal() {
		SrvSealUtil srv_seal = (SrvSealUtil) getBean("srvSeal");//
		if (srv_seal == null) {
			srv_seal = new SrvSealUtil();
		}
		String fontPath = Constants.getProperty("fontspath");
		int setFont = srv_seal.setValue(0,"SET_FONTFILES_PATH", fontPath);
		logger.info("setFont:" + setFont);
		return srv_seal;
	}

	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}

	public static String bpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type = Constants.getProperty("is_type");
			if (is_type.equals("1")) {// 从配置文件读取路径
				bpath = Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return bpath;
	}

	/**
	 * 根据请求生成合并文档
	 * 
	 *            请求的xml字符串
	 * @return 0为成功，1为失败
	 */
	public static String makeMergerFile(XMLNote xml) throws Exception {
		XMLNote fileList = xml.getByName("FILE_LIST");
		XMLNote metaNote = xml.getByName("META_DATA");
		String merger_no = metaNote.getValue("MERGER_NO");// 获取合并后的文档名称
		if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
			return "缺少FILE_LIST节点";
		}
		List<XMLNote> notes = fileList.getChilds();
		SrvSealUtil srv_seal = srv_seal();
		String bpath = bpath();
		logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
		logger.info("ocxfile:" + ocxfile);
		int nObjID = srv_seal.openObj("", 0, 0);
		logger.info("MyOper-nObjID:" + nObjID);
		String userType = Constants.getProperty("userType");
		String userAccess = Constants.getProperty("userAccess");
		String userPwd = Constants.getProperty("userPwd");
		int l = srv_seal.login(nObjID, Integer.parseInt(userType), userAccess,
				userPwd);
		logger.info("MyOper-login:" + l);
		for (XMLNote note : notes) {
			String cj_type = note.getValue("CJ_TYPE");// 获取应用场景(data：模板合成，file读取url)
			String model_name = note.getValue("MODEL_NAME");// 获取模板名称
			String file_no = note.getValue("FILE_NO");// 获取文档名称
			if (model_name == "" || model_name == null) {
				return "MODEL_NAME模板名称不能为空！";
			} else {
				String busi_data = note.getValue("APP_DATA");// 键值对
				if (note.countOfChild("APP_DATA") > 1) {
					return "缺少APP_DATA节点";
				}
				busi_data = busi_data == null ? XMLNote.toAipTplData(
						note.getByName("APP_DATA").getChilds().get(0), false)
						.dataStr() : busi_data;// XML
				String docData = "STRDATA:" + busi_data;
				//logger.info("模板数据:"+busi_data);
				int ap = 0;
				if (cj_type.equals("data")) {
					String templatePath = bpath + "upload\\" + model_name+ ".aip";
					logger.info("templatePath:" + templatePath);
					ap = srv_seal.addPage(nObjID, templatePath, docData);
					if (ap == 0) {
						return "addPage方法返回值是0及模板与数据合成失败";
					}
				} else if (cj_type.equals("file")) {
					downLoadDoc_Mer(note);
					String templatePath = bpath + "doc\\"
							+ file_no.split("\\.")[0] + ".pdf";
					ap = srv_seal.addPage(nObjID, templatePath, "");
				}
				logger.info("MyOper-addPage:" + ap);
			}
		}
		String savePath = bpath + "doc\\" + merger_no.split("\\.")[0] + ".pdf";
		logger.info("保存地址1：" + savePath);
		int s = srv_seal.saveFile(nObjID, savePath, "pdf", 0);
		logger.info("s:" + s);
		if (s == 0) {
			return "saveFIle保存模板失败，返回值是0";
		}
		return "成功";
	}

	public static String arMakeFiles(String xmlStr) throws Exception {
		String downPath = "";
		try {
			// java.net.URLEncoder.encode(param);中文转码
			// xmlStr=java.net.URLDecoder.decode(xmlStr);//再转回中文
			logger.info("数据是：" + xmlStr);
			SrvSealUtil srv_seal = srv_seal();
			String bpath = bpath();
			logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
			int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
			logger.info("ocxfile:" + ocxfile);
			int nObjID = srv_seal.openObj("", 0, 0);
			logger.info("MyOper-nObjID:" + nObjID);
			String userType = Constants.getProperty("userType");
			String userAccess = Constants.getProperty("userAccess");
			String userPwd = Constants.getProperty("userPwd");
			int l = srv_seal.login(nObjID, Integer.parseInt(userType),
					userAccess, userPwd);
			logger.info("MyOper-login:" + l);
			XMLNote xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
			logger.info("xml:" + xml);
			String mobanName = xml.getValue("mobanName");
			logger.info("mobanName:" + mobanName);
			// String mobanData = xml.getValue("mobanData");
			// String mobanStr[] = mobanData.split("&");
			// StringBuffer s1 = new StringBuffer();
			// for (int i = 0; i < mobanStr.length; i++) {
			// // if(i==0){
			// s1.append(mobanStr[i]).append("\r\n");
			// // }else{
			// // s1.append("&"+mobanStr[i]).append("\r\n");
			// // }
			// }
			// logger.info("s1:" + s1.toString());
			// bpath = "C://tomcat//apache-tomcat-6.0.18_0407//webapps//Seal//";
			logger.info("app_data:" + xml.getByName("app_data"));
			String templatePath = bpath + "upload/" + mobanName + ".aip";
			String busi_data = XMLNote.toAipTplData(
					xml.getByName("app_data").getChilds().get(0), false)
					.dataStr();// XML
			logger.info("basi_data:" + busi_data);
			String docData = "STRDATA:" + busi_data;
			logger.info("templatePath:" + templatePath);
			logger.info("docData:" + docData);
			int ap = srv_seal.addPage(nObjID, templatePath, docData);// SrvSealUtil.java
			logger.info("ap:" + ap);
			if (ap == 0) {
				return "X-失败" + ap;
			}
			String savePath = bpath + "sealdoc//" + mobanName + ".pdf";
			logger.info("保存地址2：" + savePath);
			int s = srv_seal.saveFile(nObjID, savePath, "pdf", 0);
			logger.info("s:" + s);
			if (s == 0) {
				return "X-失败" + ap;
			}
			downPath = "http://" + Constants.getProperty("server_ip") + ":"
					+ Constants.getProperty("server_port")
					+ "/Seal/doc/download.jsp?name=" + mobanName + ".pdf";
			logger.info("downPath:" + downPath);
		} catch (Exception e) {
			return "X-失败";
		}
		return downPath;
	}

	public static String aipPdf(String xmlStr) throws Exception {
		logger.info("收到的xml:" + xmlStr);
		String downPath = "";
		try {
			// java.net.URLEncoder.encode(param);中文转码
			// xmlStr=java.net.URLDecoder.decode(xmlStr);//再转回中文
			// logger.info("数据是：" + xmlStr);
			SrvSealUtil srv_seal = srv_seal();
			String bpath = bpath();
			XMLNote xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
			String aipName = xml.getValue("aipName");
			String aipUrl = xml.getValue("aipUrl");
			logger.info("aipName:" + aipName);
			logger.info("aipUrl:" + aipUrl);
			URL url = new URL(aipUrl);
			// bpath = "C://tomcat//apache-tomcat-6.0.18_0407//webapps//Seal//";
			String open_path = bpath + "upload//" + aipName;
			logger.info("open_path:" + open_path);
			try {
				FileUtil fu = new FileUtil();
				InputStream is = url.openStream();
				fu.save(fu.getFileByte(is), open_path);
				is = null;
				// logger.info("成功写入文件");
			} catch (Exception e) {
				return "FILE_PATH错误，找不到指定文档,请检查！";
			}
			// logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
			int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
			// logger.info("ocxfile:" + ocxfile);
			int nObjID = srv_seal.openObj(open_path, 0, 0);
			// logger.info("MyOper-nObjID:" + nObjID);
			String userType = Constants.getProperty("userType");
			String userAccess = Constants.getProperty("userAccess");
			String userPwd = Constants.getProperty("userPwd");
			int l = srv_seal.login(nObjID, Integer.parseInt(userType),
					userAccess, userPwd);
			String savePath = bpath + "sealdoc//" + aipName.split("\\.")[0]
					+ ".pdf";
			// logger.info("保存地址3：" + savePath);
			int s = srv_seal.saveFile(nObjID, savePath, "pdf", 0);
			// logger.info("s:" + s);
			if (s == 0) {
				return "X-失败" + s;
			}
			downPath = "http://" + Constants.getProperty("server_ip") + ":"
					+ Constants.getProperty("server_port")
					+ "/Seal/doc/download.jsp?name=" + aipName.split("\\.")[0]
					+ ".pdf";
			// logger.info("downPath:" + downPath);
		} catch (Exception e) {
			return "X-失败";
		}
		return downPath;
	}

	/**
	 * 根据请求模板合成文档
	 * 
	 * @param xmlStr 请求的xml字符串
	 * @return       0为成功，1为失败
	 */
	public static String makeFiles(XMLNote xml) throws Exception {
		XMLNote fileList = xml.getByName("FILE_LIST");
		if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
			return "缺少FILE_LIST节点";
		}
		List<XMLNote> notes = fileList.getChilds();
		SrvSealUtil srv_seal = srv_seal();
		String bpath = bpath();
		logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
		if (ocxfile != 1) {
			return "HWPostil.ocx没有找到！返回值：" + ocxfile;
		}
		logger.info("ocxfile:" + ocxfile);
		String userType = Constants.getProperty("userType");

		String userAccess = Constants.getProperty("userAccess");
		String userPwd = Constants.getProperty("userPwd");
		for (XMLNote note : notes) {
			String cj_type = note.getValue("CJ_TYPE");// 获取应用场景(data：模板合成，file读取url，base64是直接传递待改造文件的base64)
			String file_no = note.getValue("FILE_NO");
			if (cj_type.equals("data")) {
				int nObjID = srv_seal.openObj("", 0, 0);
				logger.info("MyOper-nObjID:" + nObjID);
				int l = srv_seal.login(nObjID, Integer.parseInt(userType),
						userAccess, userPwd);
				logger.info("MyOper-login:" + l);
				String model_name = note.getValue("MODEL_NAME");// 获取模板名称
				if (model_name == "" || model_name == null) {
					return "MODEL_NAME模板名称不能为空！";
				} else {
					ModelFileServiceImpl modelSer = (ModelFileServiceImpl) getBean("modelFileService");
					logger.info("model_name:" + model_name);
					String templatePath = bpath + "upload/" + model_name
							+ ".aip";
					logger.info("templatePath:" + templatePath);
					File files = new File(templatePath);
					if (!files.isFile()) {// 判断模板文件是否存在
						logger.info("模板不存在重新下载...");
						ModelFile obj = new ModelFile();
						obj.setContent_data("file_content");
						obj.setModel_name(model_name);
						modelSer.dbToFileS(obj, templatePath);
						logger.info("下载模板成功");
					}
					String busi_data = note.getValue("APP_DATA");// 键值对
					if (note.countOfChild("APP_DATA") > 1) {
						return "缺少APP_DATA节点";
					}
					busi_data = busi_data == null ? XMLNote.toAipTplData(
							note.getByName("APP_DATA").getChilds().get(0),
							false).dataStr() : busi_data;// XML
					String docData = "STRDATA:" + busi_data;
					int ap = srv_seal.addPage(nObjID, templatePath, docData);// SrvSealUtil.java
					if (ap == 0) {
						return "addPage方法返回值是0及模板与数据合成失败";
					}
				}
				// String savePath = bpath + "doc\\" + file_no.split("\\.")[0]
				// + ".pdf";
				String savePath = bpath + "doc\\" + file_no.split("\\.")[0]
						+ ".pdf";
				logger.info("保存地址4：" + savePath);
				int s = srv_seal.saveFile(nObjID, savePath, "pdf", 0);
				logger.info("s:" + s);
				if (s == 0) {
					return "saveFIle保存模板失败，返回值是0";
				} else {
				}
			} else if (cj_type.equals("file")) {
				String open_path = "";
				if (System.getProperty("os.name").toUpperCase()
						.indexOf("WINDOWS") != -1) {
					open_path = bpath + "doc/" + file_no;
				} else {
					open_path = Constants.getProperty("savepdf") + "/"
							+ file_no;
				}
				String file_path = note.getValue("FILE_PATH");
				String REQUEST_TYPE = note.getValue("REQUEST_TYPE");// 请求类型1:ftp,0:http
				if (REQUEST_TYPE.equals("1")) {
					String ftp_address = note.getValue("ftp_address");
					String ftp_port = note.getValue("ftp_port");
					String ftp_user = note.getValue("ftp_user");
					String ftp_pwd = note.getValue("ftp_pwd");
					try {
						fileDownALL(ftp_address, ftp_port, ftp_user, ftp_pwd,
								file_path, file_no, open_path);
					} catch (Exception e) {
						throw new Exception("ftp读取文件失败，请检查ftp是否开启及文件是否存在！");
					}
				} else if (REQUEST_TYPE.equals("0")) {
					open_path = bpath + "doc\\" + file_no;
					String path_name = file_path.substring(file_path
							.lastIndexOf("/") + 1);
					String url_path = file_path.substring(0,
							file_path.lastIndexOf("/"));
					URL url = new URL(url_path + "/"
							+ URLEncoder.encode(path_name, "UTF-8"));
					try {
						FileUtil fu = new FileUtil();
						InputStream is = url.openStream();
						fu.save(fu.getFileByte(is), open_path);
						is = null;
						logger.info("成功写入文件");
					} catch (Exception e) {
						return "FILE_PATH错误，找不到指定文档,请检查！";
					}
				}
				if (file_no.indexOf(".doc") != -1
						|| file_no.indexOf(".docx") != -1) {
					logger.info("open_path:" + open_path);
					// int nObjID = srv_seal.openObj(open_path, 0, 0);
					// logger.info("MyOper-nObjID:" + nObjID);
					// int login = srv_seal.login(nObjID, 4, "HWSEALDEMOXX",
					// "DEMO");
					// if (login != 0) {
					// return "login登录失败,返回值是：" + login;
					// }
					// logger.info("login:" + login);
					String save_path = "";
					if (System.getProperty("os.name").toUpperCase()
							.indexOf("WINDOWS") != -1) {
						save_path = bpath + "doc\\" + file_no.split("\\.")[0]
								+ ".pdf";
					} else {
						save_path = Constants.getProperty("savepdf") + "/"
								+ file_no.split("\\.")[0] + ".pdf";
					}
					// int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
					// if (s != 1) {
					// return "保存文档失败,返回值是：" + s;
					// }
					// logger.info("MyOper-saveFile:" + s);
					// Runtime.getRuntime().exec("TASKKill /F /IM WINWORD.EXE /T");
					int nOfficeID = srv_seal.openOffice(0);
					logger.info("打开文档(1为成功):" + nOfficeID);
					if (nOfficeID < 1) {
						return "打开文档失败：" + nOfficeID;
					}
					// int otp = srv_seal.officeToPdf(-1, filePath, savePath);
					int otp = srv_seal.officeToPdf(nOfficeID, open_path,
							save_path);
					logger.info("转化文档(1为成功):" + otp);
					int clooff = srv_seal.closeOffice(nOfficeID);
					logger.info("关闭文档返回值:" + clooff);
					if (otp < 1) {
						return "转换文档失败：" + otp;
					}
				}
			} else if (cj_type.equals("base64")) {
				String open_path = "";
				if (System.getProperty("os.name").toUpperCase()
						.indexOf("WINDOWS") != -1) {
					open_path = bpath + "doc"+File.separatorChar + file_no;//自适应路径格式
				} else {
					open_path = Constants.getProperty("savepdf") + "/"
							+ file_no;
				}
				String file_base64 = note.getValue("FILE_BASE64");
				byte[] fileData = Base64.decode(file_base64);
				FileUtil fu = new FileUtil();
				fu.save(fileData, open_path);
				logger.info("成功写入文件");

				if (file_no.indexOf(".doc") != -1
						|| file_no.indexOf(".docx") != -1) {
					logger.info("open_path:" + open_path);
					int nObjID = srv_seal.openObj(open_path, 0, 0);
					logger.info("MyOper-nObjID:" + nObjID);
					int login = srv_seal.login(nObjID, 4, "HWSEALDEMOXX",
							"DEMO");
					if (login != 0) {
						return "login登录失败,返回值是：" + login;
					}
					logger.info("login:" + login);
					String save_path = "";
					if (System.getProperty("os.name").toUpperCase()
							.indexOf("WINDOWS") != -1) {
						save_path = bpath + "doc\\" + file_no.split("\\.")[0]
								+ ".pdf";
					} else {
						save_path = Constants.getProperty("savepdf") + "/"
								+ file_no.split("\\.")[0] + ".pdf";
					}
					int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
					if (s != 1) {
						return "保存文档失败,返回值是：" + s;
					}
					logger.info("MyOper-saveFile:" + s);
					Runtime.getRuntime().exec("TASKKill /F /IM WINWORD.EXE /T");
				}
			}
		}
		return "成功";
	}

	/**
	 * 根据请求加盖印章
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @return 0为成功，1为失败
	 */
	public static String addSeal(String is_merger, XMLNote xml, String ip)
			throws Exception {
		SrvSealUtil srv_seal = srv_seal();
		String bpath = bpath();
		logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
		logger.info("ocxfile:" + ocxfile);
		if (is_merger.equals("合")) {
			String file_no = xml.getValue("META_DATA.MERGER_NO");// 合并后的pdf文档
			String rule_type = xml.getValue("META_DATA.RULE_TYPE");// 获取盖章类型（0:规则号盖章，1是规则信息盖章）
			String AREA_SEAL = xml.getValue("META_DATA.AREA_SEAL");// 是否添加标记印章1：是，0：否
			String rule_no = xml.getValue("META_DATA.RULE_NO");// 获取规则号
			String open_path = bpath + "doc/" + file_no.split("\\.")[0]
					+ ".pdf";
			logger.info("open_path:" + open_path);
			int nObjID = srv_seal.openObj(open_path, 0, 0);
			logger.info("MyOper-nObjID:" + nObjID);
			String userType = Constants.getProperty("userType");
			String userAccess = Constants.getProperty("userAccess");
			String userPwd = Constants.getProperty("userPwd");
			int l = srv_seal.login(nObjID, Integer.parseInt(userType),
					userAccess, userPwd);
			logger.info("MyOper-login:" + l);
			int pagesize = srv_seal.getPageCount(nObjID);
			if (AREA_SEAL.equals("true")) {
				AipTplData aipTpl = new AipTplData();
				aipTpl = XMLNote.toAipTplData2(xml.getByName("AREA_DATA"),
						false);
				for (AipKV obj : aipTpl.getKvs()) {
					logger.info("key:" + obj.getKey());
					logger.info("value:" + obj.getValue());
					int s1 = srv_seal.setDocProperty(nObjID, obj.getKey(),
							obj.getValue());
					logger.info("s1:" + s1);
				}
			}
			if (rule_type.equals("0")) {
				if (rule_no.indexOf(",") != -1) {
					String[] rule_str = rule_no.split(",");
					for (String ruleno : rule_str) {
						int a = addSealDJ(srv_seal, ruleno, nObjID, pagesize);
						if (a != 1) {
							return "盖章失败，返回值是" + a;
						}
						String save_path = bpath + "sealdoc\\"
								+ file_no.split("\\.")[0] + ".pdf";
						int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
						if (s == 1) {
							String ftp_savepath = xml
									.getValue("META_DATA.ftp_savepath");
							if (!ftp_savepath.equals("")) {
								String ftp_address = xml
										.getValue("META_DATA.ftp_address");
								String ftp_port = xml
										.getValue("META_DATA.ftp_port");
								String ftp_user = xml
										.getValue("META_DATA.ftp_user");
								String ftp_pwd = xml
										.getValue("META_DATA.ftp_pwd");
								try {
									FtpClientUtil obj = new FtpClientUtil(
											ftp_address, ftp_user, ftp_pwd,
											ftp_port);

									obj.upload(save_path,
											file_no.split("\\.")[0] + ".pdf",
											ftp_savepath);
								} catch (Exception er) {
									throw new Exception("上传文档失败");
								}
							}
						} else {
							return "盖章保存失败返回值是：" + s;
						}
					}
				} else {
					int a = addSealDJ(srv_seal, rule_no, nObjID, pagesize);
					if (a != 1) {
						return "盖章失败，返回值是" + a;
					}
					String save_path = bpath + "sealdoc\\"
							+ file_no.split("\\.")[0] + ".pdf";
					int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
					if (s == 1) {
						String ftp_savepath = xml
								.getValue("META_DATA.ftp_savepath");
						if (!ftp_savepath.equals("")) {
							String ftp_address = xml
									.getValue("META_DATA.ftp_address");
							String ftp_port = xml
									.getValue("META_DATA.ftp_port");
							String ftp_user = xml
									.getValue("META_DATA.ftp_user");
							String ftp_pwd = xml.getValue("META_DATA.ftp_pwd");
							try {
								FtpClientUtil obj = new FtpClientUtil(
										ftp_address, ftp_user, ftp_pwd,
										ftp_port);

								obj.upload(save_path, file_no.split("\\.")[0]
										+ ".pdf", ftp_savepath);
							} catch (Exception er) {
								throw new Exception("上传文档失败");
							}
						}
					} else {
						return "盖章保存失败返回值是：" + s;
					}
				}
			} else {
				String ruleInfo = xml.getValue("META_DATA.RULE_INFO");// 规则描述信息
				String certname = xml.getValue("META_DATA.CERT_NAME");// 证书名称
				String sealname = xml.getValue("META_DATA.SEAL_NAME");// 印章名称
				int a = addSealdesc(srv_seal, nObjID, ruleInfo, certname,
						sealname);
				if (a != 1) {
					return "盖章失败，返回值是" + a;
				}
				String save_path = bpath + "sealdoc\\"
						+ file_no.split("\\.")[0] + ".pdf";
				;
				logger.info("save_path:" + save_path);
				int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);//
				logger.info("s:" + s);
				if (s != 1) {
					return "盖章保存失败返回值是：" + s;
				}
			}
		} else {
			XMLNote fileList = xml.getByName("FILE_LIST");
			if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
				return "缺少FILE_LIST节点";
			}
			List<XMLNote> notes = fileList.getChilds();
			for (XMLNote note : notes) {
				String file_no = note.getValue("FILE_NO");
				String rule_type = note.getValue("RULE_TYPE");// 获取盖章类型（0:规则号盖章，1是规则信息盖章）
				String AREA_SEAL = note.getValue("AREA_SEAL");// 是否添加标记印章1：是，0：否
				String rule_no = note.getValue("RULE_NO");
				String open_path = bpath + "doc/" + file_no.split("\\.")[0]
						+ ".pdf";
				logger.info("open_path:" + open_path);
				int nObjID = srv_seal.openObj(open_path, 0, 0);
				logger.info("MyOper-nObjID:" + nObjID);
				String userType = Constants.getProperty("userType");
				String userAccess = Constants.getProperty("userAccess");
				String userPwd = Constants.getProperty("userPwd");
				int l = srv_seal.login(nObjID, Integer.parseInt(userType),
						userAccess, userPwd);
				logger.info("MyOper-login:" + l);
				int pagesize = srv_seal.getPageCount(nObjID);
				if (AREA_SEAL.equals("true")) {
					AipTplData aipTpl = new AipTplData();
					aipTpl = XMLNote.toAipTplData2(note.getByName("AREA_DATA"),
							false);
					for (AipKV obj : aipTpl.getKvs()) {
						logger.info("key:" + obj.getKey());
						logger.info("value:" + obj.getValue());
						int s1 = srv_seal.setDocProperty(nObjID, obj.getKey(),
								obj.getValue());
						logger.info("s1:" + s1);
					}
				}
				if (rule_type.equals("0")) {
					if (rule_no.indexOf(",") != -1) {
						String[] rule_str = rule_no.split(",");
						for (String ruleno : rule_str) {
							int a = addSealDJ(srv_seal, ruleno, nObjID,
									pagesize);
							if (a != 1) {
								return "盖章失败，返回值是" + a;
							}
							String save_path = bpath + "sealdoc\\"
									+ file_no.split("\\.")[0] + ".pdf";
							int s = srv_seal.saveFile(nObjID, save_path, "pdf",
									0);
							if (s == 1) {
								String ftp_savepath = note
										.getValue("ftp_savepath");
								if (!ftp_savepath.equals("")) {
									String ftp_address = note
											.getValue("ftp_address");
									String ftp_port = note.getValue("ftp_port");
									String ftp_user = note.getValue("ftp_user");
									String ftp_pwd = note.getValue("ftp_pwd");
									try {
										FtpClientUtil obj = new FtpClientUtil(
												ftp_address, ftp_user, ftp_pwd,
												ftp_port);

										obj.upload(save_path,
												file_no.split("\\.")[0]
														+ ".pdf", ftp_savepath);
									} catch (Exception er) {
										throw new Exception("上传文档失败");
									}
								}
							} else {
								return "盖章保存失败返回值是：" + s;
							}
						}
					} else {
						int a = addSealDJ(srv_seal, rule_no, nObjID, pagesize);
						if (a != 1) {
							return "盖章失败，返回值是" + a;
						}
						String save_path = bpath + "sealdoc\\"
								+ file_no.split("\\.")[0] + ".pdf";
						int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
						if (s == 1) {
							String ftp_savepath = note.getValue("ftp_savepath");
							if (!ftp_savepath.equals("")) {
								String ftp_address = note
										.getValue("ftp_address");
								String ftp_port = note.getValue("ftp_port");
								String ftp_user = note.getValue("ftp_user");
								String ftp_pwd = note.getValue("ftp_pwd");
								try {
									FtpClientUtil obj = new FtpClientUtil(
											ftp_address, ftp_user, ftp_pwd,
											ftp_port);

									obj.upload(save_path,
											file_no.split("\\.")[0] + ".pdf",
											ftp_savepath);
								} catch (Exception er) {
									throw new Exception("上传文档失败");
								}
							}
						} else {
							return "盖章保存失败返回值是：" + s;
						}
					}
				} else {
					String ruleInfo = note.getValue("RULE_INFO");// 规则描述信息
					String certname = note.getValue("CERT_NAME");// 证书名称
					String sealname = note.getValue("SEAL_NAME");// 印章名称
					int a = addSealdesc(srv_seal, nObjID, ruleInfo, certname,
							sealname);
					if (a != 1) {
						return "盖章失败，返回值是" + a;
					}
					String save_path = bpath + "sealdoc\\"
							+ file_no.split("\\.")[0] + ".pdf";
					;
					logger.info("save_path:" + save_path);
					int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);// 0->1
					logger.info("s:" + s);
					if (s != 1) {
						return "盖章保存失败返回值是：" + s;
					}
				}
			}
		}
		return "盖章成功";
	}

	public static String sendfax(String ReceiverFax, String savePath,
			String fileName) throws Exception {
		logger.info("ReceiverFax:" + ReceiverFax);
		if (!ReceiverFax.equals("")) {// 传真号存在，调用接口向传真机发送命令
			try {
				String ws_url = Constants.getProperty("faxws");
				logger.info("ws_url:" + ws_url);
				FaxServiceStub objs = new FaxServiceStub(ws_url);
				logger.info("ReceiverFax:" + ReceiverFax);
				String fax_xml = getFaxXml(ReceiverFax, savePath, fileName);
				logger.info("fax_xml:" + fax_xml);
				FaxServiceStub.SignIn objsign = new FaxServiceStub.SignIn();
				objsign.setStrUserName(Constants.getProperty("userfax"));
				objsign.setStrPassword(Constants.getProperty("pwdfax"));
				logger.info("userfax" + Constants.getProperty("userfax"));
				logger.info("pwdfax" + Constants.getProperty("pwdfax"));
				String tokenId = objs.SignIn(objsign).getSignInResult();
				logger.info("tokenId" + tokenId);
				String reponseXML = "";
				if (tokenId != "" || tokenId != null) {
					FaxServiceStub.Send objsend = new FaxServiceStub.Send();
					objsend.setTokenId(tokenId);
					objsend.setStrSendFax(fax_xml);
					reponseXML = objs.Send(objsend).getSendResult();
					logger.info("reponseXML:" + reponseXML);
					XMLNote xmlP = XMLNote.toNote(reponseXML);
					XMLNote reponseL = xmlP.getByName("ReturnInfo");
					String returnCode = reponseL.getValue("ReturnCode");
					logger.info("returnCode:" + returnCode);
					String returnMessage = reponseL.getValue("ReturnMessage");
					if (!returnCode.equals("0") && !returnMessage.equals("0")) {
						return "45";
					}
				}
			} catch (Exception e) {
				return "56";
			}
		}
		return "0";
	}

	// 下载文件;并返回下载文件的信息
	public static String fileDownALL(String server, String port, String user,
			String password, String filepath, String filename, String savepath)
			throws Exception {
		TelnetInputStream fget = null;
		RandomAccessFile getFile = null;
		FtpClient ftpClient = null;
		try {
			int ch;
			// logger.info("路径"+filepath+filename);
			ftpClient = new FtpClient();// ftp客户端对象
			if (port.equals("") || port.equals(null)) {
				ftpClient.openServer(server);
			} else {
				ftpClient.openServer(server, Integer.parseInt(port));
			}
			ftpClient.login(user, password);// 登录ftp服务器
			ftpClient.binary();// 使用二进制的方式下载
			String pathname = filepath + "/" + filename;
			logger.info("pathname:" + pathname);
			fget = ftpClient.get(pathname);// 读取ftp远程文件
			DataInputStream puts = new DataInputStream(fget);//
			logger.info("savepath:" + savepath);
			File fi = new File(savepath);// 新建本地文件
			getFile = new RandomAccessFile(fi, "rw");// 以读写的方式打开本地文件
			getFile.seek(0); // 将指针放到文件最前段
			while ((ch = puts.read()) >= 0) {// 循环读取远程文件的内容并写入本地文件中
				getFile.write(ch);
			}

		} catch (Exception e) {
			// e.printStackTrace(System.out);
			throw new Exception(e.getMessage());
		} finally {
			try {
				fget.close();
			} catch (Exception e) {
			}
			try {
				getFile.close();
			} catch (Exception e) {
			}
			try {
				ftpClient.closeServer();
			} catch (Exception e) {
			}
		}
		return "下载成功";
	}

	public static String downLoadDoc(XMLNote xml) throws Exception {
		XMLNote fileList = xml.getByName("FILE_LIST");
		if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
			return "缺少FILE_LIST节点";
		}
		List<XMLNote> notes = fileList.getChilds();
		String bpath = bpath();
		SrvSealUtil srv_seal = srv_seal();
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
		logger.info("ocxfile:" + ocxfile);
		for (XMLNote note : notes) {
			String file_no = note.getValue("FILE_NO");
			String open_path = "";
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
				open_path = bpath + "doc/" + file_no;
			} else {
				open_path = Constants.getProperty("savepdf") + "/" + file_no;
			}
			String file_path = note.getValue("FILE_PATH");
			String REQUEST_TYPE = note.getValue("REQUEST_TYPE");// 请求类型1:ftp,0:http
			if (REQUEST_TYPE.equals("1")) {
				String ftp_address = note.getValue("ftp_address");
				String ftp_port = note.getValue("ftp_port");
				String ftp_user = note.getValue("ftp_user");
				String ftp_pwd = note.getValue("ftp_pwd");
				try {
					fileDownALL(ftp_address, ftp_port, ftp_user, ftp_pwd,
							file_path, file_no, open_path);
				} catch (Exception e) {
					throw new Exception("ftp读取文件失败，请检查ftp是否开启及文件是否存在！");
				}
			} else if (REQUEST_TYPE.equals("0")) {
				open_path = bpath + "doc\\" + file_no;
				String path_name = file_path.substring(file_path
						.lastIndexOf("/") + 1);
				String url_path = file_path.substring(0,
						file_path.lastIndexOf("/"));
				URL url = new URL(url_path + "/"
						+ URLEncoder.encode(path_name, "UTF-8"));
				try {
					FileUtil fu = new FileUtil();
					InputStream is = url.openStream();
					fu.save(fu.getFileByte(is), open_path);
					is = null;
					logger.info("成功写入文件");
				} catch (Exception e) {
					return "FILE_PATH错误，找不到指定文档,请检查！";
				}
			}
			if (file_no.indexOf(".doc") != -1 || file_no.indexOf(".docx") != -1) {
				logger.info("open_path:" + open_path);
				int nObjID = srv_seal.openObj(open_path, 0, 0);
				logger.info("MyOper-nObjID:" + nObjID);
				int login = srv_seal.login(nObjID, 4, "HWSEALDEMOXX", "DEMO");
				if (login != 0) {
					return "login登录失败,返回值是：" + login;
				}
				logger.info("login:" + login);
				String save_path = "";
				if (System.getProperty("os.name").toUpperCase()
						.indexOf("WINDOWS") != -1) {
					save_path = bpath + "doc\\" + file_no.split("\\.")[0]
							+ ".pdf";
				} else {
					save_path = Constants.getProperty("savepdf") + "/"
							+ file_no.split("\\.")[0] + ".pdf";
				}
				int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
				logger.info("MyOper-saveFile:" + s);
				Runtime.getRuntime().exec("TASKKill /F /IM WINWORD.EXE /T");
			}
			// int doctype=srv_seal.getDocType(open_path);
			// logger.info("doctype:" + doctype);
			// if(doctype!=11&&doctype!=-2){
			// return "文档格式错误！";
			// }
			// int nOfficeID = srv_seal.openOffice(0);
			// logger.info("打开Office-word(1为成功):"+nOfficeID);
			// if(nOfficeID<1){
			// return "打开word失败！";
			// }
			// int otp = srv_seal.officeToPdf(nOfficeID, open_path, save_path);
			// logger.info("转化文档(1为成功):"+otp);
			// int clooff = srv_seal.closeOffice(nOfficeID);
			// logger.info("关闭:"+clooff);
			// if(otp<1){
			// return "转化文档失败！";
			// }

		}
		return "下载文档成功";
	}

	public static String downLoadDoc_Mer(XMLNote note) throws Exception {
		String bpath = bpath();
		SrvSealUtil srv_seal = srv_seal();
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
		logger.info("ocxfile:" + ocxfile);
		String file_no = note.getValue("FILE_NO");
		String open_path = "";
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			open_path = bpath + "doc/" + file_no;
		} else {
			open_path = Constants.getProperty("savepdf") + "/" + file_no;
		}
		String file_path = note.getValue("FILE_PATH");
		String REQUEST_TYPE = note.getValue("REQUEST_TYPE");// 请求类型1:ftp,0:http
		if (REQUEST_TYPE.equals("1")) {
			String ftp_address = note.getValue("ftp_address");
			String ftp_port = note.getValue("ftp_port");
			String ftp_user = note.getValue("ftp_user");
			String ftp_pwd = note.getValue("ftp_pwd");
			try {
				fileDownALL(ftp_address, ftp_port, ftp_user, ftp_pwd,
						file_path, file_no, open_path);
			} catch (Exception e) {
				throw new Exception("ftp读取文件失败，请检查ftp是否开启及文件是否存在！");
			}
		} else if (REQUEST_TYPE.equals("0")) {
			open_path = bpath + "doc\\" + file_no;
			String path_name = file_path
					.substring(file_path.lastIndexOf("/") + 1);
			String url_path = file_path
					.substring(0, file_path.lastIndexOf("/"));
			URL url = new URL(url_path + "/"
					+ URLEncoder.encode(path_name, "UTF-8"));
			try {
				FileUtil fu = new FileUtil();
				InputStream is = url.openStream();
				fu.save(fu.getFileByte(is), open_path);
				is = null;
				logger.info("成功写入文件");
			} catch (Exception e) {
				return "FILE_PATH错误，找不到指定文档,请检查！";
			}
		}
		if (file_no.indexOf(".doc") != -1 || file_no.indexOf(".docx") != -1) {
			logger.info("open_path:" + open_path);
			int nObjID = srv_seal.openObj(open_path, 0, 0);
			logger.info("MyOper-nObjID:" + nObjID);
			int login = srv_seal.login(nObjID, 4, "HWSEALDEMOXX", "DEMO");
			if (login != 0) {
				return "login登录失败,返回值是：" + login;
			}
			logger.info("login:" + login);
			String save_path = "";
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
				save_path = bpath + "doc\\" + file_no.split("\\.")[0] + ".pdf";
			} else {
				save_path = Constants.getProperty("savepdf") + "/"
						+ file_no.split("\\.")[0] + ".pdf";
			}
			int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
			logger.info("MyOper-saveFile:" + s);
			Runtime.getRuntime().exec("TASKKill /F /IM WINWORD.EXE /T");
		}
		// int doctype=srv_seal.getDocType(open_path);
		// logger.info("doctype:" + doctype);
		// if(doctype!=11&&doctype!=-2){
		// return "文档格式错误！";
		// }
		// int nOfficeID = srv_seal.openOffice(0);
		// logger.info("打开Office-word(1为成功):"+nOfficeID);
		// if(nOfficeID<1){
		// return "打开word失败！";
		// }
		// int otp = srv_seal.officeToPdf(nOfficeID, open_path, save_path);
		// logger.info("转化文档(1为成功):"+otp);
		// int clooff = srv_seal.closeOffice(nOfficeID);
		// logger.info("关闭:"+clooff);
		// if(otp<1){
		// return "转化文档失败！";
		// }

		return "下载文档成功";
	}

	/**
	 * 根据描述信息加盖印章
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @return 0为成功，1为失败
	 */
	public static String addSealDesc(XMLNote xml, String ip) throws Exception {
		XMLNote fileList = xml.getByName("FILE_LIST");
		if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
			return "缺少FILE_LIST节点";
		}
		SrvSealUtil srv_seal = srv_seal();
		List<XMLNote> notes = fileList.getChilds();
		String bpath = bpath();
		logger.info("cert:" + bpath + "loadocx\\HWPostil.ocx");
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx\\HWPostil.ocx");
		logger.info("ocxfile:" + ocxfile);
		for (XMLNote note : notes) {
			String file_no = note.getValue("FILE_NO");
			String open_path = bpath + "doc\\" + file_no.split("\\.")[0]
					+ ".pdf";
			;
			int nObjID = srv_seal.openObj(open_path, 0, 0);
			logger.info("MyOper-nObjID:" + nObjID);
			String userType = Constants.getProperty("userType");
			String userAccess = Constants.getProperty("userAccess");
			String userPwd = Constants.getProperty("userPwd");
			int l = srv_seal.login(nObjID, Integer.parseInt(userType),
					userAccess, userPwd);
			logger.info("MyOper-login:" + l);
			String ruleInfo = note.getValue("RULE_INFO");// 规则描述信息
			String certname = note.getValue("CERT_NAME");// 证书名称
			String sealname = note.getValue("SEAL_NAME");// 印章名称
			String AREA_SEAL = note.getValue("AREA_SEAL");// 是否添加标记印章1：是，0：否
			if (AREA_SEAL.equals("true")) {
				AipTplData aipTpl = new AipTplData();
				aipTpl = XMLNote.toAipTplData2(note.getByName("AREA_DATA"),
						false);
				for (AipKV obj : aipTpl.getKvs()) {
					logger.info("key:" + obj.getKey());
					logger.info("value:" + obj.getValue());
					int s1 = srv_seal.setDocProperty(nObjID, obj.getKey(),
							obj.getValue());
					logger.info("s1:" + s1);
				}
			}
			int a = addSealdesc(srv_seal, nObjID, ruleInfo, certname, sealname);
			if (a != 1) {
				return "盖章失败，返回值是" + a;
			}
			String save_path = bpath + "sealdoc\\" + file_no.split("\\.")[0]
					+ ".pdf";
			;
			logger.info("save_path:" + save_path);
			int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);// SrvSealUtil.java
																	// 0->1
			logger.info("s:" + s);
			if (s == 1) {
				return "盖章成功";
			} else {
				return "盖章保存失败返回值是：" + s;
			}
		}
		return "盖章成功";
	}

	/**
	 * windows盖章
	 * @param srv_seal 工具对象
	 * @param nObjID   打开数据返回值
	 * @param ruleInfo 规则信息
	 * @param certname 证书名称
	 * @param sealname 印章名称
	 * @return  
	 * @throws Exception
	 */
	public static int addSealdesc(SrvSealUtil srv_seal, int nObjID,
			String ruleInfo, String certname, String sealname) throws Exception {

		String bpath = bpath();
		SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
		File winFile=new File(bpath + "doc\\seals\\");
		if(!winFile.isDirectory()){
			winFile.mkdirs();
		}
		File files = new File(winFile.getAbsolutePath()+File.separatorChar + sealname + ".sel");// 读取印章文件
		logger.info(bpath + "doc\\seals\\" + sealname + ".sel");
		if (!files.isFile()) {// 判断印章文件是否存在
			logger.info("印章文件不存在,重新生成 ");
			SealBody obj = new SealBody();
			obj.setSeal_path(bpath + "doc\\seals\\" + sealname + ".sel");
			obj.setSeal_name(sealname);
			objSeal.dbToFile2(obj);//调用新增接口
		}
		// ISealBodyService sealbody = (ISealBodyService)
		// getBean("ISealBodyService");
		// SealBody objseal=sealbody.getSealBodys(sealname);
		// if(objseal!=null){
		//
		// }
		// String sealdata=objseal.getSeal_data();
		
		String pagesData = ruleInfo + bpath + "doc\\seals\\" + sealname+ ".sel";//规则信息盖章
//		String pagesData = ruleInfo;//绝对坐标盖章
		
		CertSrv certsrv = (CertSrv) getBean("CertService");
		Cert cert = certsrv.getObjByName(certname);
		String oriSealName = cert.getCert_name();
		String certpwd = "";
		try {
			DesUtils des = new DesUtils();
			certpwd = des.decrypt(cert.getCert_psd());
			//logger.info("certpwd:"+certpwd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}// 自定义密钥
		logger.info("sign_type:" + Constants.getProperty("sign_type"));
		File winCert=new File(bpath + "doc\\certs\\" );
		if(!winCert.isDirectory()){
			winCert.mkdirs();
		}
		if (Constants.getProperty("sign_type").equals("1")) {
			File file = new File(winCert.getAbsolutePath()+File.separatorChar+ oriSealName + ".pfx");
			if (!file.isFile()) {// 判断文件不存在
				logger.info("证书文件不存在,重新下载... ");
				Cert obj = new Cert();
				obj.setCert_detail(bpath + "doc\\certs\\" + oriSealName
						+ ".pfx");
				obj.setCert_name(oriSealName);
				CertSrv srv_file = (CertSrv) getBean("CertService");
				srv_file.dbToFile(obj);
			}
			oriSealName = bpath + "doc\\certs\\" + oriSealName + ".pfx"
					+ ";PWD:=" + certpwd;
		} else if (Constants.getProperty("sign_type").equals("2")) {
			oriSealName = "SERVER_CERT:" + cert.getCert_detail();
		}
		String sealName = "AUTO_ADD_SEAL_FROM_PATH";
		logger.info("pagesData:" + pagesData);
		logger.info("oriSealName:" + oriSealName);
		logger.info("nObjID:" + nObjID);
		int ret = srv_seal.addSeal(nObjID, pagesData, oriSealName, sealName);
		logger.info("ret:" + ret);
		if (ret == 1) {
			logger.info("加盖印章成功！");
		} else {
			logger.info("加盖印章失败：" + ret);
		}
		return ret;
	}

	public static String pdfVarity(XMLNote xml) throws Exception {
		XMLNote metadata = xml.getByName("META_DATA");
		String file_no = metadata.getValue("FILE_NO");
		String file_path = metadata.getValue("FILE_PATH");
		if (file_path == null || file_path == "") {
			return "没有找到验证的文档";
		}
		String bpath = bpath();
		String filePath = bpath + "doc/verify/" + file_no.split("\\.")[0]
				+ ".pdf";
		SrvSealUtil srv_seal = srv_seal();
		logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
		logger.info("ocxfile:" + ocxfile);
		try {
			URL url = new URL(file_path);
			logger.info("filePath----:" + filePath);
			FileUtil fu = new FileUtil();
			InputStream is = url.openStream();
			fu.save(fu.getFileByte(is), filePath);
			is = null;
		} catch (Exception e) {
			return "验证失败，根据所给路径，找不到需验证文档，请检查！";
		}

		int doctype = srv_seal.getDocType(filePath);
		logger.info("doctype:" + doctype);
		if (doctype == 0 || doctype == 12 || doctype == 52 || doctype == 127) {
			return "52";
		}
		int nObjID = srv_seal.openObj(filePath, 0, 0);
		logger.info("nObjID" + nObjID);
		String v = srv_seal.verify(nObjID);
		logger.info("v:" + v);
		// v = new String(v.getBytes("iso-8859-1"), "GBK");
		return verifyStr(v);
	}

	public static String pdfVarityt(String filePath) throws Exception {
		SrvSealUtil srv_seal = srv_seal();
		String bpath = bpath();
		logger.info("cert:" + bpath + "loadocx/HWPostil.ocx");
		int ocxfile = srv_seal.setCtrlPath(bpath + "loadocx/HWPostil.ocx");
		logger.info("ocxfile:" + ocxfile);
		int doctype = srv_seal.getDocType(filePath);
		logger.info("doctype:" + doctype);
		if (doctype == 0 || doctype == 12 || doctype == 52 || doctype == 127) {
			return "52";
		}
		int nObjID = srv_seal.openObj(filePath, 0, 0);
		logger.info("nObjID" + nObjID);
		String v = srv_seal.verify(nObjID);
		logger.info("v:" + v);
		// v = new String(v.getBytes("iso-8859-1"), "GBK");
		return verifyStrL(v);
	}

	private static String verifyStr(String ret) {
		StringBuffer sb = new StringBuffer();
		if (!ret.startsWith("<+")) {
			return "未发现签名数据，请检查待验证文档是否为加盖了印章的PDF文档!" + ret;
		} else if (ret.indexOf("-><+") != -1) {
			String[] strs = ret.split("->");
			for (int i = 0; i < strs.length; i++) {
				sb.append(verifyStr(strs[i] + "->"));
			}
		} else {
			logger.info("ret:" + ret);
			int b = ret.indexOf("RetCode=") + "RetCode=".length();
			String temp = ret.substring(b);
			String retCode = temp.substring(0, temp.indexOf("/;"));
			if (!retCode.startsWith("-")) {
				// if("0".equals(retCode)){
				sb.append("印章“").append(ret.substring(11, ret.indexOf("/;")));
				sb.append("”：验证通过；\r\n");
				sb.append("\r\n");
			} else {
				sb.append("印章“").append(ret.substring(11, ret.indexOf("/;")));
				sb.append("”：验证结果RetCode=").append(retCode).append("；\r\n");
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}

	private static String verifyStrL(String ret) {
		StringBuffer sb = new StringBuffer();
		if (!ret.startsWith("<+")) {
			return "未发现签名数据，请检查待验证文档是否为加盖了印章的PDF文档!" + ret;
		} else if (ret.indexOf("-><+") != -1) {
			String[] strs = ret.split("->");
			for (int i = 0; i < strs.length; i++) {
				sb.append(verifyStrL(strs[i] + "->"));
			}
		} else {
			// logger.info("ret:"+ret);
			String temp = ret.substring(ret.indexOf("<+") + 2,
					ret.indexOf("->"));
			// logger.info("temp:"+temp);
			String retStr[] = temp.split("/;");
			StringBuffer s1 = new StringBuffer();
			s1.append("印章：" + retStr[0].split("=")[1] + ",序列号："
					+ retStr[2].split("=")[1] + ",主题："
					+ retStr[3].split("=")[1] + ",颁发者："
					+ retStr[4].split("=")[1]);
			// logger.info(s1.toString());
			return s1.toString();
		}
		return sb.toString();
	}

	private static int addSeal(SrvSealUtil srv_seal, String rule_no,
			int nObjID, XMLNote xml, int pagesize) throws Exception {

		String pagesData = "";
		logger.info("rule_no:" + rule_no);
		GaiZhangRuleSrvImpl rule_srv = (GaiZhangRuleSrvImpl) getBean("gaiZhangRuleService");
		String bpath = bpath();
		// File fl=new File(bpath+"doc\\rules\\"+rule_no+".txt");
		String rule_arg = "";
		rule_arg = rule_srv.getRuleArg(rule_no, pagesize);
		logger.info("rule_arg:" + rule_arg);
		if (rule_arg.indexOf("end") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("end"));
			int len = temp.indexOf(",");
			String eStr = rule_arg.substring(rule_arg.indexOf("end"),
					rule_arg.indexOf("end") + len);
			rule_arg = rule_arg.replaceFirst(eStr,
					endStr(eStr, srv_seal, nObjID));
		} else if (rule_arg.indexOf("jiange") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
			int len = temp.indexOf(",");
			String jStr = rule_arg.substring(rule_arg.indexOf("jiange") - 1,
					rule_arg.indexOf("jiange") + len);
			rule_arg = rule_arg.replaceFirst(jStr,
					jiangeStr(jStr, srv_seal, nObjID));
		}
		// byte b[] = rule_arg.getBytes();
		// //将规则信息保存到指定目录
		// FileOutputStream ou = new FileOutputStream(new
		// File(bpath+"doc\\rules\\"+rule_no+".txt"));
		// ou.write(b);
		// ou.close();
		// logger.info("写入规则成功！");
		// }else{
		// File fe = new File(bpath+"doc\\rules\\"+rule_no+".txt");
		// InputStream is=null;
		// try {
		// is = new FileInputStream(fe);
		// byte[] by = new byte[(int) fe.length()];
		// is.read(by);
		// rule_arg=new String (by);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// logger.error(e.getMessage());
		// }finally{
		// if(is!=null){
		// is.close();
		// }
		// }
		// }
		// File fs=new File(bpath+"doc\\seals\\"+rule_no+".txt");//判断印章数据文件是否存在
		String seal_data = "";
		// if(!fs.isFile()){//如果文件不存在
		seal_data = rule_srv.getSealDataByRule(rule_no);
		if (seal_data == null) {
			return 2;
		}
		// byte b[] = rule_arg.getBytes();
		// //将规则信息保存到指定目录
		// FileOutputStream ou = new FileOutputStream(new
		// File(bpath+"doc\\seals\\"+rule_no+".txt"));
		// ou.write(b);
		// ou.close();
		// logger.info("写入印章数据成功！");
		// }else{
		// File fe = new File(bpath+"doc\\seals\\"+rule_no+".txt");
		// InputStream is=null;
		// try {
		// is = new FileInputStream(fe);
		// byte[] by = new byte[(int) fe.length()];
		// is.read(by);
		// seal_data=new String (by);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// logger.error(e.getMessage());
		// }finally{
		// if(is!=null){
		// is.close();
		// }
		// }
		// }
		String sealName = "AUTO_ADD_SEAL_FROM_PATH";
		CertSrv certsrv = (CertSrv) getBean("CertService");
		GaiZhangRule objGZ = rule_srv.getRule(rule_no);
		String pfxPsd = "";
		String oriSealName = "";
		if (objGZ.getUse_cert() != 1) {// 先判断证书是否和规则绑定
			SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
			SealBody objseal = objSeal.getSealBodyID(objGZ.getSeal_id());
			if (objseal.getKey_sn().equals("")) {
				return 5;
			} else {// 取的印章和证书绑定的值
				Cert cert = certsrv.getObjByNo(objseal.getKey_sn());
				pfxPsd = "";
				oriSealName = cert.getCert_name();
				DesUtils des;
				try {
					des = new DesUtils("leemenz");
					pfxPsd = des.decrypt(cert.getCert_psd());
				} catch (Exception e) {
					logger.error(e.getMessage());
				}// 自定义密钥
			}
		} else {
			Cert cert = certsrv.getObjByNo(objGZ.getCert_no());
			pfxPsd = "";
			oriSealName = cert.getCert_name();
			DesUtils des;
			try {
				des = new DesUtils("leemenz");
				pfxPsd = des.decrypt(cert.getCert_psd());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}// 自定义密钥
		}
		int as = 1;// 返回值addSeal
		pagesData = rule_arg + seal_data;
		if (Constants.getProperty("sign_type").equals("1")) {
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
				logger.info(bpath + "doc/certs/" + oriSealName + ".pfx");
				File file = new File(bpath + "doc/certs/" + oriSealName
						+ ".pfx");
				if (!file.isFile()) {// 判断文件不存在
					Cert obj = new Cert();
					obj.setCert_detail(bpath + "doc/certs/" + oriSealName
							+ ".pfx");
					obj.setCert_name(oriSealName);
					CertSrv srv_file = (CertSrv) getBean("CertService");
					srv_file.dbToFile(obj);
				}
				oriSealName = bpath + "doc/certs/" + oriSealName + ".pfx;PWD:="
						+ pfxPsd;

			} else {
				oriSealName = Constants.getProperty("certPath");
			}
		} else {
			oriSealName = "SERVER_CERT:" + oriSealName;
			logger.info("nObjID:" + nObjID);
		}

		GaiZhangRule rule = rule_srv.getRule(rule_no);
		Integer rule_type = rule.getRule_type();
		String[] arg = rule.getArg_desc().split(",");
		// 多页骑缝章开始
		if (rule_type == 7 && pagesize > 25) {
			if (arg[5].equals("1")) {
				pagesData = "0,21500,5,3,20,";
				int ret = 0;
				if (pagesize < 16) {
					int c = 100 / pagesize;
					pagesData = "0,21500,5,3," + c + ",";
					for (int i = 1; i < pagesize; i++) {
						pagesData += i + ",";
					}
					ret = srv_seal.addSeal(nObjID, pagesData + seal_data,
							oriSealName, sealName);
				}
				if (pagesize > 15) {
					int flag = 1;
					for (int i = 2; i > 1; i++) {
						int temp = pagesize / i;
						if (temp < 16 && (pagesize - (i - 1) * 15) < 16) {
							flag = i;
							break;
						}
					}
					logger.info("pagesize:" + pagesize);
					logger.info("flag:" + flag);
					int a = pagesize / flag;
					logger.info("a:" + a);
					int b = pagesize % a;
					logger.info("b:" + b);
					if (b == 0) {
						for (int i = 0; i < flag; i++) {
							pagesData = i * a + ",21500,5,3," + (100 / a) + ",";
							for (int j = 1; j < a; j++) {
								pagesData += j + ",";
							}
							logger.info("a:" + pagesData);
							ret += srv_seal.addSeal(nObjID, pagesData
									+ seal_data, oriSealName, sealName);
							logger.info(ret);
						}
					} else {
						for (int i = 0; i < b; i++) {
							pagesData = i * (a + 1) + ",21500,5,3,"
									+ (100 / (a + 1)) + ",";
							for (int j = 1; j < (a + 1); j++) {
								pagesData += j + ",";
							}
							logger.info("b:" + pagesData);
							ret += srv_seal.addSeal(nObjID, pagesData
									+ seal_data, oriSealName, sealName);
							logger.info(ret);
						}
						for (int i = b; i < flag; i++) {
							pagesData = (i * a + b) + ",21500,5,3," + (100 / a)
									+ ",";
							for (int j = 1; j < a; j++) {
								pagesData += j + ",";
							}
							logger.info("c:" + pagesData);
							ret += srv_seal.addSeal(nObjID, pagesData
									+ seal_data, oriSealName, sealName);
							logger.info(ret);
						}
					}
					logger.info("总的加盖骑缝章成功的次数：" + ret);
					if (ret == flag) {
						logger.info("页数为" + pagesize + "的文档加盖骑缝章成功!" + " ret:"
								+ ret + " flag:" + flag);
					} else {
						logger.info("页数为" + pagesize + "的文档加盖骑缝章失败!" + " ret:"
								+ ret + " flag:" + flag);
					}
				}

			} else {
				logger.info("pagesData:" + pagesData);
				// logger.info("oriSealName:" + oriSealName);
				as = srv_seal.addSeal(nObjID, pagesData, oriSealName, sealName);
				logger.info("addSeal:" + as);
				if (as != 1) {
					srv_seal.saveFile(nObjID, "", "");
					return as;
				}
			}

		} // 多页骑缝章结束
		else {
			if (!rule_arg.equals("")) {
				// logger.info("pagesData:" + pagesData);
				logger.info("oriSealName:" + oriSealName);
				as = srv_seal.addSeal(nObjID, pagesData, oriSealName, sealName);
				logger.info("addSeal:" + as);
				if (as != 1) {
					srv_seal.saveFile(nObjID, "", "");
					return as;
				}
			}
		}
		return as;
	}

	public static int addSealDJ(SrvSealUtil srv_seal, String rule_no,
			int nObjID, int pagesize) throws Exception {
		logger.info("rule_no:" + rule_no);
		GaiZhangRuleSrvImpl rule_srv = (GaiZhangRuleSrvImpl) getBean("gaiZhangRuleService");
		SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
		String bpath = bpath();
		GetRuleList getrule = (GetRuleList) getBean("objRuelList");
		logger.info("mapSize:" + getrule.getMap().size());
		if (getrule.getMap().size() <= 0) {
			int rs = getrule.GetRuleListP();
			if (rs == 5) {
				return 5;
			}
		}
		Map<String, GetRules> objmap = getrule.getMap();
		GetRules obj = objmap.get(rule_no);
		String cert_name = obj.getCert_name();
		String cert_pwd = obj.getCert_pwd();
		String seal_name = obj.getSeal_name();
		String rule_arg = obj.getRule_desc();
		Integer rule_type = obj.getRule_type();
		rule_arg = rule_srv.getRuleArgS(rule_type, rule_arg, pagesize);
		// logger.info("rule_arg:" + rule_arg);
		if (rule_arg.indexOf("end") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("end"));
			int len = temp.indexOf(",");
			String eStr = rule_arg.substring(rule_arg.indexOf("end"),
					rule_arg.indexOf("end") + len);
			rule_arg = rule_arg.replaceFirst(eStr,
					endStr(eStr, srv_seal, nObjID));
		} else if (rule_arg.indexOf("jiange") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
			int len = temp.indexOf(",");
			String jStr = rule_arg.substring(rule_arg.indexOf("jiange") - 1,
					rule_arg.indexOf("jiange") + len);
			rule_arg = rule_arg.replaceFirst(jStr,
					jiangeStr(jStr, srv_seal, nObjID));
		}
		String pagesData = "";
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			File files = new File(bpath + "doc\\seals\\" + seal_name + ".sel");
			if (!files.isFile()) {// 判断印章文件是否存在
				logger.info("文件不存在 ");
				SealBody objs = new SealBody();
				objs.setSeal_path(bpath + "doc\\seals\\" + seal_name + ".sel");
				objs.setSeal_name(seal_name);
				objSeal.dbToFile(objs);
			}
			pagesData = rule_arg + bpath + "doc\\seals\\" + seal_name + ".sel";
		} else {
			File files = new File(Constants.getProperty("saveseal") + "\\"
					+ seal_name + ".sel");
			if (!files.isFile()) {// 判断印章文件是否存在
				logger.info("文件不存在 ");
				SealBody objs = new SealBody();
				objs.setSeal_path(Constants.getProperty("saveseal") + "\\"
						+ seal_name + ".sel");
				objs.setSeal_name(seal_name);
				objSeal.dbToFile(objs);
			}
			pagesData = rule_arg + Constants.getProperty("saveseal") + "\\"
					+ seal_name + ".sel";
		}
		try {
			DesUtils des = new DesUtils();
			cert_pwd = des.decrypt(cert_pwd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}// 自定义密钥
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			if (Constants.getProperty("sign_type").equals("1")) {
				File file = new File(bpath + "doc\\certs\\" + cert_name
						+ ".pfx");
				if (!file.isFile()) {// 判断文件不存在
					logger.info("文件不存在 ");
					Cert objcert = new Cert();
					objcert.setCert_detail(bpath + "doc\\certs\\" + cert_name
							+ ".pfx");
					objcert.setCert_name(cert_name);
					CertSrv srv_file = (CertSrv) getBean("CertService");
					srv_file.dbToFile(objcert);
				}
				cert_name = bpath + "doc\\certs\\" + cert_name + ".pfx"
						+ ";PWD:=" + cert_pwd;
			} else if (Constants.getProperty("sign_type").equals("2")) {
				cert_name = "SERVER_CERT:" + cert_name;
			}
		} else {
			if (Constants.getProperty("sign_type").equals("1")) {
				File file = new File(Constants.getProperty("savecert") + "\\"
						+ cert_name + ".pfx");
				if (!file.isFile()) {// 判断文件不存在
					logger.info("文件不存在 ");
					Cert objcert = new Cert();
					objcert.setCert_detail(Constants.getProperty("savecert")
							+ "\\" + cert_name + ".pfx");
					objcert.setCert_name(cert_name);
					CertSrv srv_file = (CertSrv) getBean("CertService");
					srv_file.dbToFile(objcert);
				}
				cert_name = Constants.getProperty("savecert") + "\\"
						+ cert_name + ".pfx" + ";PWD:=" + cert_pwd;
			} else if (Constants.getProperty("sign_type").equals("2")) {
				cert_name = "SERVER_CERT:" + cert_name;
			}
		}
		String sealName = "AUTO_ADD_SEAL_FROM_PATH";

		if (rule_type == 7 && pagesize > 25) {
			String[] arg = rule_arg.split(",");
			// 多页骑缝章开始
			logger.info("rule_arg:" + rule_arg);
			logger.info("arg[5]:" + arg[5]);
			if (arg[5].equals("1")) {
				rule_arg = "0,21500,9,3,20,";
				int ret = 0;
				if (pagesize < 16) {
					int c = 100 / pagesize;
					rule_arg = "0,21500,9,3," + c + ",";
					for (int i = 1; i < pagesize; i++) {
						rule_arg += i + ",";
					}
					ret = srv_seal.addSeal(nObjID, rule_arg + bpath
							+ "doc\\seals\\" + seal_name + ".sel", cert_name,
							sealName);
				}
				if (pagesize > 15) {
					int flag = 1;
					for (int i = 2; i > 1; i++) {
						int temp = pagesize / i;
						if (temp < 16 && (pagesize - (i - 1) * 15) < 16) {
							flag = i;
							break;
						}
					}
					logger.info("pagesize:" + pagesize);
					logger.info("flag:" + flag);
					int a = pagesize / flag;
					logger.info("a:" + a);
					int b = pagesize % a;
					logger.info("b:" + b);
					if (b == 0) {
						for (int i = 0; i < flag; i++) {
							pagesData = i * a + ",21500,9,3," + (100 / a) + ",";
							for (int j = 1; j < a; j++) {
								pagesData += j + ",";
							}
							logger.info("a:" + pagesData);
							ret += srv_seal.addSeal(nObjID, pagesData + bpath
									+ "doc\\seals\\" + seal_name + ".sel",
									cert_name, sealName);
							logger.info(ret);
						}
					} else {
						for (int i = 0; i < b; i++) {
							pagesData = i * (a + 1) + ",21500,9,3,"
									+ (100 / (a + 1)) + ",";
							for (int j = 1; j < (a + 1); j++) {
								pagesData += j + ",";
							}
							logger.info("b:" + pagesData);
							ret += srv_seal.addSeal(nObjID, pagesData + bpath
									+ "doc\\seals\\" + seal_name + ".sel",
									cert_name, sealName);
							logger.info(ret);
						}
						for (int i = b; i < flag; i++) {
							pagesData = (i * a + b) + ",21500,9,3," + (100 / a)
									+ ",";
							for (int j = 1; j < a; j++) {
								pagesData += j + ",";
							}
							logger.info("c:" + pagesData);
							ret += srv_seal.addSeal(nObjID, pagesData + bpath
									+ "doc\\seals\\" + seal_name + ".sel",
									cert_name, sealName);
							logger.info(ret);
						}
					}
					logger.info("总的加盖骑缝章成功的次数：" + ret);
					if (ret == flag) {
						logger.info("页数为" + pagesize + "的文档加盖骑缝章成功!" + " ret:"
								+ ret + " flag:" + flag);
					} else {
						logger.info("页数为" + pagesize + "的文档加盖骑缝章失败!" + " ret:"
								+ ret + " flag:" + flag);
					}
				}

			} else {
				logger.info("pagesData:" + pagesData);
				// logger.info("oriSealName:" + oriSealName);
				int ret = srv_seal.addSeal(nObjID, pagesData, cert_name,
						sealName);
				logger.info("addSeal:" + ret);
				if (ret == 1) {
					logger.info("加盖印章成功！");
				} else {
					logger.info("加盖印章失败：" + ret);
				}
				return ret;
			}

		} else {
			logger.info("sign_type:" + Constants.getProperty("sign_type"));
			if (Constants.getProperty("sign_type").equals("1")) {
				logger.info("nObjID:" + nObjID);
				logger.info("pagesData:" + pagesData);
				// logger.info("cert_name:" + cert_name);
				int ret = srv_seal.addSeal(nObjID, pagesData, cert_name,
						sealName);
				logger.info("ret:" + ret);
				if (ret == 1) {
					logger.info("加盖印章成功！");
				} else {
					logger.info("加盖印章失败：" + ret);
				}
				return ret;
			} else {
				String cerNo = "1";
				int ret0 = srv_seal.setValue(nObjID, "SET_JIAMIJI_CERTNO",
						cerNo);
				String cerPath = bpath + "doc\\cer\\" + cerNo + ".cer";
				logger.info("cerPath:" + cerPath);
				int ret1 = srv_seal.setValue(nObjID, "SET_JIAMIJI_CERTPATH",
						cerPath);
				logger.info("ret0:" + ret0);
				logger.info("ret1:" + ret1);
				logger.info("pagesData:" + pagesData);
				// logger.info("cert_name:" + cert_name);
				logger.info("nObjID:" + nObjID);
				int ret = srv_seal.addSeal(nObjID, pagesData, "", sealName);
				logger.info("ret:" + ret);
				if (ret == 1) {
					logger.info("加盖印章成功！");
				} else {
					logger.info("加盖印章失败：" + ret);
				}
				return ret;
			}
		}
		return 1;
	}

	private static String endStr(String eStr, SrvSealUtil srv_seal, int nObjID) {
		int bgn = Integer.valueOf(eStr.substring(3));
		int end = srv_seal.getPageCount(nObjID);
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= end - bgn; i++) {
			sb.append(i).append(",");
		}
		if (sb.indexOf(",") != -1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.toString();
	}

	private static String jiangeStr(String jStr, SrvSealUtil srv_seal,
			int nObjID) {
		char c = jStr.charAt(0);
		int bgn = c == '2' ? 2 : 1;
		int jiange = c == '3' ? Integer.valueOf(jStr.substring(7)) : 2;
		int end = srv_seal.getPageCount(nObjID);
		StringBuffer sb = new StringBuffer();
		for (int i = jiange; i <= end - bgn; i = i + jiange) {
			sb.append(i).append(",");
		}
		if (sb.indexOf(",") != -1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.toString();
	}

	public static String addSealCODEBAR(String is_merger, XMLNote xml)
			throws Exception {
		String bpath = bpath();
		SrvSealUtil srv_seal = srv_seal();
		if (is_merger.equals("合")) {
			String file_no = xml.getValue("META_DATA.MERGER_NO");
			String CODEBAR_TYPE = xml.getValue("META_DATA.CODEBAR_TYPE");// 二维码类型1:p417,0:QR
			String filePath = bpath + "doc/" + file_no.split("\\.")[0] + ".pdf";
			String x_coordinate = xml.getValue("META_DATA.X_COORDINATE");// 偏移量左右
			String y_coordinate = xml.getValue("META_DATA.Y_COORDINATE");// 偏移量上下
			String codebar_data = xml.getValue("META_DATA.CODEBAR_DATA");// 二维码信息
			String rule_no = xml.getValue("META_DATA.RULE_NO");// 获取规则号
			int nObjID = srv_seal.openObj(filePath, 0, 0);
			logger.info("nObjID:" + nObjID);
			// int l = srv_seal.login(nObjID, 2, "donghang", "donghang");
			String userType = Constants.getProperty("userType");
			String userAccess = Constants.getProperty("userAccess");
			String userPwd = Constants.getProperty("userPwd");
			int l = srv_seal.login(nObjID, Integer.parseInt(userType),
					userAccess, userPwd);
			logger.info("login:" + l);
			String ewm = "";
			if (CODEBAR_TYPE.equals("1")) {
				if (rule_no.indexOf(",") != -1) {
					String[] rules = rule_no.split(",");
					for (String ruleId : rules) {
						ewm = addSealEWM_MS(ruleId, srv_seal, nObjID, xml,
								x_coordinate, y_coordinate, codebar_data);
					}
				} else {
					ewm = addSealEWM_MS(rule_no, srv_seal, nObjID, xml,
							x_coordinate, y_coordinate, codebar_data);
				}
				if (ewm != "加盖二维码成功") {
					return ewm;
				}
			} else if (CODEBAR_TYPE.equals("0")) {
				ewm = addSealQR(srv_seal, nObjID, xml, x_coordinate,
						y_coordinate, codebar_data);
				if (ewm != "加盖二维码成功") {
					return ewm;
				}
			}
			int s = srv_seal.saveFile(nObjID, filePath, "pdf", 0);
			logger.info("s:" + s);
			if (s != 1) {
				return "保存二维码失败";
			}
		} else {
			XMLNote fileList = xml.getByName("FILE_LIST");
			List<XMLNote> notes = fileList.getChilds();
			for (XMLNote file : notes) {// 二维码
				String file_no = file.getValue("FILE_NO");
				String CODEBAR_TYPE = file.getValue("CODEBAR_TYPE");// 二维码类型1:p417,0:QR
				String filePath = "";
				String x_coordinate = file.getValue("X_COORDINATE");// 偏移量左右
				String y_coordinate = file.getValue("Y_COORDINATE");// 偏移量上下
				String codebar_data = file.getValue("CODEBAR_DATA");// 二维码信息
				if (System.getProperty("os.name").toUpperCase()
						.indexOf("WINDOWS") != -1) {
					filePath = bpath + "doc/" + file_no.split("\\.")[0]
							+ ".pdf";
					logger.info("filePath:" + filePath);
					int nObjID = srv_seal.openObj(filePath, 0, 0);
					logger.info("nObjID:" + nObjID);
					// int l = srv_seal.login(nObjID, 2, "donghang",
					// "donghang");
					String userType = Constants.getProperty("userType");
					String userAccess = Constants.getProperty("userAccess");
					String userPwd = Constants.getProperty("userPwd");
					int l = srv_seal.login(nObjID, Integer.parseInt(userType),
							userAccess, userPwd);
					logger.info("login:" + l);
					String ewm = "";
					String rule_no = file.getValue("RULE_NO");
					if (CODEBAR_TYPE.equals("1")) {
						if (rule_no.indexOf(",") != -1) {
							String[] rules = rule_no.split(",");
							for (String ruleId : rules) {
								ewm = addSealEWM_MS(ruleId, srv_seal, nObjID,
										xml, x_coordinate, y_coordinate,
										codebar_data);
							}
						} else {
							ewm = addSealEWM_MS(rule_no, srv_seal, nObjID, xml,
									x_coordinate, y_coordinate, codebar_data);
						}
						if (ewm != "加盖二维码成功") {
							return ewm;
						}
					} else if (CODEBAR_TYPE.equals("0")) {
						ewm = addSealQR(srv_seal, nObjID, xml, x_coordinate,
								y_coordinate, codebar_data);
						if (ewm != "加盖二维码成功") {
							return ewm;
						}
					}
					int s = srv_seal.saveFile(nObjID, filePath, "pdf", 0);
					logger.info("s:" + s);
					if (s != 1) {
						return "保存二维码失败";
					}
				} else {
					filePath = Constants.getProperty("savepdf") + "/"
							+ file_no.split("\\.")[0] + ".pdf";
					int nObjID = srv_seal.openObj(filePath, 1);
					String userType = Constants.getProperty("userType");
					String userAccess = Constants.getProperty("userAccess");
					String userPwd = Constants.getProperty("userPwd");
					int l = srv_seal.login(nObjID, Integer.parseInt(userType),
							userAccess, userPwd);
				}
			}
		}
		return "加盖二维码成功";
	}

	public static String addSealEWM_MS(String rule_no, SrvSealUtil srv_seal,
			int nObjID, XMLNote xml, String x_coordinate, String y_coordinate,
			String coderData) throws Exception {
		String pagesData = "";
		String bpath = bpath();
		GaiZhangRuleSrvImpl rule_srv = (GaiZhangRuleSrvImpl) getBean("gaiZhangRuleService");
		CertSrv certsrv = (CertSrv) getBean("CertService");
		GaiZhangRule objGZ = rule_srv.getRule(rule_no);
		Cert cert = certsrv.getObjByNo(objGZ.getCert_no());
		String oriSealName = cert.getCert_name();
		DesUtils des;
		String certpwd = "";
		try {
			des = new DesUtils("leemenz");
			certpwd = des.decrypt(cert.getCert_psd());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}// 自定义密钥
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			File file = new File(bpath + "doc/certs/" + oriSealName + ".pfx");
			if (!file.isFile()) {// 判断文件不存在
				Cert obj = new Cert();
				obj.setCert_detail(bpath + "doc/certs/" + oriSealName + ".pfx");
				obj.setCert_name(oriSealName);
				CertSrv srv_file = (CertSrv) getBean("CertService");
				srv_file.dbToFile(obj);
			}
			oriSealName = bpath + "doc/certs/" + oriSealName + ".pfx;PWD:="
					+ certpwd;
			String sealName = "AUTO_ADD_SEAL_FROM_PATH";
			String coge = "DJ_DEFCODEPOS_SET";
			pagesData = "AUTO_ADD:0,500," + x_coordinate + "," + y_coordinate
					+ ",50," + coge + "" + ")|(2,";
			int b = srv_seal.addSeal(nObjID, pagesData + coderData,
					oriSealName, sealName);
			logger.info("b:" + b);
			if (b != 1) {
				return "加盖二维码失败，返回值：" + b;
			}
		}
		return "加盖二维码成功";
	}

	public static String addSealQR(SrvSealUtil srv_seal, int nObjID,
			XMLNote xml, String x_coordinate, String y_coordinate,
			String coderData) throws Exception {
		String sealName = "AUTO_ADD_SEAL_FROM_PATH";
		String coge = "DJ_DEFCODEPOS_SET";
		String pagesData = "AUTO_ADD:0,500," + x_coordinate + ","
				+ y_coordinate + ",200," + coge + "" + ")|(5,";
		logger.info("pagesData:" + pagesData);
		int set = srv_seal.setValue(nObjID, "PREDEF_BARCODE_SCALE", "200");
		logger.info("set:" + set);
		// coderData = new String(coderData.getBytes("gb2312"));
		int b = srv_seal.addSeal(nObjID, pagesData + coderData, "", sealName);
		logger.info("b:" + b);
		if (b != 1) {
			return "加盖二维码失败，返回值：" + b;
		}
		return "加盖二维码成功";
	}

	public static int getRuleInfo(String xmlStr) throws Exception {
		GaiZhangRuleSrvImpl rule_srv = (GaiZhangRuleSrvImpl) getBean("gaiZhangRuleService");
		XMLNote xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		XMLNote fileList = xml.getByName("FILE_LIST");
		List<XMLNote> notes = fileList.getChilds();
		String bpath = bpath();
		SrvSealUtil srv_seal = srv_seal();
		for (XMLNote file : notes) {
			String rule_no = file.getValue("RULE_NO");
			String file_no = file.getValue("FILE_NO");
			String filePath = bpath + "doc/" + file_no.split("\\.")[0] + ".pdf";
			int nObjID = srv_seal.openObj(filePath, 0, 0);
			int sr = rule_srv.getRuleInfo(srv_seal, nObjID, rule_no);
			return sr;
		}
		return 0;
	}

	public static String getFaxXml(String ReceiverFax, String file_path,
			String file_name) throws Exception {
		String basepdf64 = "";
		File fe = new File(file_path);
		InputStream is;
		is = new FileInputStream(fe);
		byte[] by = new byte[(int) fe.length()];
		is.read(by);// 读取数组里的数据
		// String pdfString=new String (by);
		basepdf64 = Base64.encodeToString(by);
		// basepdf64=new sun.misc.BASE64Encoder().encode(by);
		is.close();
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		UUID uuid3 = UUID.randomUUID();
		StringBuffer s1 = new StringBuffer();
		s1.append("<?xml version=\"1.0\" standalone=\"yes\"?>").append("\r\n");
		s1.append("<DsSendFax>").append("\r\n");
		s1.append("<FaxSendList>").append("\r\n");
		s1.append("<Symbol>");
		s1.append(uuid1);
		s1.append("</Symbol>").append("\r\n");
		s1.append("<SenderType>");
		s1.append("2");
		s1.append("</SenderType>").append("\r\n");
		s1.append("<CoverSubject>");
		s1.append("TestSubject");
		s1.append("</CoverSubject>").append("\r\n");
		s1.append("<CoverContent>");
		s1.append("TestContend");
		s1.append("</CoverContent>").append("\r\n");
		s1.append("<bAddCoverPage>");
		s1.append("false");
		s1.append("</bAddCoverPage>").append("\r\n");
		s1.append("<SendRetryTimes>");
		s1.append("0");
		s1.append("</SendRetryTimes>").append("\r\n");
		s1.append("<SendRetryInterval>");
		s1.append("0");
		s1.append("</SendRetryInterval>").append("\r\n");
		s1.append("<Priority>");
		s1.append("2");
		s1.append("</Priority>").append("\r\n");
		s1.append("<FaxSize>");
		s1.append("3072");
		s1.append("</FaxSize>").append("\r\n");
		s1.append("<bPrinted>");
		s1.append("false");
		s1.append("</bPrinted>").append("\r\n");
		s1.append("<FaxQuality>");
		s1.append("1");
		s1.append("</FaxQuality>").append("\r\n");
		s1.append("<FaxSn>");
		s1.append("faxsn");
		s1.append("</FaxSn>").append("\r\n");
		s1.append("</FaxSendList>").append("\r\n");
		s1.append("<FaxSubSendList>").append("\r\n");
		s1.append("<Symbol>");
		s1.append(uuid2);
		s1.append("</Symbol>").append("\r\n");
		s1.append("<SendFaxListID>");
		s1.append(uuid1);
		s1.append("</SendFaxListID>").append("\r\n");
		s1.append("<SendStatus>");
		s1.append("0");
		s1.append("</SendStatus>").append("\r\n");
		s1.append("<ReceiverType>");
		s1.append("0");
		s1.append("</ReceiverType>").append("\r\n");
		s1.append("<Receiver>");
		s1.append("test1");
		s1.append("</Receiver>").append("\r\n");
		s1.append("<ReceiverFax>");
		s1.append(ReceiverFax);
		s1.append("</ReceiverFax>").append("\r\n");
		s1.append("<ReceiverCompany>");
		s1.append("company");
		s1.append("</ReceiverCompany>").append("\r\n");
		s1.append("<ReceiverPhone>");
		s1.append("67846868");
		s1.append("</ReceiverPhone>").append("\r\n");
		s1.append("<Mobile>");
		s1.append("13698474956");
		s1.append("</Mobile>").append("\r\n");
		s1.append("<Email>");
		s1.append("test@test.com");
		s1.append("</Email>").append("\r\n");
		s1.append("<bPrinted>");
		s1.append("false");
		s1.append("</bPrinted>").append("\r\n");
		s1.append("</FaxSubSendList>").append("\r\n");
		s1.append("<FaxSendFileList>").append("\r\n");
		s1.append("<Symbol>");
		s1.append(uuid3);
		s1.append("</Symbol>").append("\r\n");
		s1.append("<SendFaxListID>");
		s1.append(uuid1);
		s1.append("</SendFaxListID>").append("\r\n");
		s1.append("<FaxShowName>");
		s1.append(file_name);
		s1.append("</FaxShowName>").append("\r\n");
		s1.append("<FaxFileName>");
		s1.append(file_name);
		s1.append("</FaxFileName>").append("\r\n");
		s1.append("<FaxFile>");
		s1.append(basepdf64);
		s1.append("</FaxFile>").append("\r\n");
		s1.append("<FaxFileType>");
		s1.append("application/pdf");
		s1.append("</FaxFileType>").append("\r\n");
		s1.append("<FaxFileSize>");
		s1.append("1024");
		s1.append("</FaxFileSize>").append("\r\n");
		s1.append("<FaxFileExtend>");
		s1.append("pdf");
		s1.append("</FaxFileExtend>").append("\r\n");
		s1.append("<FaxPrinted>");
		s1.append("false");
		s1.append("</FaxPrinted>").append("\r\n");
		s1.append("<FaxFilePages>");
		s1.append("1");
		s1.append("</FaxFilePages>").append("\r\n");
		s1.append("</FaxSendFileList>").append("\r\n");
		s1.append("</DsSendFax>").append("\r\n");
		return s1.toString();
	}
	
	
	
	

	/**
	 * 文件签章&转换为jpg图片，并上传到ce系统
	 * 
	 * @param fileData
	 *            文件字节数组
	 * @param cepo
	 *            cepo对象
	 * @return -5 印章未绑定证书 ；-2 该部门未设置印章；-4签章失败,0转换图片异常；-3其他异常
	 * @throws Exception
	 */
	public static int addNSHSeal(byte[] fileData, TransCePo cepo)
			throws Exception {
		logger.info("进入addNSHSeal方法");
		NSHRecordService recordService = (NSHRecordService) getBean("nshRecordService");
		SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
		ISealBodyService seal_srv = (ISealBodyService) getBean("ISealBodyService");
		NSHRecordPo ny = new NSHRecordPo();
		DateFormat df = new SimpleDateFormat("yyyyMMdd"); 
		SysDepartment dept= deptService.getParentNo(cepo.getOrgUnit());// 根据机构号获取部门编号、父部门编号
		
		
		/* 获取三要素 */
		String valcode = "";// 生成的验证码
		String str =null;// 3DES加密后字符串
		int n = 0;// 请求次数
		NSHRecordPo checkValcode = null;// 验证生成的valcode是否存在
		NSHRecordPo checkValcode2=null;//验证传入的valcode是否存在
		String D_TRANDT = cepo.getD_TRANDT();// 交易日期
		String caseSeqID = cepo.getCaseSeqID();// 交易流水号
		String tranCode = cepo.getTranCode();// 交易码
		String valcodeOri = cepo.getStampCode();// 传入的验证码
		String orgUnit = cepo.getOrgUnit();// 机构号
	
		Timestamp valueOf = Timestamp.valueOf(D_TRANDT);
		String format = df.format(valueOf);// 日期转成yyyyMMdd格式

		DeptForm dtForm = recordService.getDeptNameByOrgUnit(orgUnit);// 根据机构号查找机构名称
		
		logger.info(caseSeqID+"无纸接口待加密数据：" + caseSeqID + tranCode + format);// 组成顺序与有纸化一致，否则相同三要素生成不同验证码
		logger.info(caseSeqID+"开始进行签章操作......");
		String sealName = "";
		String sealType = "";// 印章类型
		String certName = "";
		String certPwd = "";
		String deptNo = "";
		String deptName = "";
		String dateStr = df.format(System.currentTimeMillis());
		String ceImageStr = "";
		UUID uuid = UUID.randomUUID();
		
//		String parent_No=dept.getDept_parent();//取得父部门编号
//		SysDepartment byparent_No=deptService.getDeptNo(parent_No);//根据父部门编号向上查询信息		
		
//		while(!byparent_No.getDept_parent().equals(Constants.UNIT_DEPT_NO)){//如果父部门编号不是最上级继续按查出来的父部门编号查询
//			byparent_No=deptService.getDeptNo(byparent_No.getDept_parent());//父部门编号作为部门编号查询
//		}
//		if ("01".equals(byparent_No.getBank_dept())) {// 村镇银行不生成验证码(核心机构号测试和生产统一)
		
		/*修改村行印章为椭圆形并动态添加机构名称和验证码*/
		str = new ThreeDesHelper().encrypt(caseSeqID + tranCode + format);
		valcode = new MD5Helper().encode8BitBySalt(str, "gznsh").toUpperCase();// 生成验证码
		logger.info(caseSeqID + "无纸接口开始生成的验证码:" + valcode);

		checkValcode = recordService.checkValcode(valcode);// 验证是否为旧三要素
		if (checkValcode != null && !"".equals(checkValcode)) {// 旧三要素
			n = (Integer) checkValcode.getRequirednum();// 非第一次请求返回最大请求次数
			logger.info(caseSeqID + "无纸接口验证码:" + valcode + "已存在，非第一次请求！！！");
		} else {// 新三要素
			if (valcodeOri == null || "".equals(valcodeOri)
					|| "null".equals(valcodeOri)) {// 如果没有传验证码则表示业务开始,以后同一笔业务使用同一个验证码时不再生成VerfNo(屏蔽可能传入验证码的值为"null")
				ny.setRequirednum(1);// 初始请求打印次数为1
				n = 1;// 第一次请求返回次数为1
				logger.info(caseSeqID + "无纸接口新三要素未传入验证码,新生成:" + valcode);
			} else {// 传入了验证码
				logger.info(caseSeqID + "无纸接口新三要素传入验证码:" + valcodeOri);
				checkValcode2 = recordService.checkValcode(valcodeOri
						.toUpperCase());// 传入了valcode验证其是否存在
				if (checkValcode2 != null && !"".equals(checkValcode2)) {// 限制查询范围仅在原始表
					NSHRecordPo requiredNum = recordService
							.getMaxRequired_printNum(valcodeOri.toUpperCase());// 以传入valcode为准获取请求次数
					n = (Integer) requiredNum.getRequirednum();// 非第一次请求返回最大请求次数
					n += 1;// 递增1
					valcode = valcodeOri.toUpperCase();// 更新验证码(以传入的为准)
				} else {
					logger.info(caseSeqID + "无纸接口传入的验证码:" + valcodeOri
							+ ",不存在,请核对是否输入正确！");
				}
			}
		}
//		} else {
//			n=0;//除总行外的村行请求次数默认为0
//			logger.info(caseSeqID+"所属银行核心机构号: " + byparent_No.getBank_dept() + ",村镇银行不生成验证码!");			
//		}				
		
		String folder = saveBasePath + "/" + dateStr;
		File dirFile = new File(folder);
		if (!dirFile.exists()) {
			logger.info("addNSHSeal,创建存放凭证文件文件夹，folder=" + folder);
			dirFile.mkdirs();
		}
		String imageSavePath = folder + "/" + cepo.getCaseSeqID() + uuid;
		String fileSavePath = folder + "/" + cepo.getCaseSeqID() + uuid+ ".pdf";
		String ruleInfo = "AUTO_ADD:0,-1,16000,0,255," + cepo.getSealFont()+ ")|(8,";
		int allRet = 0;
		int ret = 0;
		int nObjID = -1;
		int imgRet = 0;
		int fileRet = 0;

		SrvSealUtil srv_seal = srv_seal();
		List<SealBody> seals = null;
		try {
			seals = seal_srv.showSealBodyByDeptNo2(dept.getDept_no());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("报错1：" + e.getMessage());
			allRet = -2;
			addSeallogs(cepo, Integer.valueOf(allRet), sealName, sealType,deptNo, deptName);
			return allRet;
		}
		
		logger.info("sealNum::" + seals.size());
		if (seals != null && seals.size() != 0) {
			// SealBody seal = seals.get(0);// 每个部门允许有一个印章
			for (SealBody seal : seals) {
				int sealNum = 0;			
				if (seal.getSeal_type().equals("公章-法人章")) {
					logger.info("无纸化交易签章，印章类型：" + seal.getSeal_type());
					sealName = seal.getSeal_name();
					sealType = seal.getSeal_type();	
					deptNo = seal.getDept_no();
					deptName = deptService.getDeptNo(deptNo).getDept_name();
					CertSrv certsrv = (CertSrv) getBean("CertService");
					Cert cert = certsrv.getObjByNo(seal.getKey_sn());
					if (cert == null) {
						allRet = -5;
						addSeallogs(cepo, allRet, sealName, sealType, deptNo,
								deptName);
						return allRet; // 服务器中没有证书
					}
					certName = cert.getCert_name();
					try {
						DesUtils des = new DesUtils();
						certPwd = des.decrypt(cert.getCert_psd());
					} catch (Exception e) {
						logger.error("报错3：" + e.getMessage());
					}// 自定义密钥

				} else {
					sealNum++;
				}
				if (sealNum == seals.size()) {
					allRet = -2;
					seal.setDept_no("");
					addSeallogs(cepo, Integer.valueOf(allRet), sealName,
							sealType, deptNo, deptName);
					return allRet;
				}						
			}
		} else {
			allRet = -2;
			addSeallogs(cepo, Integer.valueOf(allRet), sealName, sealType,
					deptNo, deptName);
			return allRet;
		}
		try {
			nObjID = srv_seal.openData(fileData);
			logger.info(cepo.getCaseSeqID()+"nObjId nosign pdf:" + nObjID);
			/************************村镇银行需求新增-start****************************/
			UnitAndLevelVo vo = ud.getUnitAndLevel(orgUnit);//查询机构所属级别
			/******************************end***************************************/
			if (nObjID > 0) {// 打开文档成功
				int nLoginType = 2;
				long startTime=0;
				long endTime=0;
				if (System.getProperty("os.name").toUpperCase()
						.indexOf("WINDOWS") != -1) {// windows系统盖章
					
					/* 印章上添加验证码 和机构号 */
					int ss1 = srv_seal.setDocProperty(nObjID, "valcode", valcode);
					logger.info(cepo.getCaseSeqID()+"添加验证码:"+valcode+"返回值:" + ss1);
					
					/***********村镇银行增加柜面系统电子印章的需求-start***********/
					if(vo!=null&&vo.getLevelno().equals(0)){
						logger.info(caseSeqID+" 机构号对应印章不需要动态添加机构名称");
					}else{						
						int ss2 = srv_seal.setDocProperty(nObjID, "orgUnitName", dtForm.getDept_name());
						logger.info(cepo.getCaseSeqID()+"添加机构名称:"+dtForm.getDept_name()+",返回值:" + ss2);
					}
					/***************************end***************************/
					
					srv_seal.login(nObjID, nLoginType, "nsh", "1");
					ret = addSealdesc(srv_seal, nObjID, ruleInfo, certName,sealName);//文件盖章
					if (ret == 1) {
						try {
							ceImageStr = imageSavePath + ".jpg";
							long jpgstartTime = System.currentTimeMillis();
							imgRet = srv_seal.getPageImg(nObjID, 0, 600,ceImageStr, "jpg");// 转换jpg图片并保存(未盖章图片-nObjID打开的是未盖章文档)
							long jpgendTime = System.currentTimeMillis();
							logger.info("imgRet1:" + imgRet);
							logger.info(cepo.getCaseSeqID() + "转图片耗时："+ (jpgendTime - jpgstartTime));
							fileRet = srv_seal.saveFile(nObjID, fileSavePath,"pdf", 0);	//保存并关闭pdf文件										
						} catch (Exception e) {
							e.printStackTrace();
							allRet = -1;// 图片转换异常
							addSeallogs(cepo, Integer.valueOf(allRet),
									sealName, sealType, deptNo, deptName);
							return allRet;
						}
					} else {
						srv_seal.saveFile(nObjID, "", "");			
						/*add by Hyg 20180109*/
						allRet = -4;// 盖章失败
						addSeallogs(cepo, Integer.valueOf(allRet),sealName, sealType, deptNo, deptName);
					}
					logger.info("windows seal result:" + ret+",pdf文件保存地址:"+fileSavePath);
				} else {// Linux系统盖章
					
					/* 印章上添加验证码 和机构号 */
					int ss1 = srv_seal.setDocProperty(nObjID, "valcode", valcode);
					logger.info(cepo.getCaseSeqID()+"添加验证码:"+valcode+"返回值:" + ss1);
					
					/***********村镇银行增加柜面系统电子印章的需求-start***********/
					if(vo!=null&&vo.getLevelno().equals(0)){
						logger.info(caseSeqID+" 机构号对应印章不需要动态添加机构名称");
					}else{						
						int ss2 = srv_seal.setDocProperty(nObjID, "orgUnitName", dtForm.getDept_name());
						logger.info(cepo.getCaseSeqID()+"添加机构名称:"+dtForm.getDept_name()+",返回值:" + ss2);
					}
					/***************************end***************************/
					
					startTime = System.currentTimeMillis();
					ret = addSealdescLinux(srv_seal, nObjID, fileSavePath,ruleInfo, certName, certPwd, sealName, cepo);//盖章
					endTime = System.currentTimeMillis();
					logger.info(cepo.getCaseSeqID() + "签章耗时："
							+ (endTime - startTime));
					if (ret == 1) {
						int pageCount = 0;
						int signObjID = srv_seal.openObj(fileSavePath, 0, 0);
						logger.info("openObj singPdf :" + signObjID);
						if (signObjID > 0) {
							try {
								pageCount = srv_seal.getPageCount(signObjID);
								logger.info("交易信息页数，pageCount:" + pageCount);
								String tmpImageSavePath = imageSavePath+ ".jpg";
								long jpgstartTime = System.currentTimeMillis();
								imgRet = srv_seal.getPageImg(signObjID, -1,
										400, "jpg", tmpImageSavePath);
								long jpgendTime = System.currentTimeMillis();
								logger.info(cepo.getCaseSeqID() + "转图片耗时："
										+ (jpgendTime - jpgstartTime));
								ceImageStr = tmpImageSavePath;
								logger.info("ceImageStr:" + ceImageStr);
								logger.info("imgRet2:" + imgRet);
								int s = srv_seal.saveFile(signObjID, "", "",0);
								if (s == 1) {
									logger.info("linux after seal-closeDoc success"
											+ s);
								} else {
									logger.info("linux after seal-closeDoc error");
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage());
								allRet = -1;// 图片转换异常
								addSeallogs(cepo, Integer.valueOf(allRet),sealName, sealType, deptNo, deptName);
								boolean alive = Thread.currentThread().isAlive();
								if (alive){
									if (signObjID != 0)
										srv_seal.saveFile(signObjID, "", "", 0);// 关闭文档				
								}
								return allRet;
							}
						}else{
							logger.info(cepo.getCaseSeqID()+"打开要转换图片文档失败！");//add by Hyg 20180109
						}
					} else {//盖章失败已关闭文档
						allRet = -4;// 盖章失败
						addSeallogs(cepo, Integer.valueOf(allRet), sealName,sealType, deptNo, deptName);
						return allRet;
					}
					logger.info(cepo.getCaseSeqID()+"linux seal result:" + ret);

				}

				if (imgRet == 1) {
					startTime = System.currentTimeMillis();
					String ceID = CEUploadUtil.uploadToCe(fileSavePath,ceImageStr, cepo);//上传到CE系统		
					endTime = System.currentTimeMillis();
					logger.info(cepo.getCaseSeqID() + "上传CE耗时："
							+ (endTime - startTime));
					if (ceID != null && !("").equals(ceID.trim())) {
						allRet = 1;
						if (checkValcode !=null&&!"".equals(checkValcode)) {//旧三要素
							logger.info(caseSeqID+"旧三要素,更新CEID到数据库...");//将CEID、voucherNo保存到数据库
							recordService.updateCEID(ceID, cepo.getVoucherNo(),cepo.getCaseSeqID());						
						} else {//新三要素
							if(valcodeOri!=null&&!"".equals(valcodeOri)&&!"null".equals(valcodeOri)){//传入了验证码,屏蔽柜面传入验证码值可能是"null"							
								if(checkValcode2 == null || "".equals(checkValcode2)){//传入的验证码不存在
									logger.info(caseSeqID+"无纸接口传入的验证码:"+valcodeOri+"不存在！");
									allRet=-999;//20171227新增
								}else{//传入验证码存在
									logger.info(caseSeqID+"无纸接口新三要素传入了验证码:"+valcodeOri+"保存数据...");
									addHSNRecord(cepo, ceID, valcode, n);
								}
							}else{//未传入验证码
								logger.info(caseSeqID+"无纸接口新三要素未传入验证码保存数据...");							
								addHSNRecord(cepo, ceID, valcode, n);				
							}
						}
					} else {
						allRet = -6;// ce上传失败（未获取到CEID）
					}
				} else {
					allRet = -7;// 图片转换失败
				}
			} else {//modify on 20180903
				logger.info(cepo.getCaseSeqID()+"打开文档失败,返回值:"+nObjID);
				allRet=-8;//自定义返回码
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			allRet = -3;
			addSeallogs(cepo, Integer.valueOf(allRet), sealName, sealType,deptNo, deptName);
			boolean alive = Thread.currentThread().isAlive();
			if (alive){
				if (nObjID !=0)
					srv_seal.saveFile(nObjID, "", "", 0);// 关闭文档				
			}
			return allRet;
		}
		logger.info(cepo.getCaseSeqID()+"allRet:" + allRet);
		addSeallogs(cepo, Integer.valueOf(allRet), sealName, sealType, deptNo,deptName);
		return allRet;
	}

	// 内部交易：不需盖章
	public static int addNBSeal(String fileName, byte[] fileData, TransCePo cepo)
			throws Exception {

		logger.info("进入addNBSeal方法");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// String bPath = "";
		String dateStr = sdf.format(System.currentTimeMillis());
		String ceImageStr = "";
		UUID uuid = UUID.randomUUID();

		String folder = saveBasePath + "/" + dateStr;
		File dirFile = new File(folder);
		if (!dirFile.exists()) {
			logger.info("addNSHSeal,创建存放凭证文件文件夹，folder=" + folder);
			dirFile.mkdirs();
		}
		String imageSavePath = folder + "/" + cepo.getCaseSeqID() + uuid;
		String fileSavePath = folder + "/" + cepo.getCaseSeqID() + uuid
				+ ".pdf";
		int allRet = 0;
		int ret = 0;
		int nObjID = -1;
		int imgRet = 0;
		int fileRet = 0;

		File oldFile = new File(fileName);
		File newFile = new File(fileSavePath);
		oldFile.renameTo(newFile);//重命名并移动到指定位置

		SrvSealUtil srv_seal = srv_seal();
		try {
			// nObjID = srv_seal.openData(fileData);
			// logger.info("nObjId nosign pdf:" + nObjID);
			// if (nObjID > 0) {
			int nLoginType = 2;
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
				int login = srv_seal.login(nObjID, nLoginType, "nsh", "1");
				imgRet = srv_seal.getPageImg(nObjID, 0, 1280, imageSavePath,
						"jpg");
				logger.info("imgRet1:" + imgRet);
				fileRet = srv_seal.saveFile(nObjID, fileSavePath, "pdf", 0);
				//srv_seal.saveFile(nObjID, "", "");
				logger.info("windows seal result:" + ret);
			} else {
				int pageCount = 0;
				int signObjID = srv_seal.openObj(fileSavePath, 0, 0);
				logger.info("openObj signPdf :" + signObjID);
				if (signObjID > 0) {
					try {
						pageCount = srv_seal.getPageCount(signObjID);
						logger.info("交易信息页数，pageCount:" + pageCount);
						String tmpImageSavePath = imageSavePath + ".jpg";
						long jpgstartTime = System.currentTimeMillis();
						imgRet = srv_seal.getPageImg(signObjID, -1, 400, "jpg",
								tmpImageSavePath);
						long jpgendTime = System.currentTimeMillis();
						logger.info(cepo.getCaseSeqID() + "转图片耗时："
								+ (jpgendTime - jpgstartTime));
						ceImageStr = tmpImageSavePath;
						logger.info("ceImageStr:" + ceImageStr);
						logger.info("imgRet2:" + imgRet);
						int s = srv_seal.saveFile(signObjID, "", "",0);
						logger.info("s1==" + s);
						if (s == 1) {
							logger.info("linux after seal-closeDoc success");
						} else {
							logger.info("linux after seal-closeDoc error");
						}
					} catch (Exception e) {
						int s = srv_seal.saveFile(signObjID, "", "",0);
						e.printStackTrace();
						logger.error(e.getMessage());
						allRet = -1;// 图片转换异常
						addSeallogs(cepo, Integer.valueOf(allRet), null, null,
								null, null);
						boolean alive = Thread.currentThread().isAlive();
						if (alive){
							if (signObjID != 0)
								srv_seal.saveFile(signObjID, "", "", 0);// 关闭文档				
						}
						return allRet;
					}
				} else {
//					int s = srv_seal.saveFile(signObjID, "", "");
//					logger.info("s2==" + s);
					logger.info("linux seal result:" + ret);
				}
			}
			if (imgRet == 1) {
				long startTime = System.currentTimeMillis();
				String ceID = CEUploadUtil.uploadToCe(fileSavePath, ceImageStr,
						cepo);
				long endTime = System.currentTimeMillis();
				logger.info(cepo.getCaseSeqID() + "上传CE耗时："
						+ (endTime - startTime));
				if (ceID != null && !("").equals(ceID.trim())) {
					logger.info(cepo.getCaseSeqID()+"内部交易不盖章保存无纸化单据...");
					addHSNRecord(cepo, ceID, "", 1);//内部交易默认请求次数为1,不生成,也不保存valcode
					allRet = 1;
				} else {
					allRet = -6;// ce上传失败（未获取到CEID）
				}
			} else {
				allRet = -7;// 图片转换失败(如打开文件失败会接着提示转换图片失败),外部交易不会这样
			}
			/*
			 * } else { allRet = -8;// 文档打开失败 }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			allRet = -3;
			addSeallogs(cepo, Integer.valueOf(allRet), null, null, null, null);
			return allRet;
		}
		logger.info(cepo.getCaseSeqID()+"allRet:" + allRet);
		addSeallogs(cepo, Integer.valueOf(allRet), null, null, null, null);
		return allRet;
	}

	private static void addSeallogs(TransCePo cepo, Integer allRet,
			String sealName, String sealType, String deptNo, String deptName) {
		UUID uuid = UUID.randomUUID();
		String file_name = (cepo.getCaseSeqID() + uuid).replaceAll("-", "")
				.trim();
		String caseSeqID = cepo.getCaseSeqID();
		String ip = "127.0.0.1";
		String seal_status = allRet.toString();
		String user_id = cepo.getTellerId();
		SealInterface.addSealLog(user_id, caseSeqID, ip, seal_status,
				file_name, sealName, sealType, deptNo, deptName);
	}

	private static String addHSNRecord(TransCePo cepo, String ceid,
			String valcode, int n) {
		NSHRecordService recordService = (NSHRecordService) getBean("nshRecordService");
		NSHRecordPo recordPo = new NSHRecordPo();
		DeptForm deptName = recordService.getDeptNameByOrgUnit(cepo.getOrgUnit());// 机构对象
		recordPo.setCaseseqid(cepo.getCaseSeqID());
		recordPo.setCeid(ceid);
		recordPo.setCuploadtime(new Timestamp(System.currentTimeMillis()));
		recordPo.setOrgunit(cepo.getOrgUnit());
		recordPo.setTellerid(cepo.getTellerId());
		recordPo.setTrancode(cepo.getTranCode());
		recordPo.setRemarks("");
		recordPo.setD_cuacno(cepo.getD_CUACNO());
		recordPo.setD_jioyje(cepo.getD_JIOYJE());
		recordPo.setVoucherno(cepo.getVoucherNo());//凭证号(旧)
		recordPo.setD_tranname(cepo.getD_tranName());//交易名称
		recordPo.setD_trandt(Timestamp.valueOf(cepo.getD_TRANDT()));//交易日期
		recordPo.setValcode(valcode);// 验证码，盖章则印章添加验证码，否则不添加
		recordPo.setStatus("0");// 状态码为0表示无纸化单据
		recordPo.setAuthtellerno("");// 授权柜员号,无纸化没有传此字段
		recordPo.setRequirednum(n);// 请求次数
		recordPo.setBp1(cepo.getZHPZHM());//业务凭证号(新)
		recordPo.setPrintnum(0);//无纸接口默认打印份数为0(此字段仅用于有纸打印)
				
		if(deptName!=null||!"".equals(deptName)){//根据机构号查询得到信息
			recordPo.setTranorgname(deptName.getDept_name());// 交易机构名称			
		}else{
			recordPo.setTranorgname("");
			logger.info(cepo.getCaseSeqID()+"此机构不存在!");
		}
		recordService.addRecord(recordPo);
		return "";
	}

	public static int addSealDJLinux(SrvSealUtil srv_seal, String rule_no,
			int nObjID, int pagesize, String savepath,TransCePo cpo) throws Exception {
		System.out.println("rule_no:" + rule_no);
		GaiZhangRuleSrvImpl rule_srv = (GaiZhangRuleSrvImpl) getBean("gaiZhangRuleService");
		SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
		String bpath = bpath();
		GetRuleList getrule = (GetRuleList) getBean("objRuelList");
		System.out.println("mapSize:" + getrule.getMap().size());
		if (getrule.getMap().size() <= 0) {
			int rs = getrule.GetRuleListP();
			if (rs == 5) {
				int s = srv_seal.saveFile(nObjID, "");
				// System.out.println("saveFile:" + s);
				System.out.println("关闭文档成功");
				if (s == 1) {
				} else {
					System.out.println("关闭文档失败");
				}
				return 5;
			}
		}
		String rule_arg = "";
		String cert_name = "";
		String cert_pwd = "";
		String seal_name = "";
		String seal_data = "";
		Integer rule_type = 0;
		try {
			Map<String, GetRules> objmap = getrule.getMap();
			GetRules obj = objmap.get(rule_no);
			if (obj == null) {
				int s = srv_seal.saveFile(nObjID, "");
				// System.out.println("saveFile:" + s);
				if (s == 1) {
					System.out.println("关闭文档成功");
				} else {
					System.out.println("关闭文档失败");
				}
				return 88;
			}
			cert_name = obj.getCert_name();
			cert_pwd = obj.getCert_pwd();
			// System.out.println("rule_no:"+rule_no);
			seal_name = obj.getSeal_name();
			// System.out.println("seal_name:"+seal_name);
			rule_arg = obj.getRule_desc();
			// System.out.println("rule_arg:"+rule_arg);
			rule_type = obj.getRule_type();
			seal_data = obj.getSeal_data();
			// System.out.println("cert_pwd:"+cert_pwd);
			// System.out.println("cert_name:"+cert_name);
			// rule_arg = rule_srv.getRuleArgS(rule_type, rule_arg, pagesize);
			rule_arg = rule_srv.getRuleArgLinux(rule_type, rule_arg, pagesize);
		} catch (Exception e) {
			int s = srv_seal.saveFile(nObjID, "");
			// System.out.println("saveFile:" + s);
			if (s == 1) {
				System.out.println("关闭文档成功");
			} else {
				System.out.println("关闭文档失败");
			}
			return 20;
		}
		// System.out.println("rule_arg:" + rule_arg);
		if (rule_arg.indexOf("end") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("end"));
			int len = temp.indexOf(",");
			String eStr = rule_arg.substring(rule_arg.indexOf("end"),
					rule_arg.indexOf("end") + len);
			rule_arg = rule_arg.replaceFirst(eStr,
					endStr(eStr, srv_seal, nObjID));
		} else if (rule_arg.indexOf("jiange") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
			int len = temp.indexOf(",");
			String jStr = rule_arg.substring(rule_arg.indexOf("jiange") - 1,
					rule_arg.indexOf("jiange") + len);
			rule_arg = rule_arg.replaceFirst(jStr,
					jiangeStr(jStr, srv_seal, nObjID));
		}
		String pagesData = "";
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			// File files=new File(bpath + "doc\\seals\\" + seal_name + ".sel");
			// if (!files.isFile()) {// 判断印章文件是否存在
			// System.out.println("文件不存在 ");
			// SealBody objs = new SealBody();
			// objs.setSeal_path(bpath + "doc\\seals\\" + seal_name + ".sel");
			// objs.setSeal_name(seal_name);
			// objSeal.dbToFile(objs);
			// }
			// pagesData = rule_arg + bpath + "doc\\seals\\" + seal_name
			// + ".sel";
			pagesData = rule_arg + seal_data;
		} else {
			File files = new File(Constants.getProperty("saveseal") + "/"
					+ seal_name + ".sel");
			if (!files.isFile()) {// 判断印章文件是否存在
				logger.info("印章文件不存在 ");
				System.out.println("印章文件不存在 ");
				SealBody objs = new SealBody();
				objs.setSeal_path(Constants.getProperty("saveseal") + "/"
						+ seal_name + ".sel");
				objs.setSeal_name(seal_name);
				objSeal.dbToFile(objs);
			}
			// String seal_data =
			// "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";
			// pagesData = rule_arg +
			// Constants.getProperty("saveseal")+"/"+seal_name + ".sel";
			// pagesData = "0,20000,4,20000,"+seal_data;
			pagesData = rule_arg + seal_data;
		}
		if (cert_pwd != "") {
			try {
				DesUtils des = new DesUtils();
				cert_pwd = des.decrypt(cert_pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}// 自定义密钥
		}
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			if (Constants.getProperty("sign_type").equals("1")) {
				File file = new File(bpath + "doc\\certs\\" + cert_name
						+ ".pfx");
				if (!file.isFile()) {// 判断文件不存在
					System.out.println("文件不存在 ");
					logger.info("文件不存在 ");
					Cert objcert = new Cert();
					objcert.setCert_detail(bpath + "doc\\certs\\" + cert_name
							+ ".pfx");
					objcert.setCert_name(cert_name);
					CertSrv srv_file = (CertSrv) getBean("CertService");
					srv_file.dbToFile(objcert);
				}
				cert_name = bpath + "doc\\certs\\" + cert_name + ".pfx"
						+ ";PWD:=" + cert_pwd;
			} else if (Constants.getProperty("sign_type").equals("0")) {
				cert_name = "SERVER_CERT:" + cert_name;
			}
		} else {
			if (Constants.getProperty("sign_type").equals("1")) {
				File file = new File(Constants.getProperty("savecert") + "/"
						+ cert_name + ".pfx");
				if (!file.isFile()) {// 判断文件不存在
					System.out.println("证书文件不存在 ");
					logger.info("证书文件不存在");
					Cert objcert = new Cert();
					objcert.setCert_detail(Constants.getProperty("savecert")
							+ "/" + cert_name + ".pfx");
					objcert.setCert_name(cert_name);
					CertSrv srv_file = (CertSrv) getBean("CertService");
					srv_file.dbToFile(objcert);
				}
				cert_name = Constants.getProperty("savecert") + "/" + cert_name
						+ ".pfx";
			}
		}
		System.out.println("sign_type:" + Constants.getProperty("sign_type"));
		if (Constants.getProperty("sign_type").equals("1")) {
			System.out.println("pagesData:" + pagesData);
			System.out.println("rule_type::" + rule_type);
			logger.info("pagesData:" + pagesData);
			logger.info("rule_type::" + rule_type);
			if (rule_type == 7 && pagesize > 25) {
				String[] arg = rule_arg.split(",");
				// 多页骑缝章开始
				System.out.println("rule_arg:" + rule_arg);
				System.out.println("arg[5]:" + arg[5]);
				if (arg[5].equals("1")) {
					pagesData = "";
					rule_arg = "0,21500,5,3,20,";
					int ret = 0;
					if (pagesize < 16) {
						int c = 100 / pagesize;
						rule_arg = "0,21500,5,3," + c + ",";
						for (int i = 1; i < pagesize; i++) {
							rule_arg += i + ",";
						}
						ret = srv_seal.addSeal(nObjID, rule_arg + seal_data,
								cert_name, "AUTO_ADD_SEAL_FROM_PATH");
						if (String.valueOf(ret).indexOf("-") != -1) {
							int s = srv_seal.saveFile(nObjID, "");
							// System.out.println("saveFile:" + s);
							if (s == 1) {
								System.out.println("关闭文档成功");
							} else {
								System.out.println("关闭文档失败");
							}
							return 0;
						}
					}
					if (pagesize > 15) {
						int flag = 1;
						for (int i = 2; i > 1; i++) {
							int temp = pagesize / i;
							if (temp < 16 && (pagesize - (i - 1) * 15) < 16) {
								flag = i;
								break;
							}
						}
						System.out.println("pagesize:" + pagesize);
						System.out.println("flag:" + flag);
						int a = pagesize / flag;
						System.out.println("a:" + a);
						int b = pagesize % a;
						System.out.println("b:" + b);
						if (b == 0) {
							for (int i = 0; i < flag; i++) {
								pagesData = i * a + ",21500,5,3," + (100 / a)
										+ ",";
								for (int j = 1; j < a; j++) {
									pagesData += j + ",";
								}

								ret += srv_seal.addSeal(nObjID, pagesData
										+ seal_data, cert_name,
										"AUTO_ADD_SEAL_FROM_PATH");
								System.out.println(ret);
								if (String.valueOf(ret).indexOf("-") != -1) {
									int s = srv_seal.saveFile(nObjID, "");
									// System.out.println("saveFile:" + s);
									if (s == 1) {
										System.out.println("关闭文档成功");
									} else {
										System.out.println("关闭文档失败");
									}
									return 0;
								}
							}
						} else {
							for (int i = 0; i < b; i++) {
								pagesData = i * (a + 1) + ",21500,5,3,"
										+ (100 / (a + 1)) + ",";
								for (int j = 1; j < (a + 1); j++) {
									pagesData += j + ",";
								}
								System.out.println("b:" + pagesData);
								ret += srv_seal.addSeal(nObjID, pagesData
										+ seal_data, cert_name,
										"AUTO_ADD_SEAL_FROM_PATH");
								System.out.println(ret);
								if (String.valueOf(ret).indexOf("-") != -1) {
									int s = srv_seal.saveFile(nObjID, "");
									// System.out.println("saveFile:" + s);
									if (s == 1) {
										System.out.println("关闭文档成功");
									} else {
										System.out.println("关闭文档失败");
									}
									return 0;
								}
							}
							for (int i = b; i < flag; i++) {
								pagesData = (i * a + b) + ",21500,5,3,"
										+ (100 / a) + ",";
								for (int j = 1; j < a; j++) {
									pagesData += j + ",";
								}
								System.out.println("c:" + pagesData);
								ret += srv_seal.addSeal(nObjID, pagesData
										+ seal_data, cert_name,
										"AUTO_ADD_SEAL_FROM_PATH");
								System.out.println(ret);
								if (String.valueOf(ret).indexOf("-") != -1) {
									int s = srv_seal.saveFile(nObjID, "");
									// System.out.println("saveFile:" + s);
									if (s == 1) {
										System.out.println("关闭文档成功");
									} else {
										System.out.println("关闭文档失败");
									}
									return 0;
								}
							}
						}
						System.out.println("总的加盖骑缝章成功的次数：" + ret);
						if (ret == flag) {
							System.out.println("页数为" + pagesize + "的文档加盖骑缝章成功!"
									+ " ret:" + ret + " flag:" + flag);
						} else {
							System.out.println("页数为" + pagesize + "的文档加盖骑缝章失败!"
									+ " ret:" + ret + " flag:" + flag);
						}
					}

				} else {
					// System.out.println("pagesData:" + pagesData);
					// System.out.println("cert_name:" + cert_name);
					int ret = srv_seal.addSeal(nObjID, pagesData, cert_name,
							"AUTO_ADD_SEAL_FROM_PATH");
					System.out.println("addSeal:" + ret);
					if (ret == 1) {
						System.out.println("加盖印章成功！");
					} else {
						int s = srv_seal.saveFile(nObjID, "");
						if (s == 1) {
							System.out.println("关闭文档成功");
						} else {
							System.out.println("关闭文档失败");
						}
						System.out.println(cpo.getCaseSeqID()+"加盖印章失败：" + ret);
					}
					return ret;
				}
			} else {
				int as = srv_seal.addSeal(nObjID, pagesData, "",
						"AUTO_ADD_SEAL_FROM_PATH");
				System.out.println("addSeal:" + as);
				if (as != 1) {
					int s = srv_seal.saveFile(nObjID, "");
					// System.out.println("saveFile:" + s);
					if (s == 1) {
						System.out.println("关闭文档成功");
					} else {
						System.out.println("关闭文档失败");
					}
					return as;
				}
			}
			byte[] pdfbs = srv_seal.getData(nObjID);
			System.out.println("pdfbs:" + pdfbs);
			// OutputStream os=new FileOutputStream(new
			// File(Constants.getProperty("savepdf")+"/test.pdf"));
			// os.write(pdfbs);
			// os.close();
			byte[] data = srv_seal.getSignSHAData(nObjID);
			System.out.println("data:" + data);
			// System.out.println("data:" + Base64.encodeToString(data));
			int pos = srv_seal.getSignPos(nObjID);
			System.out.println("pos:" + pos);
			// System.out.println("cert_pwd:" + cert_pwd);
			KeyStore store = getKeyStore(cert_name, cert_pwd);
			byte[] signedData = SignUtil.signP7Bytes(data, store, cert_pwd);
			System.out.println("signedData:" + signedData);
			byte[] savebs = chgBs(pdfbs, pos, signedData);
			System.out.println("savebs:" + savebs);
			FileUtil fu = new FileUtil();
			fu.save(savebs, savepath);
			int s = srv_seal.saveFile(nObjID, "");
			// System.out.println("saveFile:" + s);
			if (s == 1) {
				System.out.println("关闭文档成功");
			} else {
				System.out.println("关闭文档失败");
			}
		}
		return 1;
	}

	private static KeyStore getKeyStore(String pfxPath, String psd)
			throws Exception {
		// KeyStore store = KeyStore.getInstance("pkcs12");
		KeyStore store = KeyStore.getInstance("PKCS12",
				new BouncyCastleProvider());
		InputStream in = new FileInputStream(pfxPath);
		store.load(in, psd.toCharArray());// 加载证书库
		in.close();
		return store;
	}

	private static byte[] chgBs(byte[] pdfbs, int pos, byte[] singedData)
			throws Exception {
		byte[] insertBs = new byte[singedData.length * 2];
		for (int i = 0; i < singedData.length; i++) {
			byte[] temp = bt2bt(singedData[i]);
			insertBs[i * 2] = temp[0];
			insertBs[i * 2 + 1] = temp[1];
		}
		int length = insertBs.length;
		for (int i = pos; i < pos + length; i++) {
			pdfbs[i] = insertBs[i - pos];
		}
		return pdfbs;
	}

	private static byte[] bt2bt(byte b) throws Exception {
		byte[] rb = new byte[2];
		int i1 = (0xf0 & b) >> 4;
		int i2 = 0x0f & b;
		rb[0] = toByte(i1);
		rb[1] = toByte(i2);
		return rb;
	}

	private static byte toByte(int i) throws Exception {
		byte b;
		switch (i) {
		case 0:
			b = '0';
			break;
		case 1:
			b = '1';
			break;
		case 2:
			b = '2';
			break;
		case 3:
			b = '3';
			break;
		case 4:
			b = '4';
			break;
		case 5:
			b = '5';
			break;
		case 6:
			b = '6';
			break;
		case 7:
			b = '7';
			break;
		case 8:
			b = '8';
			break;
		case 9:
			b = '9';
			break;
		case 10:
			b = 'a';
			break;
		case 11:
			b = 'b';
			break;
		case 12:
			b = 'c';
			break;
		case 13:
			b = 'd';
			break;
		case 14:
			b = 'e';
			break;
		default:
			b = 'f';
			break;
		}
		return b;
	}

	/**
	 * linux盖章
	 * @param srv_seal
	 * @param nObjID
	 * @param savePath
	 * @param ruleInfo
	 * @param certname
	 * @param certpwd
	 * @param sealname
	 * @param cpo
	 * @return
	 * @throws Exception
	 */
	public static int addSealdescLinux(SrvSealUtil srv_seal, int nObjID,
			String savePath, String ruleInfo, String certname, String certpwd,
			String sealname, TransCePo cpo)
			throws Exception {

		SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
		File files = new File(Constants.getProperty("saveseal")
				+ File.separatorChar + sealname + ".sel");// 读取印章文件
		if (!files.exists()) {
			logger.info("印章文件不存在，重新生成");
			SealBody obj = new SealBody();
			obj.setSeal_path(Constants.getProperty("saveseal")
					+ File.separatorChar + sealname + ".sel");

			obj.setSeal_name(sealname);
			objSeal.dbToFile2(obj);//调用新增接口
		}

		certname = Constants.getProperty("savecert") + File.separatorChar
				+ certname + ".pfx";
		String pagesData = ruleInfo + Constants.getProperty("saveseal")
				+ File.separatorChar + sealname + ".sel";

		logger.info(cpo.getCaseSeqID()+"签章规则：" + ruleInfo);
		logger.info("pagesDate:" + pagesData);
		int as = srv_seal.addSeal(nObjID, pagesData, "",
				"AUTO_ADD_SEAL_FROM_PATH");
		logger.info(cpo.getCaseSeqID()+"addSeal返回结果：" + as);
		if (as == 0) {
			int s = srv_seal.saveFile(nObjID, "", "",0);//关闭文档 20171228
			if (s == 1) {
				logger.info("文档关闭成功");
			} else {
				logger.info(cpo.getCaseSeqID()+"文档关闭失败");
			}
			return as;
		} else if (as == -281) {
			srv_seal.saveFile(nObjID, "", "",0);//关闭文档
			return as;
		}
		byte[] pdfbs = srv_seal.getData(nObjID);
		byte[] data = srv_seal.getSignSHAData(nObjID);
		int pos = srv_seal.getSignPos(nObjID);
		KeyStore store = getKeyStore(certname, certpwd);
		byte[] singedData = SignUtil.signP7Bytes(data, store, certpwd);
		byte[] savebs = chgBs(pdfbs, pos, singedData);
		FileUtil fu = new FileUtil();
		fu.save(savebs, savePath);
		int s = srv_seal.saveFile(nObjID, "", "",0);//保存并关闭文档
		if (s == 1) {
			logger.info("linux after seal-closeDoc success0");
			return 1;
		} else {
			logger.info("linux after seal-closeDoc error0");
			return 0;
		}
	}

	/* 最高院盖章 */
	public static String haiGuanSealPdf(String xmlStr, String ip)
			throws Exception {
		logger.info("进入haiGuanSealPdf方法");
		XMLNote xml = null;
		TransCePo cpo = null;
		String fileNo = "";
		try {
			logger.info("xmlStr:" + xmlStr);
			xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		} catch (Exception e1) {
			e1.printStackTrace();
			// SealInterface.addHuizhiLog(cpo.getTellerId(),
			// cpo.getCaseSeqID(),null,"-4",null,cpo.getOrgUnit(),null,null);
			SealInterface.addHuizhiLog(null, null, null, "-4", null, null,
					null, null);
			return MyXml.sealPdfXml("xml", xml, ip, "1",
					"XML格式错误:" + e1.toString(), "error");
		}
		try {
			XMLNote metaNote = xml.getByName("META_DATA");

			if (metaNote == null || xml.countOfChild("META_DATA") > 1) {
				SealInterface.addHuizhiLog(null, null, null, "-5", null, null,
						null, null);
				return "缺少META_DATA节点";
			} else {
				cpo = new TransCePo();
				cpo.setCaseSeqID(metaNote.getValue("CETRANSQ"));
				cpo.setCeUserPS(Constants.getProperty("password"));
				cpo.setOrgUnit(metaNote.getValue("CEORGUNIT"));
				cpo.setSealFont("seal");
				cpo.setTellerId("ceadmin");
				cpo.setTranCode(metaNote.getValue("CETRANCODE"));
				cpo.setVoucherNo(metaNote.getValue("CEVOUCHERNO"));
				cpo.setVoucherType(metaNote.getValue("CEVOUCHERTYPE"));
			}

			String s = "";
			s = MyOper.haiGuanMakeFiles(xml);// 生成不合并的文档
			if (s.indexOf("成功") == -1) {
				return MyXml.sealPdfXml("合并数据", xml, ip, "1", s,
						cpo.getCaseSeqID());
			}
			fileNo = s.split(":")[1];
			String isSeal = metaNote.getValue("IS_SEAL");
			if (isSeal != null && isSeal.equals("true")) {
				//logger.info("系统:"+System.getProperty("os.name"));
				if (System.getProperty("os.name").toUpperCase()
						.indexOf("WINDOWS") != -1) {
					s = haiGuanAddSeal(xml);// windows pdf盖章
				} else {
					s = NSHAddSealLinux(xml, cpo);// linux pdf盖章
				}
				logger.info("NSHAddSealLinux盖章返回结果字符串：" + s);
				if (s.indexOf("盖章成功") == -1) {
					return MyXml.sealPdfXml("签章", xml, ip, "1", s,
							cpo.getCaseSeqID());// 失败
				}
			}else{
				logger.info("最高院【"+cpo.getCaseSeqID()+"】文档不盖章");
			}
			SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
			Date nowTime = new Date(System.currentTimeMillis());
			String nowTimeStr = sdfFileName.format(nowTime);
			String fileSavePath = saveBasePath + "/" + nowTimeStr + "/"+ fileNo;	
			logger.info("CE账号:"+cpo.getTellerId());
			
			
			// 上传文件到CE系统
			String ceID = CEUploadUtil.uploadToCe(fileSavePath, null, cpo);
			logger.info(cpo.getCaseSeqID()+"上传文件到CE系统返回值:"+ceID);
		
			// 通过tft上传文件
			String tftReturn = execCmd(fileNo);
			logger.info(cpo.getCaseSeqID()+"tft上传文件返回值:"+tftReturn);

			return MyXml.sealPdfXml("success", xml, ip, "0", s,cpo.getCaseSeqID());
		} catch (Exception e2) {
			e2.printStackTrace();
			return MyXml.sealPdfXml(e2.toString(), xml, ip, "0", "error",cpo.getCaseSeqID());
		}
	}

	/**
	 * 根据请求加盖印章, 最高院用于windows盖章
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @return 0为成功，1为失败
	 */
	public static String haiGuanAddSeal(XMLNote xml) throws Exception {
		logger.info("进入haiGuanAddSeal方法");
		String muluStr = "";
		SrvSealUtil srv_seal = srv_seal();
		String sealName = "";
		String certName = "";
		String certPwd = "";
		XMLNote fileList = xml.getByName("FILE_LIST");
		XMLNote metaNote = xml.getByName("META_DATA");
		TransCePo cpo = null;//仅在当前方法内有效
		
		if (metaNote == null || xml.countOfChild("META_DATA") > 1) {
			return "缺少META_DATA节点";
		} else {
			cpo = new TransCePo();
			cpo.setCaseSeqID(metaNote.getValue("CETRANSQ"));
			cpo.setCeUserPS(Constants.getProperty("password"));
			cpo.setOrgUnit(metaNote.getValue("CEORGUNIT"));
			cpo.setSealFont("!@#$");
			cpo.setTellerId("ceadmin");//ceadmin-原始账号/008005-测试账号
			cpo.setTranCode(metaNote.getValue("CETRANCODE"));
			cpo.setVoucherNo(metaNote.getValue("CEVOUCHERNO"));
			cpo.setVoucherType(metaNote.getValue("CEVOUCHERTYPE"));
		}

		if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
			return "缺少FILE_LIST节点";
		}

		SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
		ISealBodyService seal_srv = (ISealBodyService) getBean("ISealBodyService");

		// 查找文字自动签章定义规则(规则信息盖章)
		String ruleInfo = "AUTO_ADD:0,1,-10000,0,255," + cpo.getSealFont()+ ")|(8,";
		
		/*绝对坐标盖章*/
//		SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
//		String sealData = objSeal.getSealData("广州农村商业银行股份有限公司");//获取印章数据
//		String ruleInfo="0,39000,5,5,39000,"+sealData;//绝对坐标信息
		
		List<XMLNote> notes = fileList.getChilds();
		String open_path = "";
		String bpath = bpath();
		for (XMLNote note : notes) {
			String file_no = note.getValue("FILE_NO");
			String rule_type = note.getValue("RULE_TYPE");// 获取盖章类型（0:规则号盖章，1是规则信息盖章）
			String rule_no = note.getValue("RULE_NO");
			open_path = bpath + "doc"+File.separatorChar + file_no;
			int nObjID = 0;
			logger.info("打开要盖章文档路径:"+open_path);
			nObjID = srv_seal.openObj(open_path, 0, 0);
			System.out.println("MyOper-nObjID:" + nObjID);
			if (nObjID <= 0) {
				return "openObj失败：" + nObjID;
			}
			String userType = Constants.getProperty("userType");
			String userAccess = Constants.getProperty("userAccess");
			String userPwd = Constants.getProperty("userPwd");
			int l = srv_seal.login(nObjID, Integer.parseInt(userType),
					userAccess, userPwd);
			System.out.println("MyOper-login:" + l);
			if (l != 0) {
				int s = srv_seal.saveFile(nObjID, "", "pdf");
				if (s == 1) {
					System.out.println("关闭文档成功");
				} else {
					System.out.println("关闭文档失败");
				}
				return "login失败：" + l;
			}
			if (rule_type.equals("0")) {
				if (rule_no.indexOf(",") != -1) {
					String[] rule_str = rule_no.split(",");
					for (String ruleno : rule_str) {
						int pagesize = srv_seal.getPageCount(nObjID);
						int a = addSealDJ(srv_seal, ruleno, nObjID, pagesize);
						if (a != 1) {
							int s1 = srv_seal.saveFile(nObjID, "", "pdf");
							return "盖章失败，返回值是" + a;
						}
					}
					// String[] pathStr = getSavePath(file_no);
					// muluStr += pathStr[0] + ";";
					String save_path = bpath + file_no;
					int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
					//System.out.println("s:" + s);
					if (s != 1) {
//						int s1 = srv_seal.saveFile(nObjID, "", "");
//						if (s1 == 1) {
//							System.out.println("关闭文档成功");
//						} else {
//							System.out.println("关闭文档失败");
//						}
						return "盖章保存失败返回值是：" + s;
					}
				} else {
					int pagesize = srv_seal.getPageCount(nObjID);
					int a = addSealDJ(srv_seal, rule_no, nObjID, pagesize);
					System.out.println("a:" + a);
					if (a != 1) {
						int s1 = srv_seal.saveFile(nObjID, "", "pdf");
						return "盖章失败，返回值是" + a;
					}
					String[] pathStr = getSavePath(file_no);
					muluStr += pathStr[0] + ";";
					String save_path = pathStr[1];
					int s = srv_seal.saveFile(nObjID, save_path, "pdf", 0);
					System.out.println("s:" + s);
					if (s != 1) {
//						int s1 = srv_seal.saveFile(nObjID, "", "pdf");
//						if (s1 == 1) {
//							System.out.println("关闭文档成功");
//						} else {
//							System.out.println("关闭文档失败");
//						}
						return "盖章保存失败返回值是：" + s;
					}
				}
			} else if (rule_type.equals("3")) {
				// 农商行新增，按照组织机构加盖电子印章
				SysDepartment dept = null;
				SealBody seal = null;
				dept = deptService.showDeptByBankNo(cpo.getOrgUnit());
				
				List<SealBody> seals = seal_srv.showSealBodyByDeptNo2(dept.getDept_no());//仅查询出一级村行印章数据
				int sealType = Integer.parseInt(note.getValue("SEAL_TYPE"));
				String sealTypeStr = "";
				switch (sealType) {
				case 1:
					sealTypeStr = "普通公章";
					break;
				case 2:
					sealTypeStr = "冻结印章";
					break;
				case 3:
					sealTypeStr = "解冻印章";
				default:
					sealTypeStr = "普通公章";
					break;
				}
				for (SealBody sealBody : seals) {
					if (sealBody.getSeal_type().equals(sealTypeStr)) {
						seal = sealBody;
						sealName = sealBody.getSeal_name();
						break;
					}
				}
				
				if (seal == null) {
					return "印章未申请，请联系管理员";
				} else {
					CertSrv certsrv = (CertSrv) getBean("CertService");
					Cert cert = certsrv.getObjByNo(seal.getKey_sn());
					if (cert == null) {
						return "未绑定证书或证书已删除"; // 服务器中没有证书
					}
					certName = cert.getCert_name();
					try {
						DesUtils des = new DesUtils();
						certPwd = des.decrypt(cert.getCert_psd());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}// 自定义密钥
				}
				String fileSavePath = bpath + "doc"
						+ File.separatorChar + file_no;
				File fs = new File(open_path);
				InputStream in = new FileInputStream(fs);
				byte[] pdfData = new byte[(int) fs.length()];
				in.read(pdfData);
				in.close();
				nObjID = srv_seal.openData(pdfData);
				if (nObjID <= 0) {
					return "87";
				}
				int ret = addSealdesc(srv_seal, nObjID, ruleInfo, certName,sealName);
				if (ret != 1) {
					return "盖章失败，返回值是" + ret;
				} else {
					int s = srv_seal.saveFile(nObjID, fileSavePath, "pdf", 0);
					System.out.println("s:" + s);
					System.out.println("文档保存路径:"+fileSavePath);
					if (s != 1) {
//						int s1 = srv_seal.saveFile(nObjID, "", "pdf");
//						if (s1 == 1) {
//							System.out.println("关闭文档成功");
//							SealInterface.addHuizhiLog(cpo.getTellerId(),
//									cpo.getCaseSeqID(), file_no, "1",
//									sealTypeStr, cpo.getOrgUnit(),
//									dept.getDept_name(), sealName);//新增
//						} else {
//							System.out.println("关闭文档失败");
//						}
						return "盖章保存失败返回值是：" + s;
					}else{
						SealInterface.addHuizhiLog(cpo.getTellerId(),
								cpo.getCaseSeqID(), file_no, "1",
								sealTypeStr, cpo.getOrgUnit(),
								dept.getDept_name(), sealName);//新增
					}
				}
			} else {
				SealInterface.addHuizhiLog(cpo.getTellerId(),
						cpo.getCaseSeqID(), file_no, "-6", null,
						cpo.getOrgUnit(), null, sealName);//新增
				return "错误的RULE_TYPE";
			}
		}
		
		return "盖章成功" + muluStr;
	}

	/**
	 * 根据请求模板合成文档
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @return 0为成功，1为失败
	 */
	public static String haiGuanMakeFiles(XMLNote xml) throws Exception {
		logger.info("进入haiGuanMakeFiles方法");
		XMLNote fileList = xml.getByName("FILE_LIST");
		
		XMLNote meta_data=xml.getByName("META_DATA");//基础数据
		List<XMLNote> childs_meta = meta_data.getChilds();
		String tranSq=null;
		for(XMLNote xmlnt:childs_meta){
			tranSq=xmlnt.getValue("CETRANSQ");//获取交易流水号
			if(tranSq!=null)
			break;
		}
		
		String open_path = "";
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
		Date nowTime = new Date(System.currentTimeMillis());
		String nowTimeStr = sdfFileName.format(nowTime);
		logger.info("nowTimeStr:" + nowTimeStr);
		if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
			return "缺少FILE_LIST节点";
		}
		List<XMLNote> notes = fileList.getChilds();
		SrvSealUtil srv_seal = srv_seal();
		String bpath = bpath();
		String userType = Constants.getProperty("userType");

		// String userAccess = Constants.getProperty("userAccess");
		// String userPwd = Constants.getProperty("userPwd");
		String file_no = "";
		for (XMLNote note : notes) {
			String cj_type = note.getValue("CJ_TYPE");// 获取应用场景(data：模板合成，file读取url，base64是直接传递待改造文件的base64)
			file_no = note.getValue("FILE_NO");
			if (cj_type.equals("data")) {
				int nObjID = 0;
				String model_name = note.getValue("MODEL_NAME");// 获取模板名称
				if (model_name == "" || model_name == null) {
					logger.info("MODEL_NAME模板名称不能为空！");
					return "MODEL_NAME模板名称不能为空！";
				} else {
					ModelFileServiceImpl modelSer = (ModelFileServiceImpl) getBean("modelFileService");
					StringBuffer sb = new StringBuffer();
					sb.append("select ").append(ModelFileUtil.MODEL_ID);
					sb.append(" from ").append(
							ModelFileUtil.TABLE_NAME + " where "
									+ ModelFileUtil.MODEL_NAME + "='"
									+ model_name + "' and "
									+ ModelFileUtil.MODEL_STATE + "='1'");
					int ret = modelSer.showCount(sb.toString());
					System.out.println("mobanshu::" + ret);
					if (ret == 0) {
						logger.info("未找到模板或者模板未激活");
						return "未找到模板或者模板未激活";
					}
					String templatePath = "";
					if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {//windows系统获取报文数据
						nObjID = srv_seal.openObj("", 0, 0);
						System.out.println("MyOper-nObjID:" + nObjID);
						if (nObjID <= 0) {
							logger.info(tranSq+"打开文档失败，返回值是" + nObjID);
							return "打开文档失败，返回值是" + nObjID;
						}
						int login = srv_seal.login(nObjID,Integer.parseInt(userType), "HWSEALDEMOXX", "");
						System.out.println("MyOper-login:" + login);
						if (login != 0) {
							int s1 = srv_seal.saveFile(nObjID, "", "pdf",0);
							if (s1 == 1) {
								logger.info("关闭文档成功");
							} else {
								logger.info("关闭文档失败");
							}
							logger.info("登录失败，返回值是：" + login);
							return "登录失败，返回值是：" + login;
						}
						System.out.println("model_name:" + model_name);
						templatePath = bpath + "upload" +File.separatorChar+ model_name + ".aip";
						System.out.println("templatePath:" + templatePath);

						File files = new File(templatePath);
						if (!files.isFile()) {// 判断模板文件是否存在
							logger.info("模板不存在下载...");
							ModelFile obj = new ModelFile();
							obj.setContent_data("file_content");
							obj.setModel_name(model_name);
							modelSer.dbToFileS(obj, templatePath);
							logger.info("下载模板成功");
						}
						String busi_data = note.getValue("APP_DATA");// 键值对
						if (note.countOfChild("APP_DATA") > 1) {
							logger.info("缺少APP_DATA节点");
							return "缺少APP_DATA节点";
						}
						busi_data = busi_data == null ? XMLNote.toAipTplData(
								note.getByName("APP_DATA").getChilds().get(0),
								false).dataStr() : busi_data;// XML
						// busi_data=new
						// String(busi_data.getBytes("ISO8859-1"),"gb2312");
						String docData = "STRDATA:" + busi_data;
						System.out.println("模板数据   "+docData);

						int ap = srv_seal
								.addPage(nObjID, templatePath, docData);// SrvSealUtil.java
						System.out.println("ap:" + ap);
						if (ap == 0) {
							int s = srv_seal.saveFile(nObjID, "", "pdf",0);
							if (s == 1) {
								logger.info("关闭文档成功");
							} else {
								logger.info("关闭文档失败");
							}
							logger.info("addPage方法返回值是0及模板与数据合成失败");
							return "addPage方法返回值是0及模板与数据合成失败";
						}
						String savePath = bpath+ "doc"
								+ File.separatorChar + file_no;
						int s = srv_seal.saveFile(nObjID, savePath, "pdf", 0);
						System.out.println("保存文档路径:" + savePath);
						if (s == 0) {
							logger.info("saveFIle保存模板失败，返回值是0");
							return "saveFIle保存模板失败，返回值是0";
						} else {
						}
					} else {//Linux系统获取报文数据
						templatePath = Constants.getProperty("savepdf") + "/"
								+ model_name + ".aip";
						System.out.println("templatePath:" + templatePath);
						File files = new File(templatePath);
						if (!files.isFile()) {// 判断模板文件是否存在
							logger.info("不存在");
							ModelFile obj = new ModelFile();
							obj.setContent_data("file_content");
							obj.setModel_name(model_name);
							modelSer.dbToFileS(obj, templatePath);
							logger.info("下载模板成功");
						}
						File fs = new File(templatePath);
						InputStream in = new FileInputStream(fs);
						byte[] aipData = new byte[(int) fs.length()];
						in.read(aipData);
						in.close();
						
//						String fontPath = Constants.getProperty("fontspath");
//						if (fontPath.equals("1")) {
//							logger.info("已设置自定义字体库。。");
//						} else {
//							logger.info("没有设置自定义字体库。。");
//						}
//						// String fontPath="/home/ca/fonts/";
//						logger.info("fontPath:" + fontPath);
//						int setFont = srv_seal.setValue(0,
//								"SET_FONTFILES_PATH", fontPath);
//						logger.info("setFont:" + setFont);
						
						nObjID = srv_seal.openTempData(aipData);
						if (nObjID <= 0) {
							logger.info(tranSq+"打开文档失败，返回值是" + nObjID);
							return "打开文档失败，返回值是" + nObjID;
						}
						int login = srv_seal.login(nObjID, "user", 2, "");
						System.out.println("MyOper-login:" + login);
						if (login != 1) {
							int s1 = srv_seal.saveFile(nObjID, "","");
							if (s1 == 1) {
								logger.info("关闭文档成功");
							} else {
								logger.info("关闭文档失败");
							}
							logger.info("登录失败，返回值是：" + login);
							return "登录失败，返回值是：" + login;
						}
						String busi_data = note.getValue("APP_DATA");// 键值对
						if (note.countOfChild("APP_DATA") > 1) {
							logger.info("缺少APP_DATA节点");
							return "缺少APP_DATA节点";
						}
						busi_data = busi_data == null ? XMLNote.toAipTplData(
								note.getByName("APP_DATA").getChilds().get(0),
								false).dataStr() : busi_data;// XML
						String docData = "STRDATA:" + busi_data;
						logger.info("");
						int set = srv_seal.setValue(nObjID,
								"FORM_DATA_TXT_FORMAT", docData);
						logger.info("docData::" + docData);
						logger.info("set:" + set);
						if (set != 1) {
							int s1 = srv_seal.saveFile(nObjID, "","");
							if (s1 == 1) {
								logger.info("关闭文档成功");
							} else {
								logger.info("关闭文档失败");
							}
							logger.info("linux-setValue设置模板数据合成pdf返回值是：" + set);
							return "linux-setValue设置模板数据合成pdf返回值是：" + set;
						}
						String savePath = Constants.getProperty("savepdf")
								+ "/" + nowTimeStr + "/"
								+ file_no.split("\\.")[0] + ".pdf";
						logger.info("保存地址5：" + savePath);
						int s1 = srv_seal.saveFile(nObjID, savePath, "pdf",0);
						if (s1 != 1) {
							s1 = srv_seal.saveFile(nObjID, "","");
							if (s1 == 1) {
								logger.info("关闭文档成功");
							} else {
								logger.info("关闭文档失败");
							}
							logger.info("保存pdf失败，返回值是：" + s1);
							return "保存pdf失败，返回值是：" + s1;
						}
					}
				}
			} else if (cj_type.equals("file")) {
				if (System.getProperty("os.name").toUpperCase()
						.indexOf("WINDOWS") != -1) {
					open_path = bpath + "doc/" + file_no + ".pdf";
				} else {
					open_path = Constants.getProperty("savepdf") + "/"
							+ nowTimeStr + "/" + file_no + ".pdf";
					logger.info("保存地址3：" + open_path);
				}
				String file_path = note.getValue("FILE_PATH");
				// open_path = bpath + "doc/" + file_no + ".pdf";
				System.out.println("open_path:" + open_path);
				System.out.println("file_path:" + file_path);
				String path_name = file_path.substring(file_path
						.lastIndexOf("/") + 1);
				String url_path = file_path.substring(0,
						file_path.lastIndexOf("/"));
				System.out.println("url_path");
				URL url = new URL(url_path + "/"
						+ URLEncoder.encode(path_name, "UTF-8"));
				try {
					FileUtil fu = new FileUtil();
					InputStream is = url.openStream();
					fu.save(fu.getFileByte(is), open_path);
					is = null;
					is.close();
					logger.info("成功写入文件");
				} catch (Exception e) {
					logger.info("FILE_PATH错误，找不到指定文档,请检查！");
					return "FILE_PATH错误，找不到指定文档,请检查！";
				}
			} else {
				logger.info("CJ_TYPE错误，不支持的CJ_TYPE,请检查！");
				return "CJ_TYPE错误，不支持的CJ_TYPE,请检查！";
			}
		}
		logger.info("成功:" + file_no);
		return "成功:" + file_no;
	}

	/* 最高院用于linux盖章 */
	public static String NSHAddSealLinux(XMLNote xml, TransCePo cpo)
			throws Exception {
		logger.info("进入NSHAddSealLinux方法");
		String muluStr = "";
		SrvSealUtil srv_seal = srv_seal();
		String sealName = "";
		String certName = "";
		String certPwd = "";
		XMLNote fileList = xml.getByName("FILE_LIST");
		XMLNote metaNote = xml.getByName("META_DATA");

		if (fileList == null || xml.countOfChild("FILE_LIST") > 1) {
			return "缺少FILE_LIST节点";
		}

		SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
		ISealBodyService seal_srv = (ISealBodyService) getBean("ISealBodyService");

		// 查找文字自动签章定义规则
		String ruleInfo = "AUTO_ADD:0,1,10000,-6800,255," + cpo.getSealFont()+ ")|(8,";
		List<XMLNote> notes = fileList.getChilds();
		for (XMLNote note : notes) {
			String file_no = note.getValue("FILE_NO");
			String rule_type = note.getValue("RULE_TYPE");// 获取盖章类型（0:规则号盖章，1是规则信息盖章）
			String rule_no = note.getValue("RULE_NO");
			int nObjID = 0;
			SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
			Date nowTime = new Date(System.currentTimeMillis());
			String nowTimeStr = sdfFileName.format(nowTime);
			String open_path = Constants.getProperty("savepdf") + "//"
					+ nowTimeStr + "//" + file_no;
			if (rule_type.equals("0")) {
				if (rule_no.indexOf(",") != -1) {
					String[] rule_str = rule_no.split(",");
					for (String ruleno : rule_str) {
						File fs = new File(open_path);
						InputStream in = new FileInputStream(fs);
						byte[] pdfData = new byte[(int) fs.length()];
						in.read(pdfData);
						in.close();
						nObjID = srv_seal.openData(pdfData);
						if (nObjID <= 0) {
							return "87";
						}
						System.out.println("MyOper-nObjID:" + nObjID);
						int pagesize = srv_seal.getPageCount(nObjID);
						String[] pathStr = getSavePath(file_no);
						muluStr += pathStr[0] + ";";
						String save_path = pathStr[1];
						System.out.println("保存目录：" + save_path);
						logger.info(cpo.getCaseSeqID()+"保存目录：" + save_path);
						int a = addSealDJLinux(srv_seal, ruleno, nObjID,
								pagesize, save_path,cpo);//新增参数cpo 20180119 by Hyg
						if (a != 1) {
							return "盖章失败，返回值是" + a;
						}
					}
				} else {
					File fs = new File(open_path);
					System.out.println("open_path:" + open_path);
					logger.info(cpo.getCaseSeqID()+"open_path:" + open_path);
					InputStream in = new FileInputStream(fs);
					byte[] pdfData = new byte[(int) fs.length()];
					in.read(pdfData);
					in.close();
					nObjID = srv_seal.openData(pdfData);
					if (nObjID <= 0) {
						return "87";
					}
					System.out.println("MyOper-nObjID:" + nObjID);
					logger.info("MyOper-nObjID:" + nObjID);
					int pagesize = srv_seal.getPageCount(nObjID);
					String[] pathStr = getSavePath(file_no);
					muluStr += pathStr[0] + ";";
					String save_path = pathStr[1];
					int a = addSealDJLinux(srv_seal, rule_no, nObjID, pagesize,
							save_path,cpo);
					if (a != 1) {
						logger.info(cpo.getCaseSeqID()+"盖章失败，返回值是" + a);
						return "盖章失败，返回值是" + a;
					}
				}
			} else if (rule_type.equals("3")) {
				try {
					// 农商行新增，按照组织机构加盖电子印章
					SysDepartment dept = null;
					SealBody seal = null;
					dept = deptService.showDeptByBankNo(cpo.getOrgUnit());
					List<SealBody> seals = null;
					try {
						seals = seal_srv.showSealBodyByDeptNo2(dept.getDept_no());
					} catch (Exception e) {
						logger.error(e.getMessage());
						SealInterface.addHuizhiLog(cpo.getTellerId(),
								cpo.getCaseSeqID(), file_no, "-7", null,
								cpo.getOrgUnit(), null, sealName);
						logger.info(cpo.getCaseSeqID()+"数据表中无此行别:"+dept.getDept_no());
						return "数据表中无此行别";
					}
					int sealType = Integer.parseInt(note.getValue("SEAL_TYPE"));
					String sealTypeStr = "";
					Integer allRet = 1;
					switch (sealType) {
					case 1:
						sealTypeStr = "普通公章";
						break;
					case 2:
						sealTypeStr = "冻结印章";
						break;
					case 3:
						sealTypeStr = "解冻印章";
					default:
						sealTypeStr = "普通公章";
						break;
					}
					for (SealBody sealBody : seals) {
						if (sealBody.getSeal_type().equals(sealTypeStr)) {
							seal = sealBody;
							sealName = sealBody.getSeal_name();
							break;
						}
					}
					if (seal == null) {
						allRet = -1;
						SealInterface.addHuizhiLog(cpo.getTellerId(),
								cpo.getCaseSeqID(), file_no, allRet.toString(),
								sealTypeStr, cpo.getOrgUnit(),
								dept.getDept_name(), sealName);
						logger.info(cpo.getCaseSeqID()+"印章未申请，请联系管理员");
						return "印章未申请，请联系管理员";
					} else {
						CertSrv certsrv = (CertSrv) getBean("CertService");
						Cert cert = certsrv.getObjByNo(seal.getKey_sn());
						if (cert == null) {
							allRet = -2;
							SealInterface.addHuizhiLog(cpo.getTellerId(),
									cpo.getCaseSeqID(), file_no,
									allRet.toString(), sealTypeStr,
									cpo.getOrgUnit(), dept.getDept_name(),
									sealName);
							logger.info("未绑定证书或证书已删除");
							return "未绑定证书或证书已删除"; // 服务器中没有证书
						}
						certName = cert.getCert_name();
						try {
							DesUtils des = new DesUtils();
							certPwd = des.decrypt(cert.getCert_psd());
						} catch (Exception e) {
							logger.error(e.getMessage());
						}// 自定义密钥
					}
					String fileSavePath = saveBasePath + "/" + nowTimeStr + "/"
							+ file_no;
					File fs = new File(open_path);
					InputStream in = new FileInputStream(fs);
					byte[] pdfData = new byte[(int) fs.length()];
					in.read(pdfData);
					in.close();
					nObjID = srv_seal.openData(pdfData);
					// int no = srv_seal.openObj(fileSavePath, 0, 0);
					if (nObjID <= 0) {
						return "87";
					}
					System.out.println("nObjID==" + nObjID + ";fileSavePath=="
							+ fileSavePath + ";ruleInfo==" + ruleInfo
							+ ";certName==" + certName + ";certPwd==:"
							+ certPwd + ";sealName==" + sealName);
					int ret = addSealdescLinux(srv_seal, nObjID, fileSavePath,
							ruleInfo, certName, certPwd, sealName, cpo);// 最高院盖章不需要验证码和机构名称
					logger.info(cpo.getCaseSeqID()+"addSealdescLinux返回值为：" + ret);
					if (ret != 1) {
						logger.info(cpo.getCaseSeqID()+"1 盖章失败,返回值是：" + ret);
						allRet = -3;
						SealInterface.addHuizhiLog(cpo.getTellerId(),
								cpo.getCaseSeqID(), file_no, allRet.toString(),
								sealTypeStr, cpo.getOrgUnit(),
								dept.getDept_name(), sealName);
						return "盖章失败，返回值是" + ret;
					} else {
						logger.info("2 盖章成功");
						SealInterface.addHuizhiLog(cpo.getTellerId(),
								cpo.getCaseSeqID(), file_no, allRet.toString(),
								sealTypeStr, cpo.getOrgUnit(),
								dept.getDept_name(), sealName);
					}
				} catch (Exception e) {
					//add 20180124  原为空
					if (nObjID > 0)
						srv_seal.saveFile(nObjID, "", "", 0);//出现出错关闭已打开的文件
					logger.info(cpo.getCaseSeqID() + "加盖印章异常");
					e.printStackTrace();
				}
			} else {
				SealInterface.addHuizhiLog(cpo.getTellerId(),
						cpo.getCaseSeqID(), file_no, "-6", null,
						cpo.getOrgUnit(), null, sealName);
				logger.info("错误的RULE_TYPE");
				return "错误的RULE_TYPE";
			}
		}
		logger.info("盖章成功" + muluStr);
		return "盖章成功" + muluStr;
	}

	public static String[] getSavePath(String file_no) {
		String bpath = bpath();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String yearStr = Integer.toString(year);
		String monthStr = Integer.toString(month);
		String mululujing1 = bpath + "sealdoc//" + yearStr;
		File file1 = new File(mululujing1);
		// 如果文件夹不存在则创建
		if (!file1.exists() && !file1.isDirectory()) {
			System.out.println("//目录不存在");
			file1.mkdir();
		}
		String mululujing2 = bpath + "sealdoc//" + yearStr + "//" + monthStr;
		File file2 = new File(mululujing2);
		// 如果文件夹不存在则创建
		if (!file2.exists() && !file2.isDirectory()) {
			System.out.println("//目录不存在");
			file2.mkdir();
		}
		String save_path = mululujing2 + "//" + file_no + ".pdf";
		;
		System.out.println("getSavePath:save_path:" + save_path);
		String muluStr = yearStr + "_" + monthStr;
		String[] returnStr = new String[2];
		returnStr[0] = muluStr;
		returnStr[1] = save_path;
		return returnStr;
	}

	public static String execCmd(String fileName) throws IOException,InterruptedException {
		logger.info("已进入TFT上传方法execCmd");
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
		Date nowTime = new Date(System.currentTimeMillis());
		String nowTimeStr = sdfFileName.format(nowTime);
		String serverPath = Constants.getProperty("tft_serverPath");
		String tftClientStr = "tftclient -dup -r" + serverPath + fileName + " "
				+ nowTimeStr + "//" + fileName;
		logger.info(tftClientStr);
		String[] cmds = { "/bin/sh", "-c", tftClientStr };
		Process pro = Runtime.getRuntime().exec(cmds);
		InputStream in = pro.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = read.readLine()) != null) {
			System.out.println(line);
			sb.append(line);
		}
		pro.getOutputStream().close();
		int exitValue = pro.waitFor();
		System.out.println("exitVaue::" + exitValue);
		if (exitValue != 0) {
			pro.destroy();
			pro = null;
		}
		return sb.toString();
	}
}