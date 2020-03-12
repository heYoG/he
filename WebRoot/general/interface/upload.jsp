<%@page import="java.util.UUID"%>
<%@page import="java.io.File"%>
<%@page import="com.dj.seal.util.properyUtil.DJPropertyUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="serv.MyOper" %>
<%@page import="com.dj.seal.util.ceUtil.TransCePo"%>
<%@page import="sun.tools.tree.ThisExpression"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page import="com.dj.seal.util.Constants"%>
<%@page	contentType="text/html;charset=UTF-8"%>
<%@page	import="com.dj.hotelApi.InterfaceClass"%>
<%@page	import="sun.misc.BASE64Encoder"%>
<jsp:useBean id="mySmartUpload"	scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("UTF-8");
	int allRet =0;//签章返回结果
	String sealRet=null;//返回给客户端的值
	try {
		long startTime = System.currentTimeMillis();
		/*初始化上传组件*/
		mySmartUpload.initialize(pageContext);
		mySmartUpload.upload();
		String filename="";
		com.jspsmart.upload.File myFile = null;
		String jsonParam = mySmartUpload.getRequest().getParameter("JSONParam");
		jsonParam = new String(jsonParam.getBytes(), "UTF-8");
		TransCePo cepo = JSON.parseObject(jsonParam, TransCePo.class);
		logger.info("凭证上传开始！流水号==========" + cepo.getCaseSeqID());
		byte[] fileData = null;
		logger.info("pdfInfo" + jsonParam);
		for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
			myFile = mySmartUpload.getFiles().getFile(i);//得到文件
			fileData = myFile.getBytes();
			UUID uuid = UUID.randomUUID();
			filename = cepo.getCaseSeqID()+ uuid+ myFile.getFileName().substring(0,myFile.getFileName().indexOf(".")) + ".pdf";
			SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
			Date nowTime = new Date(System.currentTimeMillis());
			String nowTimeStr = sdfFileName.format(nowTime);
			String folder = Constants.getProperty("noseal_path").trim()+ File.separatorChar + nowTimeStr;
			File dirFile = new File(folder);
			if (!dirFile.exists()) {
				logger.info("upload,创建存放未签章凭证文件文件夹，folder=" + folder);
				dirFile.mkdirs();
			}
			String fileName = folder + File.separatorChar + filename;
			myFile.saveAs(fileName);//保存未盖章文件到指定目录 
			filename = myFile.getFileName();
			logger.info(cepo.getCaseSeqID() + " 上传的文件：" + fileName);
			if (!myFile.isMissing()) {
				String recordId = "";
				try {
					if (cepo.getSealFont().contains("签名确认")) {
						allRet = MyOper.addNSHSeal(fileData, cepo);
					} else {
						allRet = MyOper.addNBSeal(fileName, fileData,cepo);
					}
					if (allRet == 1) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",签章成功");
					} else if (allRet == -1) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",图片转换异常");
					} else if (allRet == -2) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",服务器中无当前可用印章");
					} else if (allRet == -3) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",程序异常");
					} else if (allRet == -4) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",签章失败");
					} else if (allRet == -5) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",印章未绑定证书");
					} else if (allRet == -6) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",CE上传失败");
					} else if (allRet == -7) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",图片转换失败");
					} else if (allRet == -8) {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",文档打开失败");
					} else {
						logger.info(cepo.getCaseSeqID() + "签章返回值："+ allRet + ",其它异常");
					}
					sealRet = "failed: servercode:" + allRet;
					long endTime = System.currentTimeMillis();
					logger.info(cepo.getCaseSeqID() + "请求总耗时："+ (endTime - startTime));
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(cepo.getCaseSeqID()+ "upload.jsp--插入记录失败");
					logger.error(e.getMessage());
					out.clear();
					out.print(sealRet);
					out.flush();
					return;
				}
			}
		}
		out.clear();
		if (allRet == 1) {
			out.print("succeed");
		} else {
			out.print(sealRet);
		}
		out.flush();
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage());
		out.clear();
		out.print(sealRet);//返回控件HttpPost()方法值。
		out.flush();
	}
%>