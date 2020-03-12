<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@ page contentType="text/html;charset=utf-8" import="java.util.*" %><%@page import="java.text.SimpleDateFormat"%><%@page import="com.dj.hotelApi.InterfaceClass"%><%@page import="sun.misc.BASE64Encoder"%>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	try {
		logger.info("upload.jsp");
		// 初始化上传组件
		mySmartUpload.initialize(pageContext);
		mySmartUpload.upload();
		String filename;
		com.jspsmart.upload.File myFile = null;
		String pdfInfo = request.getParameter("pdfInfo");
		String user_id = request.getParameter("UID");
		logger.info(user_id);
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date date = new Date();//获取当前的系统时间
		UUID uuid = UUID.randomUUID();//生成一个UUID字符串
		
		String fileName = sf2.format(date)+uuid;
		String pwFileName = fileName+".pdf";
		for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
			myFile = mySmartUpload.getFiles().getFile(i);//得到文件
			filename = pwFileName;
			filename = new String(filename.getBytes(),"utf-8");
			logger.info("文件------"+filename);
			if (!myFile.isMissing()) {
		//		BASE64Encoder oEncoder = new BASE64Encoder();
		//		String fileData = oEncoder.encode(myFile.getBytes());
		//		myFile.saveAs("doc/hotelDocs/" + filename,mySmartUpload.SAVE_VIRTUAL);
				String filePath = InterfaceClass.saveHotelFile(filename,myFile.getBytes());
				String recordId = "";
				try{
					String fileData = filePath;
					recordId = InterfaceClass.addRecord(user_id,filename,pdfInfo,request.getRemoteAddr(),fileData);
				}catch (Exception e) {
					logger.info("upload.jsp--插入记录失败");
					logger.error(e.getMessage());
					out.clear();
					out.print("FAILED");
					out.flush();
					return;
				}
				if(recordId==null||recordId.equals("")){
					out.clear();
					out.print("FAILED");
					out.flush();
					return;
				}
			}
		}
		out.clear();
		out.print("upload ok");
		out.flush();
	} catch (Exception e) {
		logger.error(e.getMessage());
		out.clear();
		out.print("FAILED");//返回控件HttpPost()方法值。
		out.flush();
	}
%>