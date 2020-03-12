package serv;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.appSystem.service.impl.AppSysServiceImpl;
import com.dj.seal.appSystem.web.form.AppSystemForm;
import com.dj.seal.log.service.impl.LogSysServiceImpl;
import com.dj.seal.organise.service.impl.UserService;
import com.dj.seal.util.Constants;
import com.dj.seal.util.encrypt.FileUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.sign.Base64;

import serv.base.RetData;
import serv.reqres.ModelResponse;
import srvSeal.SrvSealUtil;
import xmlUtil.xml.XMLNote;

public class MyXml {
	static Logger logger = LogManager.getLogger(MyXml.class.getName());
	/**
	 * 根据请求判断是否需要合并
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @throws Exception
	 * @throws StringIndexOutOfBoundsException
	 */
	public static String isMerger(XMLNote xml)
			throws StringIndexOutOfBoundsException, Exception {
		XMLNote metaData = xml.getByName("META_DATA");
		String is_merger = metaData.getValue("IS_MERGER");
		if (is_merger == null || metaData.countOfChild("IS_MERGER") > 1) {
			return "IS_MERGER:扩展信息出错-不包含(或同时存在多个)是否合并信息，请参照接口文档。";
		}
		if ("true".equals(is_merger))
			return "合";
		return "分";
	}

	/**
	 * 根据请求判断应用场景
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @throws Exception
	 * @throws StringIndexOutOfBoundsException
	 */
	public static String isCjtype(XMLNote xml)
			throws StringIndexOutOfBoundsException, Exception {
		XMLNote fileList = xml.getByName("FILE_LIST");
		List<XMLNote> notes = fileList.getChilds();
		String cy_type = "";
		for (XMLNote note : notes) {
			cy_type = note.getValue("CJ_TYPE");
			break;
		}
		if (cy_type.equals("data")) {
			return "模板合成";
		}
		return "pdf盖章";
	}

	/**
	 * 根据请求判断用户身份是否合法
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @return 请求用户身份
	 * @throws Exception
	 * @throws StringIndexOutOfBoundsException
	 */
	public static String isUserAllow(XMLNote xml)
			throws StringIndexOutOfBoundsException, Exception {
		XMLNote base_data = xml.getByName("BASE_DATA");
		String user_id = base_data.getValue("USER_ID");
		String user_pwd = base_data.getValue("USER_PSD");
		String server_id = base_data.getValue("SYS_ID");
		UserService userServ = (UserService) getBean("IUserService");
		int f_user = userServ.isExistUser(user_id);
		if (f_user == 0) {
			return "此USER_ID用户不存在，请检查！";
		}
		int f_pwd = userServ.isExistUserorPwd(user_id, user_pwd);
		if (f_pwd == 0) {
			return "此USER_PSD错误或不存在，请检查！";
		}
		int f_sys = userServ.isExistServer(server_id);
		if (f_sys == 0) {
			return "此应用系统SYS_ID错误或不存在，请检查！";
		}

		return "信息完整";
	}

	/**
	 * 
	 * 根据应用系统是否合法
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @return 请求用户身份
	 * @throws Exception
	 * @throws StringIndexOutOfBoundsException
	 */
	public static String isSysAllow(XMLNote xml)
			throws StringIndexOutOfBoundsException, Exception {
		XMLNote base_data = xml.getByName("BASE_DATA");
		String app_no = base_data.getValue("SYS_ID");
		String server_psw = base_data.getValue("SYS_PSW");
		AppSysServiceImpl sys_Serv = (AppSysServiceImpl) getBean("appSysService");
		AppSystemForm appSys=sys_Serv.getAppSystemByAPP_NO(app_no);
		if (appSys==null) {
			return "此应用系统SYS_ID错误或不存在，请检查！";
		}	
		if(appSys.getApp_pwd()!=null&&!appSys.getApp_pwd().equals(server_psw)){
			return "此应用系统SYS_PSW错误,请检查！";
		}
		return "信息完整";
	}

	/**
	 * 判断报文中的必填项是否正确
	 * 
	 * @param xmlStr
	 *            请求的xml字符串
	 * @return
	 * @throws Exception
	 * @throws StringIndexOutOfBoundsException
	 */
	public static String isInfoAble(XMLNote xml)
			throws StringIndexOutOfBoundsException, Exception {
		try {
			XMLNote baseData = xml.getByName("BASE_DATA");
			if (baseData == null) {
				return "BASE_DATA节点错误，请检查!";
			}
			XMLNote metaData = xml.getByName("META_DATA");
			if (metaData == null) {
				return "META_DATA节点错误，请检查!";
			}
			String is_merger = metaData.getValue("IS_MERGER");
			if (is_merger.equals("") || is_merger == null) {
				return "IS_MERGER节点错误，或者节点值不能为空，请检查！";
			}
			if (is_merger.equals("true")) {
				String merger_no = metaData.getValue("MERGER_NO");
				if (merger_no.equals("") || merger_no == null) {
					return "MERGER_NO节点错误，或者节点值不能为空，请检查！";
				}
				String is_codebar = metaData.getValue("IS_CODEBAR");
				if (is_codebar.equals("") || is_codebar == null) {
					return "IS_CODEBAR节点错误，或者节点值不能为空，请检查！";
				}
				if (is_codebar.equals("true")) {
					String codebar_type = metaData.getValue("CODEBAR_TYPE");
					if (codebar_type.equals("") || codebar_type == null) {
						return "CODEBAR_TYPE节点错误，或者节点值不能为空，请检查！";
					}
					String codebar_data = metaData.getValue("CODEBAR_DATA");
					if (codebar_data.equals("") || codebar_data == null) {
						return "CODEBAR_DATA节点错误，或者节点值不能为空，请检查！";
					}
					String x_doordinate = metaData.getValue("X_COORDINATE");
					if (x_doordinate.equals("") || x_doordinate == null) {
						return "X_COORDINATE节点错误，或者节点值不能为空，请检查！";
					}
					String y_doordinate = metaData.getValue("Y_COORDINATE");
					if (y_doordinate.equals("") || y_doordinate == null) {
						return "Y_COORDINATE节点错误，或者节点值不能为空，请检查！";
					}
				}
				String rule_type = metaData.getValue("RULE_TYPE");
				if (rule_type.equals("") || rule_type == null) {
					return "RULE_TYPE节点错误，或者节点值不能为空，请检查！";
				}
				if (rule_type.equals("0")) {// 0:规则号进行盖章,1:规则信息盖章
					String rule_no = metaData.getValue("RULE_NO");
					if (rule_no.equals("") || rule_no == null) {
						return "RULE_NO节点错误，或者节点值不能为空，请检查！";
					}
				} else {
					String rule_info = metaData.getValue("RULE_INFO");
					if (rule_info.equals("") || rule_info == null) {
						return "RULE_INFO节点错误，或者节点值不能为空，请检查！";
					}
					String cert_name = metaData.getValue("CERT_NAME");
					if (cert_name.equals("") || cert_name == null) {
						return "CERT_NAME节点错误，或者节点值不能为空，请检查！";
					}
					String seal_name = metaData.getValue("SEAL_NAME");
					if (seal_name.equals("") || seal_name == null) {
						return "SEAL_NAME节点错误，或者节点值不能为空，请检查！";
					}
				}
				String ftp_savepath = metaData.getValue("ftp_savepath");
				if (ftp_savepath.equals("") || ftp_savepath == null) {
					return "ftp_savepath节点错误，或者节点值不能为空，请检查！";
				} else {
					String ftp_address = metaData.getValue("ftp_address");
					if (ftp_address.equals("") || ftp_address == null) {
						return "ftp_address节点错误，或者节点值不能为空，请检查！";
					}
					String ftp_port = metaData.getValue("ftp_port");
					if (ftp_port.equals("") || ftp_port == null) {
						return "ftp_port节点错误，或者节点值不能为空，请检查！";
					}
					String ftp_user = metaData.getValue("ftp_user");
					if (ftp_user.equals("") || ftp_user == null) {
						return "ftp_user节点错误，或者节点值不能为空，请检查！";
					}
					String ftp_pwd = metaData.getValue("ftp_pwd");
					if (ftp_pwd.equals("") || ftp_pwd == null) {
						return "ftp_pwd节点错误，或者节点值不能为空，请检查！";
					}
				}
				String area_seal = metaData.getValue("AREA_SEAL");
				if (area_seal.equals("") || area_seal == null) {
					return "AREA_SEAL节点错误，或者节点值不能为空，请检查！";
				}
			} else {
				XMLNote fileList = xml.getByName("FILE_LIST");
				List<XMLNote> notes = fileList.getChilds();
				for (XMLNote note : notes) {
					String file_no = note.getValue("FILE_NO");
					if (file_no.equals("") || file_no == null) {
						return "FILE_NO节点错误，或者节点值不能为空，请检查！";
					} else {
						if (file_no.indexOf(".pdf") != -1
								|| file_no.indexOf(".doc") != -1
								|| file_no.indexOf(".docx") != -1) {
							return "FILE_NO值没有带后缀名，请检查！";
						}
					}
					String is_codebar = note.getValue("IS_CODEBAR");
					if (is_codebar.equals("") || is_codebar == null) {
						return "IS_CODEBAR节点错误，或者节点值不能为空，请检查！";
					} else {
						if (is_codebar.equals("true")) {
							String codebar_type = note.getValue("CODEBAR_TYPE");
							if (codebar_type.equals("") || codebar_type == null) {
								return "CODEBAR_TYPE节点错误，或者节点值不能为空，请检查！";
							}
							String codebar_data = note.getValue("CODEBAR_DATA");
							if (codebar_data.equals("") || codebar_data == null) {
								return "CODEBAR_DATA节点错误，或者节点值不能为空，请检查！";
							}
							String x_doordinate = note.getValue("X_COORDINATE");
							if (x_doordinate.equals("") || x_doordinate == null) {
								return "X_COORDINATE节点错误，或者节点值不能为空，请检查！";
							}
							String y_doordinate = note.getValue("Y_COORDINATE");
							if (y_doordinate.equals("") || y_doordinate == null) {
								return "Y_COORDINATE节点错误，或者节点值不能为空，请检查！";
							}
						}
					}
					String rule_type = note.getValue("RULE_TYPE");
					if(rule_type.equals("") || rule_type==null){
						return "RULE_TYPE节点错误，或者节点值不能为空，请检查！";
					}
					if (rule_type.equals("0")) {// 0:规则号进行盖章,1:规则信息盖章
						String rule_no = note.getValue("RULE_NO");
						if (rule_no.equals("") || rule_no == null) {
							return "RULE_NO节点错误，或者节点值不能为空，请检查！";
						}
					} else {
						String rule_info = note.getValue("RULE_INFO");
						if (rule_info.equals("") || rule_info == null) {
							return "RULE_INFO节点错误，或者节点值不能为空，请检查！";
						}
						String cert_name = note.getValue("CERT_NAME");
						if (cert_name.equals("") || cert_name == null) {
							return "CERT_NAME节点错误，或者节点值不能为空，请检查！";
						}
						String seal_name = note.getValue("SEAL_NAME");
						if (seal_name.equals("") || seal_name == null) {
							return "SEAL_NAME节点错误，或者节点值不能为空，请检查！";
						}
						
					}
					
					String cj_type = note.getValue("CJ_TYPE");
					if(cj_type.equals("") || cj_type==null){
						return "CJ_TYPE节点错误，或者节点值不能为空，请检查！";
					}
					if(cj_type.equals("data")){
						String model_name = note.getValue("MODEL_NAME");
						if(model_name.equals("") || model_name==null){
							return "MODEL_NAME节点错误，或者节点值不能为空，请检查！";
						}

					}else if(cj_type.equals("file")){
						String request_type = note.getValue("REQUEST_TYPE");
						if(request_type.equals("") || request_type==null){
							return "REQUEST_TYPE节点错误，或者节点值不能为空，请检查！";
						}
						if(request_type.equals("0")){
							String file_path = note.getValue("FILE_PATH");
							if(file_path.equals("") || file_path==null){
								return "FILE_PATH节点错误，或者节点值不能为空，请检查！";
							}
						}else if(request_type.equals("1")){
							String ftp_savepath = note.getValue("ftp_savepath");
							if (ftp_savepath.equals("") || ftp_savepath == null) {
								return "ftp_savepath节点错误，或者节点值不能为空，请检查！";
							} else {
								String ftp_address = note.getValue("ftp_address");
								if (ftp_address.equals("") || ftp_address == null) {
									return "ftp_address节点错误，或者节点值不能为空，请检查！";
								}
								String ftp_port = note.getValue("ftp_port");
								if (ftp_port.equals("") || ftp_port == null) {
									return "ftp_port节点错误，或者节点值不能为空，请检查！";
								}
								String ftp_user = note.getValue("ftp_user");
								if (ftp_user.equals("") || ftp_user == null) {
									return "ftp_user节点错误，或者节点值不能为空，请检查！";
								}
								String ftp_pwd = note.getValue("ftp_pwd");
								if (ftp_pwd.equals("") || ftp_pwd == null) {
									return "ftp_pwd节点错误，或者节点值不能为空，请检查！";
								}
							}
						}
						
					}
					String area_seal = metaData.getValue("AREA_SEAL");
					if (area_seal.equals("") || area_seal == null) {
						return "AREA_SEAL节点错误，或者节点值不能为空，请检查！";
					}

				}
			}
		} catch (Exception e) {
			return e.toString();
		}
		return "信息正确";
	}

	// 判断二维码类型
	public static String isCODEBAR(String is_merger, XMLNote xml)
			throws StringIndexOutOfBoundsException, Exception {
		String is_codebar = "0";
		if (is_merger.equals("合")) {
			is_codebar = xml.getValue("META_DATA.IS_CODEBAR");// 是否加盖二维码
		} else {
			XMLNote fileList = xml.getByName("FILE_LIST");
			is_codebar = fileList.getValue("TREE_NODE.IS_CODEBAR");
		}
		if ("true".equals(is_codebar)) {
			return "1";
		} else {
			return "0";
		}
	}

	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}

	private static String bpath() {
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
	 * 模板合成响应xml
	 * 
	 * @param req_no
	 * @param msg
	 * @param xmlStr
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public static String modelMergerXml(String req_no, String msg)
			throws Exception {
		ModelResponse response = new ModelResponse();
		RetData retData = new RetData();
		retData.setRET_CODE(req_no);
		retData.setFILE_MSG(msg);
		response.setRetData(retData);
		return response.toString();
	}

	/**
	 * pdf盖章响应xml
	 * 
	 * @param req_no
	 * @param msg
	 * @param xmlStr
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public static String sealPdfXml(String is_merger, XMLNote xml, String ip,
			String req_no, String msg,String transId) throws Exception {
		String bpath = bpath();
		LogSysServiceImpl ssObj = (LogSysServiceImpl) getBean("logSysService");
		if(is_merger.equals("合")){
			String file_name=xml.getValue("META_DATA.MERGER_NO");
			XMLNote base_data = xml.getByName("BASE_DATA");
		//	String user_id = base_data.getValue("USER_ID");
		//	String server_id = base_data.getValue("SYS_ID");
			String user_id = "zgfy";
			String server_id = "sys_id";
			deletefile(bpath + "doc/",file_name);
			if(req_no.equals("0")){
				msg="http://"+Constants.getProperty("server_ip")+":"+Constants.getProperty("server_port")+"/Seal/doc/download.jsp?name="+file_name+";";
			    ssObj.logAddServerSeal(user_id, server_id, ip, req_no,file_name,"盖章成功");
			}else{
				ssObj.logAddServerSeal(user_id, server_id, ip, req_no,file_name,msg);	
			}
		} else if (is_merger.equals("分")) {
			XMLNote fileList = xml.getByName("FILE_LIST");
			List<XMLNote> notes = fileList.getChilds();
			if (req_no.equals("0")) {
				msg = "";
				for (XMLNote note : notes) {
					String file_name = note.getValue("FILE_NO");
					msg += "http://" + Constants.getProperty("server_ip") + ":"
							+ Constants.getProperty("server_port")
							+ "/Seal/doc/download.jsp?name=" + file_name + ";";
					deletefile(bpath + "doc/", file_name);
					deletefile(bpath + "doc/", file_name.split("\\.")[0]+".pdf");
					XMLNote base_data = xml.getByName("BASE_DATA");
					String user_id = base_data.getValue("USER_ID");
					String server_id = base_data.getValue("SYS_ID");
					String file_no = note.getValue("FILE_NO");
					ssObj.logAddServerSeal(user_id, server_id, ip, req_no,
							file_no, "盖章成功");
				}
			} else {
				for (XMLNote note : notes) {
					String file_name = note.getValue("FILE_NO");
					deletefile(bpath + "doc/", file_name);
					XMLNote base_data = xml.getByName("BASE_DATA");
					String user_id = base_data.getValue("USER_ID");
					String server_id = base_data.getValue("SYS_ID");
					String file_no = note.getValue("FILE_NO");
					String cj_type=note.getValue("CJ_TYPE");
					ssObj.logAddServerSeal(user_id, server_id, ip, req_no,
							file_no, msg);
					String save_path = bpath + "sealdoc\\"
					+ file_no.split("\\.")[0] + ".pdf";
					if(cj_type.equals("base64")){
						FileUtil util=new FileUtil();
						byte[] data=util.getFileByte(save_path);
						String filebase64=Base64.encodeToString(data);
//						filebase64=filebase64.replace("\r\n", "");
//						filebase64=filebase64.replace("\r", "");
//						filebase64=filebase64.replace("\n", "");
						msg=filebase64;
					}
				}
			}
		}

		// SealDocResponse response = new SealDocResponse();
		// RetData retData = new RetData();
		// retData.setRET_CODE(req_no);
		// retData.setFILE_MSG(msg);
		// response.setRetData(retData);
		// logger.info(response.toString());
		StringBuffer s1 = new StringBuffer();
		s1.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>").append("\r\n");
		s1.append("<SealDocResponse>").append("\r\n");
		s1.append("<RetData>").append("\r\n");
		s1.append("<RET_CODE>");
		s1.append(req_no);
		s1.append("</RET_CODE>").append("\r\n");
		s1.append("<CETRANID>");
		s1.append(transId);
		s1.append("</CETRANID>").append("\r\n");
		s1.append("<FILE_MSG>");
		s1.append(msg);
		s1.append("</FILE_MSG>").append("\r\n");
		s1.append("</RetData>").append("\r\n");
		s1.append("</SealDocResponse>").append("\r\n");
		logger.info(s1.toString());
		return s1.toString();
	}

	public static boolean deletefile(String delpath, String file_name)
			throws Exception {
		File file = new File(delpath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(delpath + "\\" + filelist[i]);
				String filname = filelist[i];
				// logger.info("file_name:" + file_name);
				// logger.info("filname:" + filname);
				if (filname.equals(file_name)) {
					delfile.delete();
				}
			}
		}
		return true;
	}

	/**
	 * pdf盖章响应xml
	 * 
	 * @param req_no
	 * @param msg
	 * @param xmlStr
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public static String varityPdfXml(String req_no, String msg)
			throws Exception {
		// VarifyPdfResponse response = new VarifyPdfResponse();
		// RetData retData = new RetData();
		// retData.setRET_CODE(req_no);
		// retData.setFILE_MSG(msg);
		// response.setRetData(retData);
		StringBuffer s1 = new StringBuffer();
		s1.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>").append("\r\n");
		s1.append("<VarifyPdfResponse>").append("\r\n");
		s1.append("<RetData>").append("\r\n");
		s1.append("<RET_CODE>");
		s1.append(req_no);
		s1.append("</RET_CODE>").append("\r\n");
		s1.append("<FILE_MSG>");
		s1.append(msg);
		s1.append("</FILE_MSG>").append("\r\n");
		s1.append("</RetData>").append("\r\n");
		s1.append("</VarifyPdfResponse>").append("\r\n");
		return s1.toString();
	}

	private static SrvSealUtil srv_seal() {
		SrvSealUtil srv_seal = (SrvSealUtil) getBean("srvSeal");//
		if (srv_seal == null) {
			srv_seal = new SrvSealUtil();
		}
		return srv_seal;
	}

	// 判断客户端ip是否存在于应用系统中
	public static int isSysIP(XMLNote xml, String clientIp) throws Exception {
		XMLNote mase_data = xml.getByName("BASE_DATA");
		String sys_id = mase_data.getValue("SYS_ID");
		AppSysServiceImpl appobj = (AppSysServiceImpl) getBean("appSysService");
		boolean fl = appobj.getAppIp(sys_id, clientIp);
		if (fl == true) {
			return 0;
		} else {
			return 1;
		}
	}
}
